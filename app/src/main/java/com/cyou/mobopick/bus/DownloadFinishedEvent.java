package com.cyou.mobopick.bus;

/**
 * Created by chengfei on 14/11/7.
 */
public class DownloadFinishedEvent extends DownloadEvent {

    public DownloadFinishedEvent(String url) {
        super(url);
    }
}
