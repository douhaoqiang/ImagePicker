package com.dhq.picker.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dhq.pickerdemo.R;

/**
 * DESC
 * Created by douhaoqiang on 2017/9/6.
 */

public class MulImgView<T> extends FrameLayout {

    private RecyclerView mRecyclerView;



    public MulImgView(Context context) {
        super(context,null);
    }

    public MulImgView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MulImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView(){

        inflate(getContext(),R.layout.multi_image_view,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mul_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        mRecyclerView.setAdapter(new RecycleViewBaseAdapter<T>(R.layout.item_mult_view) {
            @Override
            public void convert(RecycleViewBaseHolder holder, T item, int position) {

            }
        });

    }

}
