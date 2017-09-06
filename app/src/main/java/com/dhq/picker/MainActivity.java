package com.dhq.picker;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dhq.picker.pickutil.ImagePickUtils;
import com.dhq.pickerdemo.R;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.demo_select_pic_iv);

        findViewById(R.id.demo_select_pic_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePickUtils.pickPic(MainActivity.this, new ImagePickUtils.PickSingleCallBack() {
//
//                    @Override
//                    public void result(String imgPath, Bitmap picBitmap) {
//                        imageView.setImageBitmap(picBitmap);
//                    }
//                });


                ImagePickUtils.pickMulPic(MainActivity.this, 6, new ImagePickUtils.PickMulCallBack() {

                    @Override
                    public void result(List<String> pics) {
                        Glide.with(MainActivity.this).load(Uri.fromFile(new File(pics.get(0)))).into(imageView);
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
