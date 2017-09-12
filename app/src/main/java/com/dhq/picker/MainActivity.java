package com.dhq.picker;

import android.graphics.Bitmap;
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
import com.dhq.picker.view.PhotoGridAdapter;
import com.dhq.pickerdemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private MulImageView mulImgView;


    public static String[] imgUrls = {
            "http://image.tianjimedia.com/uploadImages/2014/308/50/G95HTB33CM50_1000x500.jpg",
            "http://p4.yokacdn.com/pic/life/sex/2013/U355P41T8D134686F231DT20130105181832_maxw808.png",
            "http://img.cnmo-img.com.cn/241_800x600/240813.jpg",
            "http://img.gstv.com.cn/material/news/img/640x/2015/04/2015041015122756LR.jpg",
            "http://ww3.sinaimg.cn/large/610dc034jw1f070hyadzkj20p90gwq6v.jpg",
            "http://img.popoho.com/UploadPic/2011-10/20111024132221149.jpg",
            "http://ws3.cdn.caijing.com.cn/2013-10-08/113380221.jpg",
            "http://www.yoka.com/dna/pics/ba1caeb9/97/d35cce9cdb3b11bac9.jpg"
    };
    private PhotoGridAdapter<String> photoGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.demo_select_pic_iv);
        mulImgView = (MulImageView) findViewById(R.id.mv_grid_image);

        findViewById(R.id.demo_select_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePickUtils.pickPic(MainActivity.this, new ImagePickUtils.PickSingleCallBack() {

                    @Override
                    public void result(String imgPath, Bitmap picBitmap) {
                        imageView.setImageBitmap(picBitmap);
                    }
                });


//                ImagePickUtils.pickMulPic(MainActivity.this, 6, new ImagePickUtils.PickMulCallBack() {
//
//                    @Override
//                    public void result(List<String> pics) {
//                        Glide.with(MainActivity.this).load(Uri.fromFile(new File(pics.get(0)))).into(imageView);
//                    }
//
//                });


            }
        });

        findViewById(R.id.demo_pager_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> list = new ArrayList<String>();
                for (String imgUrl : imgUrls) {
                    list.add(imgUrl);
                }

                PhotoPagerDialog.getInstance(list, new PhotoPagerCallback<String>() {
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

        initListener();

    }


    private void initListener() {

        photoGridAdapter = new PhotoGridAdapter<String>(3) {
            @Override
            public void convert(ImageView imageView, String item, final int position) {
                Glide.with(MainActivity.this).load(item).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoPagerDialog.getInstance(photoGridAdapter.getDatas(),position, new PhotoPagerCallback<String>() {
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
                imageView.setImageResource(R.drawable.__picker_camera);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePickUtils.pickPic(MainActivity.this, new ImagePickUtils.PickSingleCallBack() {

                            @Override
                            public void result(String imgPath, Bitmap picBitmap) {
                                mulImgView.addData(imgPath);
                            }
                        });
                    }
                });
            }
        };
        mulImgView.setAdapter(photoGridAdapter);
    }


    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void toastMsg(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }
}
