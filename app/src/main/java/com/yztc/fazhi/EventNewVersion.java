package com.yztc.fazhi;

/**
 * Created by wanggang on 2017/2/28.
 */

public class EventNewVersion {
    String newFile;

    public EventNewVersion(String newFile) {
        this.newFile = newFile;
    }

    public String getNewFile() {
        return newFile;
    }

    public void setNewFile(String newFile) {
        this.newFile = newFile;
    }
}
