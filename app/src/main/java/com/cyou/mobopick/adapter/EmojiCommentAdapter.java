package com.cyou.mobopick.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.AppModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chengfei on 14/10/29.
 */
public class EmojiCommentAdapter extends  RecyclerView.Adapter<EmojiCommentAdapter.ListItemViewHolder> {


    private List<AppModel.EmojiComment> emojiComments;

    public EmojiCommentAdapter(List<AppModel.EmojiComment> emojiComments) {
        this.emojiComments = emojiComments;
    }

    @Override
    public EmojiCommentAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_emoji_comment, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmojiCommentAdapter.ListItemViewHolder listItemViewHolder, int i) {
        AppModel.EmojiComment comment = emojiComments.get(i);
        listItemViewHolder.emojiCommentText.setText(comment.label);
        listItemViewHolder.emojiCommentText.setCompoundDrawablesWithIntrinsicBounds(0, comment.drawable, 0, 0);
        listItemViewHolder.numText.setText(String.valueOf(comment.totalNum));
    }

    @Override
    public int getItemCount() {
        return emojiComments != null ? emojiComments.size() : 0;
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.text_emoji_comment)
        TextView emojiCommentText;
        @InjectView(R.id.text_emoji_comment_num)
        TextView numText;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
