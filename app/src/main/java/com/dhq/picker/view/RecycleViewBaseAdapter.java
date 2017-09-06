package com.dhq.picker.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

/**
 * DESC RecycleView 的公用adapter
 * Created by douhaoqiang on 2016/9/6.
 */

public abstract class RecycleViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<T> datas = new ArrayList<>();
    private int layoutId;

    /**
     * @param itemId 单个item的布局文件id
     */
    public RecycleViewBaseAdapter(int itemId) {
        this.layoutId = itemId;
    }

    /**
     * 设置列表数据
     *
     * @param datas 列表数据
     */
    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param data 列表数据
     */
    public void addData(T data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据到一个位置
     *
     * @param data 列表数据
     */
    public void addData(T data, int position) {
        this.datas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 添加多个数据
     *
     * @param datas 要添加的多个数据
     */
    public void addDatas(ArrayList<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 移除单个item
     *
     * @param position 要移除的item下标
     */
    public void removeData(int position) {
        this.datas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 拖拽交换数据
     *
     * @param dragPosition   拖拽的item下标
     * @param targetPosition 要交换的item的下标
     */
    public void changeItem(int dragPosition, int targetPosition) {
        Collections.swap(datas, dragPosition, targetPosition);
        notifyItemMoved(dragPosition, targetPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return new RecycleViewBaseHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((RecycleViewBaseHolder) holder, datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public abstract void convert(RecycleViewBaseHolder holder, T item, int position);

}
