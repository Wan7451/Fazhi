package cn.liweiqin.testselectphoto.ui.adpater;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.liweiqin.testselectphoto.R;
import cn.liweiqin.testselectphoto.model.PhotoFolderInfo;
import cn.liweiqin.testselectphoto.utils.PhotoUtil;

/**
 * Created by liweiqin on 2016/1/31.
 */
public class FolderListAdapter extends ViewHolderAdapter<FolderListAdapter.FolderViewHolder, PhotoFolderInfo> {

    private PhotoFolderInfo mSelectPhotoFolderInfo;

    public PhotoFolderInfo getmSelectPhotoFolderInfo() {
        return mSelectPhotoFolderInfo;
    }

    public void setmSelectPhotoFolderInfo(PhotoFolderInfo mSelectPhotoFolderInfo) {
        this.mSelectPhotoFolderInfo = mSelectPhotoFolderInfo;
    }


    public FolderListAdapter(Activity activity, List<PhotoFolderInfo> list) {
        super(activity, list);
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflate(R.layout.item_photofolder, viewGroup);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FolderViewHolder viewHolder, int position) {
        PhotoFolderInfo mPhotoFolderInfo = getData().get(position);
        if (null != mPhotoFolderInfo.getCoverPhoto())
            PhotoUtil.display((Activity) getContext(), mPhotoFolderInfo.getCoverPhoto().getPhotoPath(), viewHolder.iv_cover,200,200);
        if (null != mPhotoFolderInfo.getPhotoInfoList())
            viewHolder.tv_photo_count.setText(String.valueOf(mPhotoFolderInfo.getPhotoInfoList().size()));
        if (null != mPhotoFolderInfo.getFolderName())
            viewHolder.tv_folder_name.setText(mPhotoFolderInfo.getFolderName());
        if (mSelectPhotoFolderInfo == mPhotoFolderInfo || (mSelectPhotoFolderInfo == null && position == 0)) {
            viewHolder.iv_folder_check.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_folder_check.setVisibility(View.GONE);
        }
    }


    static class FolderViewHolder extends ViewHolderAdapter.ViewHolder {

        ImageView iv_cover;
        ImageView iv_folder_check;
        TextView tv_folder_name;
        TextView tv_photo_count;
        View mView;

        public FolderViewHolder(View view) {
            super(view);
            this.mView = view;
            iv_cover = (ImageView) mView.findViewById(R.id.iv_cover);
            iv_folder_check = (ImageView) mView.findViewById(R.id.iv_folder_check);
            tv_folder_name = (TextView) mView.findViewById(R.id.tv_folder_name);
            tv_photo_count = (TextView) mView.findViewById(R.id.tv_photo_count);
        }
    }
}
