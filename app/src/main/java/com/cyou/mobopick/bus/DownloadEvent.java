package com.cyou.mobopick.bus;

/**
 * Created by chengfei on 14/11/7.
 */
public class DownloadEvent {
    private String url;

    public DownloadEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
