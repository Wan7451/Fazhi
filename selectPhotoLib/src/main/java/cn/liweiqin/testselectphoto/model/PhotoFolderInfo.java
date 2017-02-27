package cn.liweiqin.testselectphoto.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liweiqin on 2016/1/31.
 */

/**
 * 图片文件夹对象
 */

public class PhotoFolderInfo implements Serializable {

    public PhotoFolderInfo() {
    }

    private int folderId;
    private String FolderName;  //文件夹名字
    private PhotoInfo coverPhoto;  //封面图片
    private List<PhotoInfo> photoInfoList;  //文件夹内的图片

    public List<PhotoInfo> getPhotoInfoList() {
        return photoInfoList;
    }

    public void setPhotoInfoList(List<PhotoInfo> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }

    public PhotoInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(PhotoInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
