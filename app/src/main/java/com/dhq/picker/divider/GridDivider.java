package com.dhq.picker.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * DESC RecyclerViewDivider工具类
 * Created by douhaoqiang on 2017/11/17.
 */

public class GridDivider extends DividerFactory {

    private final Rect mBounds = new Rect();
    private final Drawable mDivider;
    private final int mColumnSpace;
    private final int mRowSpace;
    private final boolean mHideRoundDivider;//是否显示四周分割线
    private int mSpanCount = 1;
    private int mItemWidth;
    private int mItemHeight;

    public GridDivider(Builder builder) {
        this.mDivider = builder.getDrawable();
        this.mColumnSpace = builder.getColumnSpace();
        this.mRowSpace = builder.getRowSpace();
        this.mHideRoundDivider = builder.isHideLastDivider();
//        this.mHideRoundDivider = false;
    }

    @Override
    public void drawDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        //判断是否是LinearLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            //获取列数
            mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();

            drawSpace(c, parent);

        }
    }


    @Override
    public void calculateItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        mSpanCount = layoutManager.getSpanCount();

        final int position = parent.getChildAdapterPosition(view);

        int column = position % mSpanCount; // item column

        if (mHideRoundDivider) {
            outRect.left = column * mColumnSpace / mSpanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = mColumnSpace - (column + 1) * mColumnSpace / mSpanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= mSpanCount) {
                outRect.top = mColumnSpace; // item top
            }
        } else {
            outRect.left = mColumnSpace - column * mColumnSpace / mSpanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * mColumnSpace / mSpanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < mSpanCount) { // top edge
                outRect.top = mColumnSpace;
            }
            outRect.bottom = mColumnSpace; // item bottom
        }


    }


    private void drawSpace(Canvas canvas, RecyclerView parent) {


    }


    private int getAdapterPosition(View view) {
        return ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
    }

    private Drawable getDivider() {
        return mDivider;

    }


    private int getLeftSpaceWidth(int position) {
        if (position % mSpanCount == 0) {
            return getEdgeWidth();
        }
        return getColumnSpace() / 2;
    }

    private int getTopSpaceWidth(int position) {
        if (position < mSpanCount) {
            return getEdgeHeight();
        }
        return getRowSpace() / 2;
    }

    private int getRightSpaceWidth(int position) {
        if (position % mSpanCount == mSpanCount - 1) {
            return getEdgeWidth();
        }
        return getColumnSpace() / 2;
    }

    private int getBottomSpaceWidth(int totalCount, int position) {
        int totalRow = (totalCount - 1) / mSpanCount + 1;
        int currentRow = position / mSpanCount + 1;
        if (totalRow == currentRow) {
            return getEdgeWidth();
        }
        return getRowSpace() / 2;
    }


    /**
     * 获取分割线宽度
     *
     * @return
     */
    private int getColumnSpace() {

        if (mDivider instanceof ColorDrawable) {
            return mColumnSpace;
        }
        return mDivider.getIntrinsicWidth();
    }

    /**
     * 获取分割线高度
     *
     * @return
     */
    private int getRowSpace() {

        if (mDivider instanceof ColorDrawable) {
            return mRowSpace;
        }
        return mDivider.getIntrinsicHeight();
    }

    /**
     * 获取边缘分割线宽度
     *
     * @return
     */
    private int getEdgeWidth() {
        if (mHideRoundDivider) {
            return 0;
        }

        return getColumnSpace();
    }

    /**
     * 获取边缘分割线高度
     *
     * @return
     */
    private int getEdgeHeight() {

        if (mHideRoundDivider) {
            return 0;
        }
        return getRowSpace();
    }

}