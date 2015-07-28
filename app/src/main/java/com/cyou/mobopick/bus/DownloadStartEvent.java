package com.cyou.mobopick.bus;

/**
 * Created by chengfei on 14/11/14.
 */
public class DownloadStartEvent extends DownloadEvent {

    public DownloadStartEvent(String url) {
        super(url);
    }
}
