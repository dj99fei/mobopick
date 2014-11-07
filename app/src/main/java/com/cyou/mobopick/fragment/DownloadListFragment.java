package com.cyou.mobopick.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.DownloadAdapter;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.providers.DownloadManager;
import com.cyou.mobopick.providers.downloads.ui.DownloadItem;
import com.cyou.mobopick.util.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/5.
 */
public class DownloadListFragment extends BaseFragment implements DialogInterface.OnCancelListener, AdapterView.OnItemClickListener, DownloadItem.DownloadSelectListener, CompoundButton.OnCheckedChangeListener {

    private DownloadManager downloadManager;
    private Cursor dataSortedCursor;
    private int statusColumnId;
    private int idColumnId;
    private int localUriColumnId;
    private int mediaTypeColumnId;
    private int reasonColumndId;
    private DownloadAdapter adapter;

    private static final String TAG = DownloadListFragment.class.getSimpleName();

    @InjectView(R.id.list)
    protected ListView listView;
    @InjectView(R.id.action_delete_layout)
    protected View deleteLayout;
    @InjectView(R.id.action_selectAll)
    protected CheckBox selectAllCheckbox;
    @InjectView(R.id.action_delete)
    protected Button deleteButton;
    private List<Long> ids = new ArrayList<>();


    public static DownloadListFragment newInstance() {
        return new DownloadListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        downloadManager = new DownloadManager(getActivity().getContentResolver(), getActivity().getPackageName());
        downloadManager.setAccessAllDownloads(true);
        DownloadManager.Query baseQuery = new DownloadManager.Query().setOnlyIncludeVisibleInDownloadsUi(true);
        dataSortedCursor = downloadManager.query(baseQuery);
        if (dataSortedCursor != null) {
            getActivity().startManagingCursor(dataSortedCursor);

            statusColumnId = dataSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
            idColumnId = dataSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
            localUriColumnId = dataSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI);
            mediaTypeColumnId = dataSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE);
            reasonColumndId = dataSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);
            adapter = new DownloadAdapter(getActivity(), dataSortedCursor);
            adapter.setListener(this);
            while (dataSortedCursor.moveToNext()) {
                ids.add(dataSortedCursor.getLong(idColumnId));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_downloads, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        selectAllCheckbox.setOnCheckedChangeListener(this);
        deleteButton.setOnClickListener(this);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.setMultiChoiceModeListener(this);
    }

    /**
     * We keep track of when a dialog is being displayed for a pending download,
     * because if that download starts running, we want to immediately hide the
     * dialog.
     */
    private Long mQueuedDownloadId = null;
    private AlertDialog mQueuedDialog;

    private void handleItemClick(long id, int status, String mimeType, String localUri, String packageName) {
        switch (status) {
            case DownloadManager.STATUS_PENDING:
            case DownloadManager.STATUS_RUNNING:
//                showRunningDialog(id);
                downloadManager.pauseDownload(id);
                break;

            case DownloadManager.STATUS_PAUSED:
//                if (isPausedForWifi(cursor)) {
//                    mQueuedDownloadId = id;
//                    mQueuedDialog = new AlertDialog.Builder(getActivity())
//                            .setTitle(R.string.dialog_title_queued_body)
//                            .setMessage(R.string.dialog_queued_body)
//                            .setPositiveButton(R.string.keep_queued_download, null)
//                            .setNegativeButton(R.string.remove_download,
//                                    getDeleteClickHandler(id))
//                            .setOnCancelListener(this).show();
//                } else {
//                    showPausedDialog(id);
//                }
                downloadManager.resumeDownload(id);
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                openCurrentDownload(id ,mimeType, localUri, packageName);
                break;

            case DownloadManager.STATUS_FAILED:
//                showFailedDialog(id, getErrorMessage(cursor));
                break;
        }
    }

    private boolean isPausedForWifi(Cursor cursor) {
        return cursor.getInt(reasonColumndId) == DownloadManager.PAUSED_QUEUED_FOR_WIFI;
    }

    /**
     * Send an Intent to open the download currently pointed to by the given
     * cursor.
     */
    private void openCurrentDownload(long id, String mimeType, String localUriStr,  String packageName) {

        if (DeviceInfo.apps.contains(packageName)) {
            Utils.startAPP(packageName);
            return ;
        }

        Uri localUri = Uri.parse(localUriStr);
        try {
            getActivity().getContentResolver().openFileDescriptor(localUri, "r").close();
        } catch (FileNotFoundException exc) {
            Log.d(TAG,
                    "Failed to open download " + id,
                    exc);
            showFailedDialog(id,
                    getString(R.string.dialog_file_missing_body));
            return;
        } catch (IOException exc) {
            // close() failed, not a problem
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(localUri, mimeType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), R.string.download_no_application_title,
                    Toast.LENGTH_LONG).show();
        }

    }

    private void showFailedDialog(long downloadId, String dialogBody) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_title_not_available)
                .setMessage(dialogBody)
                .setNegativeButton(R.string.delete_download,
                        getDeleteClickHandler(downloadId))
                .setPositiveButton(R.string.retry_download,
                        getRestartClickHandler(downloadId)).show();
    }

    /**
     * @return an OnClickListener to delete the given downloadId from the
     * Download Manager
     */
    private DialogInterface.OnClickListener getDeleteClickHandler(
            final long downloadId) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDownload(downloadId);
            }
        };
    }

    /**
     * Delete a download from the Download Manager.
     */
    private void deleteDownload(long downloadId) {
        if (moveToDownload(downloadId)) {
            int status = dataSortedCursor.getInt(statusColumnId);
            boolean isComplete = status == DownloadManager.STATUS_SUCCESSFUL
                    || status == DownloadManager.STATUS_FAILED;
            String localUri = dataSortedCursor.getString(localUriColumnId);
            if (isComplete && localUri != null) {
                String path = Uri.parse(localUri).getPath();
                if (path.startsWith(Environment.getExternalStorageDirectory()
                        .getPath())) {
                    downloadManager.markRowDeleted(downloadId);
                    return;
                }
            }
        }
        downloadManager.remove(downloadId);
    }

    /**
     * Move {@link #dataSortedCursor} to the download with the given ID.
     *
     * @return true if the specified download ID was found; false otherwise
     */
    private boolean moveToDownload(long downloadId) {
        for (dataSortedCursor.moveToFirst(); !dataSortedCursor.isAfterLast(); dataSortedCursor
                .moveToNext()) {
            if (dataSortedCursor.getLong(idColumnId) == downloadId) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return an OnClickListener to resume the given downloadId from the
     * Download Manager
     */
    private DialogInterface.OnClickListener getResumeClickHandler(
            final long downloadId) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadManager.resumeDownload(downloadId);
            }
        };
    }

    /**
     * @return an OnClickListener to restart the given downloadId in the
     * Download Manager
     */
    private DialogInterface.OnClickListener getRestartClickHandler(
            final long downloadId) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadManager.restartDownload(downloadId);
            }
        };
    }

    private void showPausedDialog(long downloadId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.download_queued)
                .setMessage(R.string.dialog_paused_body)
                .setNegativeButton(R.string.delete_download,
                        getDeleteClickHandler(downloadId))
                .setPositiveButton(R.string.resume_download,
                        getResumeClickHandler(downloadId)).show();
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        mQueuedDownloadId = null;
        mQueuedDialog = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (editMode) {
            inflater.inflate(R.menu.cancel, menu);
        } else {
            inflater.inflate(R.menu.download, menu);
        }
    }

    private boolean editMode;

    //    private ActionMode actionMode;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            editMode = true;
            deleteLayout.setVisibility(View.VISIBLE);
