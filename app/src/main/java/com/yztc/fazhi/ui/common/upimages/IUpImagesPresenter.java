package com.yztc.fazhi.ui.common.upimages;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wanggang on 2017/2/24.
 */

public interface IUpImagesPresenter {
    void upImage( File img);
    void upImages(ArrayList<File> imgs);
}
