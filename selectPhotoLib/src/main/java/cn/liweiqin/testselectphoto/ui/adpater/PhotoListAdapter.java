package cn.liweiqin.testselectphoto.ui.adpater;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import cn.liweiqin.testselectphoto.R;
import cn.liweiqin.testselectphoto.model.PhotoInfo;
import cn.liweiqin.testselectphoto.utils.PhotoUtil;

/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo> {

    private int mScreenWidth;
    private Map<String, PhotoInfo> mSelectList;
    private int mRowWidth;


    public PhotoListAdapter(Activity activity, List<PhotoInfo> list, Map<String, PhotoInfo> selectList, int mScreenWidth) {
        super(activity, list);
        this.mScreenWidth = mScreenWidth;
        this.mSelectList = selectList;
        this.mRowWidth = mScreenWidth / 3;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflate(R.layout.item_photo, viewGroup);
        setHeight(view);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder viewHolder, int position) {
        PhotoInfo info = getData().get(position);
        if (info.getDrawable() != 0 && info.getPhotoId() == -100) {
            PhotoUtil.display((Activity) getContext(), info.getDrawable(), viewHolder.iv_thumb, mRowWidth, mRowWidth);
            //选择的按钮隐藏掉
            viewHolder.iv_check.setVisibility(View.GONE);
        } else {
            PhotoUtil.display((Activity) getContext(), info.getPhotoPath(), viewHolder.iv_thumb, mRowWidth, mRowWidth);
            viewHolder.iv_check.setVisibility(View.VISIBLE);
            if (mSelectList.get(info.getPhotoPath()) != null) { // is selected..
                viewHolder.iv_check.setSelected(true);
            } else {
                viewHolder.iv_check.setSelected(false);
            }
        }
    }

    public void setHeight(View view) {
        int height = mScreenWidth / 3 - 8;
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }


    public static class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {

        public ImageView iv_thumb;
        public ImageView iv_check;
        public View mView;

        public PhotoViewHolder(View view) {
            super(view);
            mView = view;
            iv_check = (ImageView) mView.findViewById(R.id.iv_check);
            iv_thumb = (ImageView) mView.findViewById(R.id.iv_thumb);
        }
    }
}