//            ((ActionBarActivity) getActivity()).startSupportActionMode(callback);
        }
        if (item.getItemId() == R.id.action_selectAll) {
            editMode = false;
            deleteLayout.setVisibility(View.GONE);
            selectedIds.clear();
        }
        adapter.setEditMode(editMode);
        getActivity().invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.cancel, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            adapter.setEditMode(false);
            deleteLayout.setVisibility(View.GONE);
            if (menuItem.getItemId() == R.id.action_selectAll) {
                actionMode.finish();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long id = (Long) view.getTag(R.id.tag_id);
        int status = (Integer)view.getTag(R.id.tag_status);
        String mimeType = (String) view.getTag(R.id.tag_mime);
        String localUri = (String) view.getTag(R.id.tag_local);
        String packageName = (String) view.getTag(R.id.tag_package_name);
        handleItemClick(id, status, mimeType, localUri, packageName);
    }

    private void showRunningDialog(long downloadId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.download_running)
                .setMessage(R.string.dialog_running_body)
                .setNegativeButton(R.string.cancel_running_download,
                        getDeleteClickHandler(downloadId))
                .setPositiveButton(R.string.pause_download,
                        getPauseClickHandler(downloadId)).show();
    }

    /**
     * @return an OnClickListener to pause the given downloadId from the
     * Download Manager
     */
    private DialogInterface.OnClickListener getPauseClickHandler(
            final long downloadId) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadManager.pauseDownload(downloadId);
            }
        };
    }

    private Set<Long> selectedIds = new HashSet<>();

    @Override
    public void onDownloadSelectionChanged(long downloadId, boolean isSelected) {
        if (isSelected) {
            selectedIds.add(downloadId);
        } else {
            selectedIds.remove(downloadId);
        }
    }

    @Override
    public boolean isDownloadSelected(long id) {
        return selectedIds.contains(id);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            //TODO: select ALL
            selectedIds.clear();
            selectedIds.addAll(ids);

        } else {
            //TODO: deselect ALL
            selectedIds.clear();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_delete) {
            editMode = false;
            long[] idArray = getSelectIds();
            if (idArray != null && idArray.length > 0) {
                downloadManager.remove(getSelectIds());
            }
            selectAllCheckbox.setChecked(false);
        }
    }

    private long[] getSelectIds() {
        long[] idArray = new long[selectedIds.size()];
        Iterator<Long> iterator = selectedIds.iterator();
        for (int i = 0; i < selectedIds.size(); i++) {
            Long next = iterator.next();
            if (next != null) {
                idArray[i] = next;
            }
        }
        return idArray;
    }
}

