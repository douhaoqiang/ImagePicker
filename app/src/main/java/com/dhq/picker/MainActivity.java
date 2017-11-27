package com.dhq.picker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dhq.picker.dialog.PhotoPagerCallback;
import com.dhq.picker.dialog.PhotoPagerDialog;
import com.dhq.picker.pickutil.ImagePickUtils;
import com.dhq.picker.view.MulImageView;
import com.dhq.pickerdemo.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MulImageView mulImgView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mulImgView = (MulImageView) findViewById(R.id.mv_grid_image);

        initListener();

    }


    private void initListener() {


        mulImgView.setListener(3, new MulImageView.NineListener<String>() {

            @Override
            public void convert(ImageView imageView, String item, final int position) {
                Glide.with(MainActivity.this).load(item).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoPagerDialog.getInstance(mulImgView.getDatas(), position, new PhotoPagerCallback<String>() {
                            @Override
                            public String getImagePath(String data) {
                                return data;
                            }

                            @Override
                            public void removeImage(int position) {

                            }
                        }).show(getSupportFragmentManager());
                    }
                });
            }

            @Override
            public void addImg(ImageView imageView, int position) {

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ImagePickUtils.pickMulPic(MainActivity.this, 6, new ImagePickUtils.PickMulCallBack() {

                            @Override
                            public void result(List<String> pics) {
                                mulImgView.setDatas(pics);
                            }

                        });

//                        ImagePickUtils.pickPic(MainActivity.this, new ImagePickUtils.PickSingleCallBack() {
//
//                            @Override
//                            public void result(String imgPath, Bitmap picBitmap) {
//                                mulImgView.addData(imgPath);
//                            }
//                        });
                    }
                });
            }
        });


    }


    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void toastMsg(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }
}
