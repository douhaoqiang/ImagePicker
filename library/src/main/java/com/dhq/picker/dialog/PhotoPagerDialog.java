package com.dhq.picker.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.dhq.picker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC 图片展示
 * Created by douhaoqiang on 2017/9/11.
 */

public class PhotoPagerDialog extends DialogFragment {


    private static CallBack mCallBack;
    //    private float dimAmount = 0.5f;//灰度深浅
    private boolean outCancel = true;//是否点击外部取消
    private PhotoAdapter<String> mPagerAdapter;
    private ViewPager mViewPager;
    private List<String> mPaths;


    public static PhotoPagerDialog getInstance(ArrayList<String> paths,CallBack callBack){
        mCallBack=callBack;
        PhotoPagerDialog photoPagerDialog = new PhotoPagerDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos",paths);
        photoPagerDialog.setArguments(bundle);
        return photoPagerDialog;
    }


//    public abstract void convertView(ViewHolder holder, PhotoPagerDialog dialog);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo_pager, container, false);
//        convertView(ViewHolder.create(view), this);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        convertView(ViewHolder.create(getView()), this);

        mPaths= (List<String>) getArguments().getSerializable("photos");

        mViewPager = (ViewPager) getView().findViewById(R.id.vp_photos);
        mPagerAdapter = new PhotoAdapter<>(Glide.with(getActivity()), mPaths, new PhotoAdapter.PagerCallBack<String>() {
            @Override
            public String getImagePath(String data) {
                return data;
            }
        });
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }


    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
//            lp.dimAmount = dimAmount;

            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }


    public PhotoPagerDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }


    public static class ViewHolder {

        private SparseArray<View> views;
        private View convertView;

        private ViewHolder(View view) {
            convertView = view;
            views = new SparseArray<>();
        }

        public static ViewHolder create(View view) {
            return new ViewHolder(view);
        }

        public <T extends View> T getView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return convertView;
        }


    }


    public interface CallBack{

        void delectImage(int position);

    }


}
