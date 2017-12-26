package com.dhq.picker.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dhq.picker.divider.DividerFactory;
import com.dhq.picker.divider.GridDivider;
import com.dhq.pickerdemo.R;

import java.util.List;

/**
 * DESC 九宫格图片展示
 * Created by douhaoqiang on 2017/9/6.
 */

public class NineImageView<T> extends LinearLayout {

    private RecyclerView mRecyclerView;
    private GridAdapter<T> mAdapter;
    private int mColumnCount = 3;//图片显示列数

    public NineImageView(Context context) {
        this(context, null);
    }

    public NineImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        inflate(context, R.layout.multi_image_view, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mul_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));

        DividerFactory.Builder builder = DividerFactory.builder(context)
                .setSpaceColor(android.R.color.transparent)
                .setSpace(R.dimen.divider_stroke_width);
        GridDivider itemDecoration = builder.buildGridDivider();
        itemDecoration.addTo(mRecyclerView);

    }


    /**
     * 设置显示适配器
     */
    public void setAdapter(GridAdapter gridAdapter) {
        this.mAdapter = gridAdapter;
        mRecyclerView.setAdapter(gridAdapter);
    }


    public void addData(T data) {
        mAdapter.addData(data);
    }

    public void addDatas(List<T> datas) {
        mAdapter.addDatas(datas);
    }

    public void setDatas(List<T> datas) {
        mAdapter.setDatas(datas);
    }

    public List<T> getDatas() {
        return mAdapter.getDatas();
    }


    /**
     * 九宫格数据监听
     */
    public interface ImageListener<T> {
        /**
         * 显示有图片的位置
         *
         * @param imageView
         * @param item
         * @param position
         */
        void convert(ImageView imageView, T item, int position);

        /**
         * 显示添加图片位置
         *
         * @param imageView 添加图片
         * @param position  位置
         * @param count     现在图片数量
         */
        void addImg(ImageView imageView, int position, int count);
    }


}
