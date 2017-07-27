package com.dhq.picker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhq.picker.PhotoPickerActivity;
import com.dhq.picker.R;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/7/27.
 */

public class PickTypeFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.activity_pick_type, container, false);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView() {
        mRootView.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启相册
                ((PhotoPickerActivity) getActivity()).openAlbum();
            }
        });

        mRootView.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启相机
                ((PhotoPickerActivity) getActivity()).openCarmera();
            }
        });

        mRootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                ((PhotoPickerActivity) getActivity()).onBackPressed();
            }
        });
    }
}
