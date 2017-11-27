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

public class MulImageView<T> extends LinearLayout {

    private RecyclerView mRecyclerView;
    private NineGridAdapter<T> mAdapter;

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
        inflate(context, R.layout.multi_image_view, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mul_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        GridDivider itemDecoration = DividerFactory.builder(context)
                .setSpaceColor(R.color.divider, R.dimen.divider_stroke_width)
//                .setDrawable(R.drawable.divider)
//                .setHideLastDivider(false)
                .buildGridDivider();
        itemDecoration.addTo(mRecyclerView);

    }


    /**
     * 设置显示适配器
     *
     * @param maxCount 图片显示的最大数量
     * @param listener 数据处理监听器
     */
    public void setListener(int maxCount, NineListener listener) {
        mAdapter = new NineGridAdapter<T>(maxCount, listener);
        mRecyclerView.setAdapter(mAdapter);
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
    public interface NineListener<T> {
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
         * @param imageView
         * @param position
         */
        void addImg(ImageView imageView, int position);
    }


}
