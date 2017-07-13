package com.dhq.picker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dhq.picker.pickutil.ImagePickUtils;
import com.dhq.pickerdemo.R;

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
                ImagePickUtils.pickPic(MainActivity.this, new ImagePickUtils.PickSingleCallBack() {

                    @Override
                    public void result(String imgPath, Bitmap picBitmap) {
                        imageView.setImageBitmap(picBitmap);
                    }
                });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImagePickUtils.pickPicResult(requestCode, resultCode, data);

    }


    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void toastMsg(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }
}
