package cn.liweiqin.testselectphoto.ui.weight;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.liweiqin.testselectphoto.BasePhotoActivity;
import cn.liweiqin.testselectphoto.R;
import cn.liweiqin.testselectphoto.core.FunctionConfig;
import cn.liweiqin.testselectphoto.core.PhotoFinal;
import cn.liweiqin.testselectphoto.model.PhotoFolderInfo;
import cn.liweiqin.testselectphoto.model.PhotoInfo;
import cn.liweiqin.testselectphoto.ui.Callback;
import cn.liweiqin.testselectphoto.ui.adpater.FolderListAdapter;
import cn.liweiqin.testselectphoto.ui.adpater.PhotoListAdapter;
import cn.liweiqin.testselectphoto.utils.PhotoUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;


/**
 * 这里就是展示图片的界面了
 * <p/>
 * Created by liweiqin on 2016/1/31.
 */
@RuntimePermissions
public class PhotoSelectActivity extends BasePhotoActivity implements View.OnClickListener, AbsListView.OnItemClickListener, Callback {

    private static final int HANDLER_REFRESH_LIST_EVENT = 1002;

    private Callback mCallback;

    /**
     * loading...
     */
    private TextView tv_empty_view;
    /**
     * back
     */
    private ImageView iv_back;
    /**
     * finish to select picture
     */
    private TextView tv_select_finish;

    /**
     * finish to selct picture folder
     */
    private TextView tv_photo_folder;

    /***
     * photo gridview
     */
    private GridView gv_photo_list;

    /**
     * show picture folder
     */
    private LinearLayout ll_folder_panel;
    /**
     * photoFolder  listview
     */
    private ListView lv_folder_list;

    /**
     * 当前展示图片的list
     */
    private List<PhotoInfo> mCurrentList;
    private PhotoListAdapter mPhotoListAdapter;


    /**
     * 所有的图片文件列表
     */
    private List<PhotoFolderInfo> mAllPhotoFolderList;
    private FolderListAdapter mFolderListAdapter;

