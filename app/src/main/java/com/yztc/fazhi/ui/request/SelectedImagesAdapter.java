package com.yztc.fazhi.ui.request;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yztc.fazhi.R;
import com.yztc.fazhi.ui.request.mvp.IRequestPresenter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.liweiqin.testselectphoto.ui.adpater.ViewHolderAdapter;

/**
 * Created by wanggang on 2017/2/27.
 */

public class SelectedImagesAdapter extends ViewHolderAdapter<SelectedImagesAdapter.SelectedViewHolder, String> {


    private Context context;
    private IRequestPresenter requestPresenter;

    public SelectedImagesAdapter(Context context, ArrayList<String> selectedImgs, IRequestPresenter requestPresenter) {
        super(context, selectedImgs);
        this.context = context;
        this.requestPresenter = requestPresenter;
    }


    @Override
    public SelectedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate(R.layout.item_selected_imgs, parent);
        return new SelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectedViewHolder holder, final int position) {
        if (getList() == null || position == getList().size()) {
//            holder.itemImgVSelected.setImageResource(R.mipmap.ic_launcher);
            Glide.with(context)
                    .load(R.drawable.icon_addpic_unfocused)
                    .into(holder.itemImgVSelected);
            holder.itemImgVSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPresenter.choiceImages();
                }
            });
            holder.itemBtnDel.setVisibility(View.GONE);
            return;
        }

        Glide.with(context)
                .load(Uri.fromFile(new File(getList().get(position))))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.itemImgVSelected);

        holder.itemBtnDel.setVisibility(View.VISIBLE);
        holder.itemBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPresenter.deleteImage(getList().get(position));
            }
        });
    }

    @Override
    public int getCount() {
        if (super.getCount() >= 9) {
            return 9;
        }
        return super.getCount() + 1;
    }


    static class SelectedViewHolder extends ViewHolderAdapter.ViewHolder {

        @BindView(R.id.item_imgV_selected)
        ImageView itemImgVSelected;
        @BindView(R.id.item_btn_del)
        Button itemBtnDel;


        public SelectedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
