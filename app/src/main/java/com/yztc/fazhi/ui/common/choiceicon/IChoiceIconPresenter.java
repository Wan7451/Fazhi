package com.yztc.fazhi.ui.common.choiceicon;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by wanggang on 2017/2/24.
 */

public interface IChoiceIconPresenter {
    void openCamera();

    void openGallery();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
