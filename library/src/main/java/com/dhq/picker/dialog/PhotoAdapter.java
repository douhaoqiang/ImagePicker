package com.dhq.picker.dialog;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dhq.picker.R;
import com.dhq.picker.utils.AndroidLifecycleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PhotoAdapter<T> extends PagerAdapter {

    private final PhotoPagerCallback mPagerCallBack;
    private List<T> paths = new ArrayList<>();
    private RequestManager mGlide;

    public PhotoAdapter(RequestManager glide, List<T> paths, PhotoPagerCallback pagerCallBack) {
        this.paths = paths;
        this.mGlide = glide;
        this.mPagerCallBack = pagerCallBack;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.__picker_picker_item_pager, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

        String path = "";
        if (mPagerCallBack != null) {
            path = mPagerCallBack.getImagePath(paths.get(position));
        }
        final Uri uri;
        if (path.startsWith("http")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }

        boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(context);

        if (canLoadImage) {
            mGlide.load(uri)
                    .thumbnail(0.1f)
                    .dontAnimate()
                    .dontTransform()
                    .override(800, 800)
                    .placeholder(R.drawable.photo_error)
                    .error(R.drawable.photo_error)
                    .into(imageView);
        }

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (context instanceof Activity) {
//                    if (!((Activity) context).isFinishing()) {
//                        ((Activity) context).onBackPressed();
//                    }
//                }
//            }
//        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        Glide.clear((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void removeItem(int position) {
        paths.remove(position);
        if (mPagerCallBack != null) {
            mPagerCallBack.removeImage(position);
        }
        notifyDataSetChanged();
    }


}
