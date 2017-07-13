package com.dhq.picker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.dhq.picker.entity.Photo;
import com.dhq.picker.event.OnItemCheckListener;
import com.dhq.picker.fragment.ImagePagerFragment;
import com.dhq.picker.fragment.PhotoPickerFragment;


public class PhotoPickerActivity extends AppCompatActivity {

    private PhotoPickerFragment pickerFragment;
    private ImagePagerFragment imagePagerFragment;

    private int maxCount = PhotoPicker.DEFAULT_MAX_COUNT;


    private int columnNumber = PhotoPicker.DEFAULT_COLUMN_NUMBER;
    private ArrayList<String> originalPhotos = null;

    private TextView downCountTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_picker_lay);

        boolean showCamera = getIntent().getBooleanExtra(PhotoPicker.EXTRA_SHOW_CAMERA, true);//是否展示相机

        boolean previewEnabled = getIntent().getBooleanExtra(PhotoPicker.EXTRA_PREVIEW_ENABLED, true);//是否可以预览

        maxCount = getIntent().getIntExtra(PhotoPicker.EXTRA_MAX_COUNT, PhotoPicker.DEFAULT_MAX_COUNT);//选择的最大张数
        columnNumber = getIntent().getIntExtra(PhotoPicker.EXTRA_GRID_COLUMN, PhotoPicker.DEFAULT_COLUMN_NUMBER);//展示的列数
        originalPhotos = getIntent().getStringArrayListExtra(PhotoPicker.EXTRA_ORIGINAL_PHOTOS);//原始的图片

        downCountTv = (TextView) findViewById(R.id.toolbar_done_tv);//已经选择的张数

        pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
        if (pickerFragment == null) {
            pickerFragment = PhotoPickerFragment.newInstance(showCamera, previewEnabled, columnNumber, maxCount, originalPhotos);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, pickerFragment, "tag")
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


        downCountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                if(selectedPhotos!=null || selectedPhotos.size()>0){
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS, selectedPhotos);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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

}
