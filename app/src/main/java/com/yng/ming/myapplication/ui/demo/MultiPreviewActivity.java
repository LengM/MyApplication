package com.yng.ming.myapplication.ui.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.util.img.BitmapUtil;

import java.util.ArrayList;

import butterknife.Bind;
import me.kareluo.intensify.image.IntensifyImage;
import me.kareluo.intensify.image.IntensifyImageView;

/**
 * 多图预览
 * https://github.com/kareluo/IntensifyImageView
 */
public class MultiPreviewActivity extends BaseActivity {

    @Bind(R.id.vp_pager)
    ViewPager mViewPager;

    private ImagePageAdapter mAdapter;

    private static final String PIC_DIR = "pictures";

    private ArrayList<String> imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);

        Bundle bundle = getIntent().getExtras();
        imgList = bundle.getStringArrayList("imgList");
        int index = bundle.getInt("index");

        mAdapter = new ImagePageAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index);
    }

    private class ImagePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            final IntensifyImageView imageView = new IntensifyImageView(container.getContext());
            imageView.setScaleType(IntensifyImage.ScaleType.FIT_AUTO);
            Glide.with(MultiPreviewActivity.this).load(imgList.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImage(BitmapUtil.saveBitmap(resource, System.currentTimeMillis() + "pic", PIC_DIR));
                    container.addView(imageView);
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
