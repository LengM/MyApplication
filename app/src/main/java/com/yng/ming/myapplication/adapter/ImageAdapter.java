package com.yng.ming.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yng.ming.myapplication.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class ImageAdapter extends SuperAdapter<String> {

    private Context context;

    public ImageAdapter(Context context, List<String> items, @LayoutRes int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        Glide.with(context).load(item).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                holder.setImageDrawable(R.id.task_image, resource);
            }
        });
    }

}
