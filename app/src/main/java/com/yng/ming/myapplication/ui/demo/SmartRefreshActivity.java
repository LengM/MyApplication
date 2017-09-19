package com.yng.ming.myapplication.ui.demo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.adapter.SmartRefreshAdapter;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.model.SmartRefreshBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 下拉刷新上拉加载
 * https://github.com/scwang90/SmartRefreshLayout
 */
@ParallaxBack(edge = ParallaxBack.Edge.LEFT)
public class SmartRefreshActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;

    SmartRefreshAdapter adapter;
    List<SmartRefreshBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_refresh_layout);
        setToolbar();
        initRefresh();
    }

    private void setToolbar() {
        setTitleText("下拉刷新上拉加载");
    }

    private void initDate(int i, int j) {
        for (; i < j; i++) {
            SmartRefreshBean bean = new SmartRefreshBean();
            bean.setName("魔法少女");
            bean.setPhone(String.valueOf(i));
            list.add(bean);
        }
    }

    private void initRefresh() {
        adapter = new SmartRefreshAdapter(SmartRefreshActivity.this, list, R.layout.list_smart_refresh_item);
        smartRefresh.setRefreshHeader(new MyHeader(this));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefresh.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        initDate(0, 20);
                        adapter.notifyDataSetHasChanged();
                        smartRefresh.finishRefresh();
                        smartRefresh.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });
        smartRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefresh.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initDate(adapter.getItemCount(), adapter.getItemCount() + 20);
                        adapter.notifyDataSetHasChanged();
                        smartRefresh.finishLoadmore();
                    }
                }, 2000);
            }
        });
        listView.setAdapter(adapter);
    }

    /**
     * 自定义的动画
     */
    public static class MyHeader extends LinearLayout implements RefreshHeader {

        private ImageView mProgressView;
        private AnimationDrawable progressDrawable;

        public MyHeader(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            mProgressView = new ImageView(context);
            mProgressView.setImageResource(R.drawable.my_loading);
            progressDrawable = (AnimationDrawable) mProgressView.getDrawable();
            addView(mProgressView, DensityUtil.dp2px(50), DensityUtil.dp2px(50));
        }

        @Override
        public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

        }

        @Override
        public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

        }

        @NonNull
        @Override
        public View getView() {
            return this;
        }

        @Override
        public SpinnerStyle getSpinnerStyle() {
            return SpinnerStyle.Translate;
        }

        @Override
        public void setPrimaryColors(@ColorInt int... colors) {

        }

        @Override
        public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

        }

        @Override
        public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

        }

        @Override
        public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
            progressDrawable.start();
        }

        @Override
        public int onFinish(RefreshLayout layout, boolean success) {
            progressDrawable.stop();
            return 500;
        }

        @Override
        public boolean isSupportHorizontalDrag() {
            return false;
        }

        @Override
        public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

        }
    }

}
