package com.cyou.mobopick.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.Version;
import com.cyou.mobopick.util.SharedPreferencesHelper;

/**
 * Created by chengfei on 14-10-21.
 */
public class UpdateDialogFragment extends DialogFragment {


    private Version version;
    private SharedPreferencesHelper helper;

    public static UpdateDialogFragment newInstance() {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        version = Version.getLastestVersion();
        helper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_msg_update);
        builder.setTitle(R.string.dialog_title_update);
        builder.setPositiveButton(R.string.dialog_button_update_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Uri uri = Uri.parse(version.downloadUrl);
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(downloadIntent);
                Version.setSessionNotified(true);
            }
        });
        builder.setNegativeButton(R.string.dialog_button_update_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Version.setSessionNotified(true);
                helper.withKey(R.string.key_poped_version_code).setData(int.class, version.versionCode).commit();

            }
        });
        return builder.create();
    }
}

