package com.yztc.fazhi.ui.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yztc.fazhi.R;
import com.yztc.fazhi.ui.request.mvp.IRequestPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.liweiqin.testselectphoto.ui.adpater.ViewHolderAdapter;

/**
 * Created by wanggang on 2017/2/27.
 */

public class ListImagesAdapter extends ViewHolderAdapter<ListImagesAdapter.ListViewHolder,String> {


    private final LayoutInflater inflater;
    private Context context;
    private IRequestPresenter requestPresenter;

    public ListImagesAdapter(Context context, ArrayList<String> selectedImgs) {
        super(context,selectedImgs);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_selected_imgs, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        Glide.with(context)
                .load(getList().get(position))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.itemImgVSelected);
    }



    static class ListViewHolder extends ViewHolderAdapter.ViewHolder {

        @BindView(R.id.item_imgV_selected)
        ImageView itemImgVSelected;
        @BindView(R.id.item_btn_del)
        Button itemBtnDel;


        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemBtnDel.setVisibility(View.GONE);
        }
    }

}
