package com.dhq.picker.view;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * DESC RecycleView的公用ViewHolder
 * Created by douhaoqiang on 2016/9/7.
 */

public class PhotoGridHolder extends RecyclerView.ViewHolder {
    /**
     * save view ids
     */
    private SparseArray<View> mSparseArray;

    private View rootView;

    public PhotoGridHolder(View itemView) {
        super(itemView);
        this.mSparseArray = new SparseArray<>();
        this.rootView = itemView;
    }

    /**
     * 获取跟布局
     *
     * @return
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * 根据id获取控件
     *
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int resId) {
        View view = mSparseArray.get(resId);
        if (view == null) {
            view=rootView.findViewById(resId);
            mSparseArray.put(resId, view);
        }
        return (T) view;
    }

}
