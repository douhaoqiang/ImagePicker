package com.dhq.picker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhq.picker.entity.Photo;
import com.dhq.picker.event.OnItemCheckListener;
import com.dhq.picker.fragment.ImagePagerFragment;
import com.dhq.picker.fragment.PhotoPickerFragment;
import com.dhq.picker.fragment.PickTypeFragment;
import com.dhq.picker.pickutil.CropUtils;
import com.dhq.picker.pickutil.ImagePickUtils;
import com.dhq.picker.pickutil.MyUCorp;
import com.dhq.picker.utils.ImageCaptureManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PhotoPickerActivity extends AppCompatActivity {

    private PhotoPickerFragment pickerFragment;
    private ImagePagerFragment imagePagerFragment;

    private int maxCount = PhotoPicker.DEFAULT_MAX_COUNT;

    private boolean showPickType = true;//是否显示选择方式(默认展示)
    private boolean previewEnabled;//是否可以预览

    private int columnNumber = PhotoPicker.DEFAULT_COLUMN_NUMBER;
    private ArrayList<String> originalPhotos = null;

    private View mHeaderView;
    private TextView downCountTv;
    private ImageCaptureManager captureManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_picker_lay);

        showPickType = getIntent().getBooleanExtra(PhotoPicker.EXTRA_SHOW_PICK_TYPE, true);//是否展示相机

        previewEnabled = getIntent().getBooleanExtra(PhotoPicker.EXTRA_PREVIEW_ENABLED, true);//是否可以预览

        maxCount = getIntent().getIntExtra(PhotoPicker.EXTRA_MAX_COUNT, PhotoPicker.DEFAULT_MAX_COUNT);//选择的最大张数
        columnNumber = getIntent().getIntExtra(PhotoPicker.EXTRA_GRID_COLUMN, PhotoPicker.DEFAULT_COLUMN_NUMBER);//展示的列数
        originalPhotos = getIntent().getStringArrayListExtra(PhotoPicker.EXTRA_ORIGINAL_PHOTOS);//原始的图片

        mHeaderView = findViewById(R.id.header_pick_img);//header view
        ImageView ivBack = (ImageView) findViewById(R.id.toolbar_back_iv);//返回按钮
        downCountTv = (TextView) findViewById(R.id.toolbar_done_tv);//已经选择的张数（完成）

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        downCountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                if (selectedPhotos != null && selectedPhotos.size() > 0) {

                    if (ImagePickUtils.isOnlyOne) {
                        //表示是单选
                        singleImageHandle(selectedPhotos.get(0));

                    } else if (ImagePickUtils._mulCallBack != null) {
                        try {
                            ImagePickUtils._mulCallBack.result(selectedPhotos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();
                    }

                } else {
                    showToastMsg("请先选择图片");
                }
            }
        });
        captureManager = new ImageCaptureManager(getActivity());
        if (showPickType) {
            mHeaderView.setVisibility(View.GONE);
            PickTypeFragment pickTypeFragment = (PickTypeFragment) getSupportFragmentManager().findFragmentByTag("pickType");

            if (pickTypeFragment == null) {
                pickTypeFragment = new PickTypeFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, pickTypeFragment, "pickType")
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        } else {
            openAlbum();
        }

    }


    /**
     * 直接开启相机拍照
     */
    public void openCarmera() {
        try {
            Intent intent = captureManager.getTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            // TODO No Activity Found to handle Intent
            e.printStackTrace();
        }
    }

    /**
     * 前往相册选择图片
     */
    public void openAlbum() {
        mHeaderView.setVisibility(View.VISIBLE);
        pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("pick");

        if (pickerFragment == null) {
            pickerFragment = PhotoPickerFragment.newInstance(!showPickType, previewEnabled, columnNumber, maxCount, originalPhotos);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, pickerFragment, "pick")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean onItemCheck(int position, Photo photo, final int selectedItemCount) {

                downCountTv.setEnabled(selectedItemCount > 0);

                if (maxCount <= 1) {
                    List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
                    if (!photos.contains(photo.getPath())) {
                        photos.clear();
                        pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
                    }
                    return true;
                }

                if (selectedItemCount > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                downCountTv.setText(getString(R.string.__picker_done_with_count, selectedItemCount, maxCount));
                return true;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO) {
            //拍照返回
            if (captureManager == null) {
                captureManager = new ImageCaptureManager(getActivity());
            }
            String currentPhotoPath = captureManager.getCurrentPhotoPath();
            if (TextUtils.isEmpty(currentPhotoPath)) {
                return;
            }

            singleImageHandle(currentPhotoPath);


        } else if (requestCode == MyUCorp.REQUEST_CROP) {
            //剪切结果
            final Uri resultUri = UCrop.getOutput(data);

            if (ImagePickUtils._singleCallBack != null) {
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    ImagePickUtils._singleCallBack.result(resultUri.getPath(), bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            finish();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    /**
     * 单选图片处理
     * @param path
     */
    private void singleImageHandle(String path){

        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);

        if(ImagePickUtils.mIsCrop){
            //启动剪裁
            CropUtils.startCropActivity(getActivity(), contentUri);
        }else if(ImagePickUtils._singleCallBack!=null){
            try {
                Bitmap bmp= MediaStore.Images.Media.getBitmap(getContentResolver(), contentUri);
                ImagePickUtils._singleCallBack.result(contentUri.getPath(), bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
    }



    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it complete.
     */
    @Override
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            imagePagerFragment.runExitAnimation(new Runnable() {
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 打开图片预览
     *
     * @param imagePagerFragment
     */
    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
        this.imagePagerFragment = imagePagerFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, this.imagePagerFragment)
                .addToBackStack(null)
                .commit();
    }


    public PhotoPickerActivity getActivity() {
        return this;
    }


    private void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
