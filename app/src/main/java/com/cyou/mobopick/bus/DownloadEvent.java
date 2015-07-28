package com.cyou.mobopick.bus;

/**
 * Created by chengfei on 14/11/14.
 */
public class DownloadEvent {
    protected String url;

    public DownloadEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
