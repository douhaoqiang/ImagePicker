package com.dhq.picker.dialog;

/**
 * DESC
 * Created by douhaoqiang on 2017/9/12.
 */

public interface PhotoPagerCallback<T> {
    /**
     * 获取图片的显示路径
     *
     * @param data
     * @return
     */
    String getImagePath(T data);

    /**
     * 删除图片
     *
     * @param position 移除图片下标
     */
    void removeImage(int position);

}
