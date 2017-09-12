package com.dhq.picker.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.dhq.pickerdemo.R;

/**
 * DESC 九宫格图片展示
 * Created by douhaoqiang on 2017/9/6.
 */

public class MulImageView<T> extends LinearLayout {

    private RecyclerView mRecyclerView;
    private PhotoGridAdapter mAdapter;

    public MulImageView(Context context) {
        super(context);
        initView(context);
    }

    public MulImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MulImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
//        LayoutInflater.from(context).inflate(R.layout.multi_image_view, this, true);
        inflate(getContext(), R.layout.multi_image_view, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mul_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


    }

    /**
     * 设置显示适配器
     *
     * @param adapter
     */
    public void setAdapter(PhotoGridAdapter adapter) {
        this.mAdapter=adapter;
        mRecyclerView.setAdapter(adapter);
    }


    public void addData(T data){
        mAdapter.addData(data);
    }

}
