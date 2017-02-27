package cn.liweiqin.testselectphoto.core;


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import cn.liweiqin.testselectphoto.model.PhotoInfo;

/**
 * Created by liweiqin on 2016/1/31.
 */


//用于配置 图片选取的选项

public class FunctionConfig implements Cloneable {

    private Context context;
    private int maxSize;  //最大的图片选择数
    private ArrayList<String> selectedList;//选择的照片 private boolean debug;
    private File takePhotoFolder; //拍照文件夹

    private FunctionConfig(final Builder mBuilder) {
        this.maxSize = mBuilder.maxSize;
        this.selectedList = mBuilder.selectedList;
        this.context = mBuilder.context;
        this.takePhotoFolder = mBuilder.takePhotoFolder;

        if (takePhotoFolder == null) {
            takePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/" + "SchoPhoto/");
        }
        if (!takePhotoFolder.exists()) {
            takePhotoFolder.mkdirs();
        }

    }


    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public FunctionConfig clone() {
        FunctionConfig o = null;
        try {
            o = (FunctionConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public Context getContext() {
        return context;
    }

    public File getTakePhotoFolder() {
        return this.takePhotoFolder;
    }


    public static class Builder {
        private File takePhotoFolder;
        private Context context;
        private int maxSize;
        private ArrayList<String> selectedList;//选择的照片


        public Builder setSelectedList(ArrayList<String> selectedList) {
            this.selectedList = selectedList;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setSelected(ArrayList<String> selectedList) {
            if (selectedList != null) {
                this.selectedList = (ArrayList<String>) selectedList.clone();
            }
            return this;
        }

        public Builder setSelected(Collection<PhotoInfo> selectedList) {
            if (selectedList != null) {
                ArrayList<String> list = new ArrayList<>();
                for (PhotoInfo info : selectedList) {
                    if (info != null) {
                        list.add(info.getPhotoPath());
                    }
                }

                this.selectedList = list;
            }
            return this;
        }

        public FunctionConfig build() {
            return new FunctionConfig(this);
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTakePhotoFolder(File takePhotoFolder) {
            this.takePhotoFolder = takePhotoFolder;
            return this;
        }
    }

}
