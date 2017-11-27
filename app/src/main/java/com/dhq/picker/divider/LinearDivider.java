package com.dhq.picker.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * DESC RecyclerViewDivider工具类
 * Created by douhaoqiang on 2017/11/17.
 */

public class LinearDivider extends DividerFactory {

    private final Rect mBounds = new Rect();
    private final Drawable mDivider;
    private final int mStrokeWidth;
    private final int mStrokeHeight;
    private final boolean mHideLastDivider;
    private int orientation = VERTICAL;

    private int lastPosition = -1;

    public LinearDivider(Builder builder) {
        this.mDivider = builder.getDrawable();
        this.mStrokeWidth = builder.getHorizontalSpace();
        this.mStrokeHeight = builder.getVerticalSpace();
        this.mHideLastDivider = builder.isHideLastDivider();
    }

    @Override
    public void drawDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        //判断是否是LinearLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == HORIZONTAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }


    @Override
    public void calculateItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        lastPosition = parent.getAdapter().getItemCount() - 1;
        final int position = getAdapterPosition(view);
        if (mDivider == null && null == getDivider()) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        if (orientation == HORIZONTAL) {
            outRect.set(0, 0, getIntrinsicWidth(position), 0);
        } else {
            outRect.set(0, 0, 0, getIntrinsicHeight(position));
        }
    }


    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left;
        int right;

        final int childCount = mHideLastDivider ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int position = getAdapterPosition(child);
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();

            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - getIntrinsicHeight(position);

            Drawable drawable = getDivider();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int top;
        int bottom;

        final int childCount = mHideLastDivider ? parent.getChildCount() - 1 : parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int position = getAdapterPosition(child);

            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
                    bottom);

            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - getIntrinsicWidth(position);
            Drawable drawable = getDivider();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
        canvas.restore();
    }


    private int getAdapterPosition(View view) {
        return ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
    }

    private Drawable getDivider() {
        return mDivider;

    }

    private int getIntrinsicHeight(int position) {
        if ((mHideLastDivider && position == lastPosition)) {
            return 0;
        }

        if (mDivider instanceof ColorDrawable) {
            return mStrokeHeight;
        }
        return mDivider.getIntrinsicHeight();
    }

    private int getIntrinsicWidth(int position) {
        if (mHideLastDivider && position == lastPosition) {
            return 0;
        }
        if (mDivider instanceof ColorDrawable) {
            return mStrokeWidth;
        }
        return mDivider.getIntrinsicWidth();
    }


}