package cn.liweiqin.testselectphoto.model;

import java.io.Serializable;

/**
 * Created by liweiqin on 2016/1/31.
 */

//图片信息

public class PhotoInfo implements Serializable {
    public PhotoInfo() {
    }

    private int photoId;
    private String photoPath;
    private int drawable;

    private int width;
    private int height;


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
