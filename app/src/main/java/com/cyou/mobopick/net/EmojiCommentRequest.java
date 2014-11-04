package com.cyou.mobopick.net;

import com.cyou.mobopick.domain.AppModel;

import org.json.JSONException;

/**
 * Created by chengfei on 14/10/30.
 */
public class EmojiCommentRequest extends BaseRequest<AppModel.EmojiComment> {

    public static final String URL = HOST + "/home/increment/resource/";

    private AppModel.EmojiComment emojiComment;
    public EmojiCommentRequest(AppModel.EmojiComment emojiComment, String url, NetworkRequestListener<AppModel.EmojiComment> listener) {
        super(url, null, listener);
        this. emojiComment = emojiComment;
    }


    @Override
    protected AppModel.EmojiComment parseResponse(String jsonString) throws JSONException {
        return emojiComment;
    }
}
