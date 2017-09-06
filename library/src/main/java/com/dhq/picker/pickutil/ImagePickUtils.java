package com.dhq.picker.pickutil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.dhq.picker.PhotoPicker;
import com.dhq.picker.PhotoPreview;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * DESC 图片选择辅助类
 * Created by douhaoqiang on 2017/5/12.
 */

public class ImagePickUtils {

    private static Activity _activity;
    public static PickSingleCallBack _singleCallBack;
    public static PickMulCallBack _mulCallBack;
    public static boolean mIsCrop = true;//表示是否需要剪裁（true-需要剪裁, false-不需要剪裁 默认剪裁）
    public static boolean isOnlyOne = true;//表示是否是单选

    private static ImagePickUtils pickPicUtils;

    private ImagePickUtils(Activity activity) {
        this._activity = activity;
    }

    /**
     * 选择照片（单选）
     *
     * @param activity
     * @param callBack 回调
     */
    public static void pickPic(Activity activity, PickSingleCallBack callBack) {
        pickPic(activity, true, callBack);
    }

    /**
     * 选择照片（单选）
     *
     * @param activity
     * @param isCanCrop 是否可以剪裁
     * @param callBack  回调
     */
    public static void pickPic(Activity activity, boolean isCanCrop, PickSingleCallBack callBack) {
        pickPicUtils = new ImagePickUtils(activity);
        _singleCallBack = callBack;
        mIsCrop = isCanCrop;
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowPickType(true)
                .setPreviewEnabled(true)
                .start(_activity);
    }


    /**
     * 选择照片（多选）
     *
     * @param num
     * @param callBack
     */
    public static void pickMulPic(Activity activity, int num, PickMulCallBack callBack) {
        pickPicUtils = new ImagePickUtils(activity);
        if (num < 1) {
            return;
        }

        _mulCallBack = callBack;
        isOnlyOne = false;
        PhotoPicker.builder()
                .setPhotoCount(num)
                .setShowPickType(false)
                .setPreviewEnabled(true)
                .start(_activity);
    }


    /**
     * 剪裁图片
     */
    private static void cropPic(Uri uri) {
        CropUtils.startCropActivity(_activity, uri);
    }


    public interface PickMulCallBack {

        /**
         * 多选图片回调
         *
         * @param pics 图片路径集合
         */
        void result(List<String> pics);

    }

    public interface PickSingleCallBack {

        /**
         * 单选图片回调
         *
         * @param imgPath   图片地址
         * @param picBitmap 图片的Bitmap对象
         */
        void result(String imgPath, Bitmap picBitmap);

    }


}
