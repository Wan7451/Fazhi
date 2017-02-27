package cn.liweiqin.testselectphoto.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import cn.liweiqin.testselectphoto.R;

/**
 * Created by liweiqin on 2016/2/1.
 *
 * 封装的Adapter ，自带 ViewHolder
 *
 * VH  ViewHolder 类型
 * T   显示数据的类型
 */
public abstract class ViewHolderAdapter<VH extends ViewHolderAdapter.ViewHolder, T> extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<T> mList;


    public ViewHolderAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(this.mContext);

    }


    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderAdapter.ViewHolder holder;
        if (null == convertView) {
            holder = this.onCreateViewHolder(parent, position);
            holder.view.setTag(holder);
        } else {
            holder = (ViewHolderAdapter.ViewHolder) convertView.getTag();
        }
        this.onBindViewHolder((VH) holder, position);
        return holder.view;
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int position);

    public abstract void onBindViewHolder(VH viewHolder, int position);


    @Override
    public int getCount() {
        return this.mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return this.mList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return (long) position;
    }


    public List<T> getData() {
        return this.mList;
    }

    public View inflate(int resLayout, ViewGroup parent) {
        return this.mLayoutInflater.inflate(resLayout, parent, false);
    }

    public Context getContext() {
        return this.mContext;
    }


    public static class ViewHolder {
        View view;
        public ViewHolder(View view) {
            this.view = view;
        }
    }
}
