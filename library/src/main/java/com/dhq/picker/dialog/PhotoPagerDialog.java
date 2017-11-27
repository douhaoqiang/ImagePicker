package com.dhq.picker.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhq.picker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC 图片展示
 * Created by douhaoqiang on 2017/9/11.
 */

public class PhotoPagerDialog<T> extends DialogFragment {


    private static PhotoPagerCallback mCallBack;
    //    private float dimAmount = 0.5f;//灰度深浅
    private boolean outCancel = true;//是否点击外部取消
    private PhotoAdapter<T> mPagerAdapter;
    private ViewPager mViewPager;
    private static List mPaths;
    private static int mIndex;
    private ImageView mIvBack;
    private TextView mTvDelect;


    public static PhotoPagerDialog getInstance(List paths, PhotoPagerCallback callBack) {
        return getInstance(paths,0,callBack);
    }

    public static PhotoPagerDialog getInstance(List paths,int showIndex, PhotoPagerCallback callBack) {
        mCallBack = callBack;
        mPaths = paths;
        mIndex = showIndex;
        PhotoPagerDialog photoPagerDialog = new PhotoPagerDialog();
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

        mIvBack = (ImageView) getView().findViewById(R.id.iv_photo_header_back);
        mViewPager = (ViewPager) getView().findViewById(R.id.vp_photos);
        mTvDelect = (TextView) getView().findViewById(R.id.tv_photo_header_delect);


        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTvDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPaths.size() == 0) {
                    dismiss();
                }

                final int currentPosition = mViewPager.getCurrentItem();
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.__picker_confirm_to_delete)
                        .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                mPagerAdapter.removeItem(currentPosition);
                                mCallBack.removeImage(currentPosition);
                                if (mPaths.size() == 0) {
                                    dismiss();
                                }
                            }
                        })
                        .setNegativeButton(R.string.__picker_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();


            }
        });

        mPagerAdapter = new PhotoAdapter<>(Glide.with(getActivity()), mPaths, mCallBack);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mIndex);
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

    @Override
    public void dismiss() {
        super.dismiss();
        mCallBack = null;
        mPaths.clear();
        mPaths = null;
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


}
