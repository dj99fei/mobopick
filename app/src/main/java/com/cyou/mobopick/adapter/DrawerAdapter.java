package com.cyou.mobopick.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.view.DrawerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/10.
 */
public class DrawerAdapter extends BaseAdapter<DrawerItem> {


    private final static List<DrawerItem> drawerItems;

    static {
        drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem(R.drawable.ic_downloads, R.string.daily_pick, R.id.item_drawer_dailypick));
        drawerItems.add(new DrawerItem(R.drawable.ic_downloads, R.string.my_group, R.id.item_drawer_group));
        drawerItems.add(new DrawerItem(R.drawable.ic_downloads, R.string.downloads, R.id.item_drawer_downloads));
    }

    public DrawerAdapter(Context context) {
        super(context);
        data = drawerItems;
    }

    @Override
    protected View inflate() {
        return View.inflate(context, R.layout.item_drawer, null);
    }

    @Override
    protected BaseViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViews(BaseViewHolder holder, int position) {
        DrawerItem item = drawerItems.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemActionText.setText(item.text);
        viewHolder.itemActionText.setCompoundDrawablesWithIntrinsicBounds(item.drawable, 0, 0, 0);
    }

    public class ViewHolder extends BaseViewHolder {
        @InjectView(R.id.item_action)
        protected TextView itemActionText;
        public ViewHolder(View view) {
            super(view);
        }
    }
}
