package com.dhq.picker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
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

public class GridImageView<T> extends LinearLayout {

    private RecyclerView mRecyclerView;
    private NineGridAdapter<T> mAdapter;
    private int mMaxCount;//最大数量
    private int mColumnCount;//图片显示列数
    private int mColumnSpace;//列间距
    private int mRowSpace;//行间距
    private int mSpaceColor;//间距颜色
    private boolean mIsHindEdge;//是否显示四周边界线

    public GridImageView(Context context) {
        this(context, null);
    }

    public GridImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GridImageView);
        if (typedArray != null) {
            mMaxCount = typedArray.getInteger(R.styleable.GridImageView_gridImageMax, -1);
            //列数默认3列
            mColumnCount = typedArray.getInteger(R.styleable.GridImageView_gridImageColumn, 3);
            mColumnSpace = typedArray.getDimensionPixelSize(R.styleable.GridImageView_gridImageColumnSpace, 0);
            mRowSpace = typedArray.getDimensionPixelSize(R.styleable.GridImageView_gridImageRowSpace, 0);
            //默认间隙颜色为透明
            mSpaceColor = typedArray.getColor(R.styleable.GridImageView_gridImageSpaceColor, ContextCompat.getColor(getContext(), android.R.color.transparent));
            mIsHindEdge = typedArray.getBoolean(R.styleable.GridImageView_gridImageShowEdge, false);
            mSpaceColor=ContextCompat.getColor(getContext(),R.color.divider2);
        }
        typedArray.recycle();
    }

    private void initView(Context context) {
//        LayoutInflater.from(context).inflate(R.layout.multi_image_view, this, true);
        inflate(context, R.layout.multi_image_view, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_mul_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));

        DividerFactory.Builder builder = DividerFactory.builder(context)
                .setSpaceColor(mSpaceColor)
                .setHideLastDivider(mIsHindEdge);
        if (mColumnSpace != -1) {
            builder.setColumnSpace(mColumnSpace);
        }
        if (mRowSpace != -1) {
            builder.setRowSpace(mRowSpace);
        }
        GridDivider itemDecoration = builder.buildGridDivider();

        itemDecoration.addTo(mRecyclerView);

    }


    /**
     * 设置显示适配器
     *
     * @param maxCount 图片显示的最大数量
     * @param listener 数据处理监听器
     */
    public void setListener(int maxCount, ImageListener listener) {
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
         * @param imageView
         * @param position
         */
        void addImg(ImageView imageView, int position);
    }


}
