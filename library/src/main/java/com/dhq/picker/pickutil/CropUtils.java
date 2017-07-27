package com.dhq.picker.pickutil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * DESC 图片裁剪工具类
 * Created by douhaoqiang on 2017/5/10.
 */

public class CropUtils {


    /**
     * 启动图片剪裁 (默认剪裁比例不可调节 1:1 )
     *
     * @param activity
     * @param uri      图片路径
     */
    public static void startCropActivity(Activity activity, @NonNull Uri uri) {

        startCropActivity(activity, uri, false);
    }

    /**
     * 启动图片剪裁
     *
     * @param activity
     * @param uri
     * @param isFreeScale 剪裁比例是否可调节
     */
    protected static void startCropActivity(Activity activity, @NonNull Uri uri, boolean isFreeScale) {

        Builder builder = Builder.getInstance().setIsFreeScale(isFreeScale);

        startCropActivity(activity, uri, builder);
    }


    /**
     * * 启动图片剪裁
     *
     * @param activity
     * @param uri      图片路径
     * @param builder  剪裁设置
     */
    protected static void startCropActivity(Activity activity, @NonNull Uri uri, Builder builder) {

        //设置剪裁的图片 和 剪裁完保存的图片
        MyUCorp uCrop = MyUCorp.of(uri, getSavePath(activity));

        //设置图片的裁剪宽高比例
        uCrop = uCrop.withAspectRatio(builder.getwScale(), builder.gethScale());

        if (builder.getMaxWidth() != 0 && builder.getMaxHeight() != 0) {
            //设置裁剪的最大尺寸
            uCrop = uCrop.withMaxResultSize(builder.getMaxWidth(), builder.getMaxHeight());
        }

        UCrop.Options options = new UCrop.Options();

        //设置图片的裁剪格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //设置图片的裁剪质量
        options.setCompressionQuality(builder.getCompressionQuality());

        //是否隐藏底部控制菜单
//        options.setHideBottomControls(true);

//        options.setToolbarTitle("");//标题
//        options.setCircleDimmedLayer(true);//是否显示圆形
//        options.setStatusBarColor();
//        options.setToolbarColor();
//        options.setToolbarWidgetColor();

        //是否自由比例裁剪
        options.setFreeStyleCropEnabled(builder.isFreeScale());


        uCrop.withOptions(options);

        uCrop.start(activity);
    }


    private static Uri getSavePath(Activity activity) {

        String cropImgName = "cropImg" + System.currentTimeMillis() + ".jpg";
        return Uri.fromFile(new File(activity.getCacheDir(), cropImgName));
    }


    protected static class Builder {

        private float wScale = 1;//宽比例
        private float hScale = 1;//高比例

        private int maxWidth;//最大宽度
        private int maxHeight;//最大高度

        private boolean isFreeScale = true;//剪切比例是否可调

        private int compressionQuality = 100;//压缩质量[ 0 ~ 100 ]

        private Builder() {
        }

        public static Builder getInstance() {

            return new Builder();
        }


        /**
         * 设置宽高比
         *
         * @param wScale
         * @param hScale
         * @return
         */
        public Builder setWHscale(float wScale, float hScale) {
            this.wScale = wScale;
            this.hScale = hScale;
            return this;
        }

        /**
         * 设置最大宽高尺寸
         *
         * @param widthSize
         * @param heightSize
         * @return
         */
        public Builder setMaxSize(int widthSize, int heightSize) {
            this.maxWidth = widthSize;
            this.maxHeight = heightSize;
            return this;
        }

        /**
         * 是否剪裁比例可调节
         *
         * @param isFreeScale
         * @return
         */
        public Builder setIsFreeScale(boolean isFreeScale) {
            this.isFreeScale = isFreeScale;
            return this;
        }


        /**
         * 设置压缩质量
         *
         * @param compressionQuality 压缩质量 （范围 [0~100]）
         * @return
         */
        public Builder setCompressionQuality(int compressionQuality) {
            this.compressionQuality = compressionQuality;
            return this;
        }


        public float getwScale() {
            return wScale;
        }

        public void setwScale(float wScale) {
            this.wScale = wScale;
        }

        public float gethScale() {
            return hScale;
        }

        public void sethScale(float hScale) {
            this.hScale = hScale;
        }

        public int getMaxWidth() {
            return maxWidth;
        }

        public void setMaxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
        }

        public int getMaxHeight() {
            return maxHeight;
        }

        public void setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
        }

        public boolean isFreeScale() {
            return isFreeScale;
        }

        public void setFreeScale(boolean freeScale) {
            isFreeScale = freeScale;
        }

        public int getCompressionQuality() {
            return compressionQuality;
        }
    }

}
