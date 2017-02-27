package cn.liweiqin.testselectphoto.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.liweiqin.testselectphoto.core.PhotoFinal;
import cn.liweiqin.testselectphoto.R;
import cn.liweiqin.testselectphoto.model.PhotoFolderInfo;
import cn.liweiqin.testselectphoto.model.PhotoInfo;

/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoUtil {

    private static Drawable defaultDrawable;

    private static final String[] projectionPhotos = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Thumbnails.DATA
    };

    public static void display(Activity activity, String path, ImageView imageView, int width, int height) {
        if (isNotNull(activity, imageView)) return;
        imageView.setImageResource(R.drawable.ic_gf_default_photo);
        if (null == path) {
            Glide.with(activity).load(R.drawable.ic_gf_default_photo).into(imageView);
        } else {
            Glide.with(activity)
                    .load(new File(path))
                    .placeholder(R.drawable.ic_gf_default_photo)
                    .error(defaultDrawable)
                    .override(width, height)
                    .centerCrop()
                    .into(imageView);
        }

    }

    public static void display(Activity activity, int drawable, ImageView imageView, int width, int height) {
        if (isNotNull(activity, imageView)) return;
        imageView.setImageResource(R.drawable.ic_gf_default_photo);
        if (0 == drawable) {
            Glide.with(activity).load(R.drawable.ic_gf_default_photo).into(imageView);
        } else {
            Glide.with(activity)
                    .load(drawable)
                    .error(defaultDrawable)
                    .override(width, height)
                    .centerCrop()
                    .into(imageView);
        }

    }

    private static boolean isNotNull(Activity activity, ImageView imageView) {
        if (null == activity || null == imageView)
            return true;
        if (null == defaultDrawable)
            getDefaultDrawable(activity);
        return false;
    }

    private static void getDefaultDrawable(Activity activity) {
        defaultDrawable = activity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
    }


    /**
     * 获取所有图片
     *
     * @param activity
     * @param mSelectPhotoMap
     * @return
     */
    public static List<PhotoFolderInfo> getAllPhotoFolder(Activity activity, HashMap<String, PhotoInfo> mSelectPhotoMap) {
        //所有的文件夹
        List<PhotoFolderInfo> allFolderList = new ArrayList<>();
        //保存选择图片List
        List<String> selectedList = PhotoFinal.getFunctionConfig().getSelectedList();

        //图片文件夹
        ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
        //记录 图片文件夹 与 文件夹ID
        HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();

        Cursor cursor = null;
        //第一个文件夹 所有照片   所有图片的文件夹
        PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
        allPhotoFolderInfo.setFolderId(0);
        allPhotoFolderInfo.setFolderName("所有照片");
        allPhotoFolderInfo.setPhotoInfoList(new ArrayList<PhotoInfo>());

        allPhotoFolderList.add(allPhotoFolderInfo);

        //ContentResolver  查询数据库中的图片资源
        try {
            cursor = MediaStore.Images.Media.query(activity.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    //数据库Cursor取字段
                    final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    //int thumbImageColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    //final String thumb = cursor.getString(thumbImageColumn);
                    File file = new File(path);
                    final PhotoInfo photoInfo = new PhotoInfo();
                    photoInfo.setPhotoId(imageId);
                    photoInfo.setPhotoPath(path);
                    //photoInfo.setThumbPath(thumb);
                    if (allPhotoFolderInfo.getCoverPhoto() == null) {
                        //封面图片
                        allPhotoFolderInfo.setCoverPhoto(photoInfo);
                    }
                    //添加到 所有图片文件夹
                    allPhotoFolderInfo.getPhotoInfoList().add(photoInfo);

                    //通过bucketId获取文件夹
                    PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);

                    if (photoFolderInfo == null) {
                        //设置文件夹
                        photoFolderInfo = new PhotoFolderInfo();
                        photoFolderInfo.setPhotoInfoList(new ArrayList<PhotoInfo>());
                        photoFolderInfo.setFolderId(bucketId);
                        photoFolderInfo.setFolderName(bucketName);
                        photoFolderInfo.setCoverPhoto(photoInfo);
                        bucketMap.put(bucketId, photoFolderInfo);
                        //添加到所有的文件夹
                        allPhotoFolderList.add(photoFolderInfo);
                    }
                    //图片添加到对应文件夹
                    photoFolderInfo.getPhotoInfoList().add(photoInfo);


                    if (selectedList != null && selectedList.size() > 0 && selectedList.contains(path)) {
                        //将之前选择的图片添加到 集合中
                        mSelectPhotoMap.put(path, photoInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //加入 全部图片 文件夹
        allFolderList.addAll(allPhotoFolderList);
        if (selectedList != null) {
            selectedList.clear();
        }
        return allFolderList;
    }


}