    private FunctionConfig mFunctionConfig;
    /**
     * current selected picture
     */
    private LinkedHashMap<String, PhotoInfo> mSelectPhotoMap = new LinkedHashMap<>();


    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLER_REFRESH_LIST_EVENT) {
                //刷新界面
                refreshSelectCount();
                mPhotoListAdapter.notifyDataSetChanged();
                mFolderListAdapter.notifyDataSetChanged();
                if (mAllPhotoFolderList.get(0).getPhotoInfoList() == null || mAllPhotoFolderList.get(0).getPhotoInfoList().size() == 0) {
                    tv_empty_view.setText("没有照片");
                }
                gv_photo_list.setEnabled(true);
                tv_select_finish.setEnabled(true);
                lv_folder_list.setEnabled(true);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFunctionConfig = PhotoFinal.getFunctionConfig();
        setContentView(R.layout.activity_select);
        initView();
        setClikListener();
        setCallback(this);

        mCurrentList = new ArrayList<PhotoInfo>();
        mPhotoListAdapter = new PhotoListAdapter(PhotoSelectActivity.this, mCurrentList, mSelectPhotoMap, mScreenWidth);
        gv_photo_list.setEmptyView(tv_empty_view);
        gv_photo_list.setAdapter(mPhotoListAdapter);
        mAllPhotoFolderList = new ArrayList<PhotoFolderInfo>();
        mFolderListAdapter = new FolderListAdapter(PhotoSelectActivity.this, mAllPhotoFolderList);
        lv_folder_list.setAdapter(mFolderListAdapter);

        refreshSelectCount();
        //加载图片
        getPhotos();


    }


    /**
     * 刷新现在选择的文字
     */
    private void refreshSelectCount() {
        tv_select_finish.setText(getString(R.string.selected,
                mSelectPhotoMap.size(),
                mFunctionConfig.getMaxSize()));
    }

    private void setClikListener() {
        iv_back.setOnClickListener(this);
        tv_select_finish.setOnClickListener(this);
        tv_photo_folder.setOnClickListener(this);
        lv_folder_list.setOnItemClickListener(this);
        gv_photo_list.setOnItemClickListener(this);

    }

    private void initView() {
        tv_empty_view = (TextView) findViewById(R.id.tv_empty_view);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_select_finish = (TextView) findViewById(R.id.tv_select_finish);
        tv_photo_folder = (TextView) findViewById(R.id.tv_photo_folder);
        gv_photo_list = (GridView) findViewById(R.id.gv_photo_list);
        ll_folder_panel = (LinearLayout) findViewById(R.id.ll_folder_panel);
        lv_folder_list = (ListView) findViewById(R.id.lv_folder_list);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_select_finish) {

            if (PhotoFinal.getCallback() != null)
                PhotoFinal.getCallback().onHanlderSuccess(PhotoFinal.REQUEST_CODE_MUTI, new ArrayList<PhotoInfo>(mSelectPhotoMap.values()));
            this.finish();
        } else if (id == R.id.iv_back) {
            this.finish();
        } else if (id == R.id.tv_photo_folder) {
            if (ll_folder_panel.getVisibility() == View.GONE) {
                ll_folder_panel.setVisibility(View.VISIBLE);
            } else {
                ll_folder_panel.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取照片集
     * <p/>
     * 先清除图片集列表 再加载图片集列表 清除单前图集的所有图片 再加载当前图片
     */
    public void getPhotos() {
        tv_select_finish.setEnabled(false);
        gv_photo_list.setEnabled(false);
        lv_folder_list.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                super.run();
                mAllPhotoFolderList.clear();
                //获取所有图片的文件夹
                List<PhotoFolderInfo> allFolderList = PhotoUtil.getAllPhotoFolder(PhotoSelectActivity.this, mSelectPhotoMap);
                //文件夹集合
                mAllPhotoFolderList.addAll(allFolderList);

                mCurrentList.clear();
                if (allFolderList.size() > 0) {
                    if (allFolderList.get(0).getPhotoInfoList() != null) {
                        //多加一张拍照的图片
                        setAllPhotoList(allFolderList);
                        //设置选择的文件夹是 全部图片文件夹
                        mFolderListAdapter.setmSelectPhotoFolderInfo(allFolderList.get(0));
                    }
                }
                refreshAdapter();
            }
        }.start();

    }

    //添加第一个文件夹中的所有图片   全部图片
    private void setAllPhotoList(List<PhotoFolderInfo> allFolderList) {
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoId(-100);
        photoInfo.setPhotoPath(null);
        photoInfo.setDrawable(R.drawable.photo_camera);
        mCurrentList.add(photoInfo);
        mCurrentList.addAll(allFolderList.get(0).getPhotoInfoList());
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lv_folder_list) {
            onItemClickForFolderList(position);
        } else {
            onItemClickForPhotoList(view, position);
        }
    }


    private void onItemClickForFolderList(int position) {
        PhotoFolderInfo photoFolderInfo = mAllPhotoFolderList.get(position);
        tv_photo_folder.setText(photoFolderInfo.getFolderName());
        ll_folder_panel.setVisibility(View.GONE);
        mCurrentList.clear();
        if (position == 0) {
            setAllPhotoList(mAllPhotoFolderList);
        } else {
            mCurrentList.addAll(photoFolderInfo.getPhotoInfoList());
        }
        mFolderListAdapter.setmSelectPhotoFolderInfo(photoFolderInfo);

        mPhotoListAdapter.notifyDataSetChanged();
        mFolderListAdapter.notifyDataSetChanged();
        gv_photo_list.smoothScrollToPosition(0);
        if (mCurrentList.size() == 0) {
            tv_empty_view.setText("没有照片");
        }

    }


    private void onItemClickForPhotoList(View view, int position) {
        PhotoListAdapter.PhotoViewHolder holder = (PhotoListAdapter.PhotoViewHolder) view.getTag();
        PhotoInfo photoInfo = mCurrentList.get(position);
        if (photoInfo.getPhotoId() != -100 && photoInfo.getDrawable() == 0) {
            if (mSelectPhotoMap.containsKey(photoInfo.getPhotoPath())) {
                // selected
                mSelectPhotoMap.remove(photoInfo.getPhotoPath());
                if (holder != null) holder.iv_check.setSelected(false);
            } else {
                // un selected
                if (mSelectPhotoMap.size() == mFunctionConfig.getMaxSize()) {
                    Toast.makeText(this, "最多只能选择" + PhotoFinal.getFunctionConfig().getMaxSize(), Toast.LENGTH_SHORT).show();
                    return;
                }
                mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
                if (holder != null) holder.iv_check.setSelected(true);
            }
        } else {
            PhotoSelectActivityPermissionsDispatcher.openCameraWithCheck(this);
        }
        refreshSelectCount();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoSelectActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void openCamera() {
        //打开处理拍照的Activity
        PhotoFinal.openCamera(PhotoFinal.getCallback(), mCallback);
    }


    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Toast.makeText(this, "重新授权", Toast.LENGTH_SHORT).show();
    }


    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void callback() {
        mAllPhotoFolderList.clear();
        mSelectPhotoMap.clear();
        this.finish();

    }
}
