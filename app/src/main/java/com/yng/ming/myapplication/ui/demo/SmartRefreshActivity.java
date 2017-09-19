package com.yng.ming.myapplication.ui.demo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
                }, 3000);
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

        // 图片视图
        private ImageView mProgressView;
        // 动画
        private AnimationDrawable progressDrawable;

        public MyHeader(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            mProgressView = new ImageView(context);
            mProgressView.setImageResource(R.drawable.my_loading);
            progressDrawable = (AnimationDrawable) mProgressView.getDrawable();
            addView(mProgressView, 500, 300);
        }

        /**
         * 手指拖动下拉（会连续多次调用，用于实时控制动画关键帧）
         *
         * @param percent      下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+extendHeight) / headerHeight )
         * @param offset       下拉的像素偏移量  0 - offset - (headerHeight+extendHeight)
         * @param headerHeight Header的高度
         * @param extendHeight Header的扩展高度
         */
        @Override
        public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

        }

        /**
         * 手指释放之后的持续动画（会连续多次调用，用于实时控制动画关键帧）
         *
         * @param percent      下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+extendHeight) / headerHeight )
         * @param offset       下拉的像素偏移量  0 - offset - (headerHeight+extendHeight)
         * @param headerHeight Header的高度
         * @param extendHeight Header的扩展高度
         */
        @Override
        public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

        }

        /**
         * 获取真实视图（必须返回，不能为null）
         */
        @NonNull
        @Override
        public View getView() {
            return this;
        }

        /**
         * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
         */
        @Override
        public SpinnerStyle getSpinnerStyle() {
            return SpinnerStyle.Translate;
        }

        /**
         * 设置主题颜色 （如果自定义的Header没有注意颜色，本方法可以什么都不处理）
         *
         * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
         */
        @Override
        public void setPrimaryColors(@ColorInt int... colors) {

        }

        /**
         * 尺寸定义初始化完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
         *
         * @param kernel       RefreshKernel 核心接口（用于完成高级Header功能）
         * @param height       HeaderHeight or FooterHeight
         * @param extendHeight extendHeaderHeight or extendFooterHeight
         */
        @Override
        public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

        }

        /**
         * 水平方向的拖动
         *
         * @param percentX 下拉时，手指水平坐标对屏幕的占比（0 - percentX - 1）
         * @param offsetX  下拉时，手指水平坐标对屏幕的偏移（0 - offsetX - LayoutWidth）
         */
        @Override
        public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

        }

        /**
         * 开始动画（开始刷新或者开始加载动画）
         *
         * @param layout       RefreshLayout
         * @param height       HeaderHeight or FooterHeight
         * @param extendHeight extendHeaderHeight or extendFooterHeight
         */
        @Override
        public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
            progressDrawable.start();
        }

        /**
         * 动画结束
         *
         * @param layout  RefreshLayout
         * @param success 数据是否成功刷新或加载
         * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
         */
        @Override
        public int onFinish(RefreshLayout layout, boolean success) {
            progressDrawable.stop();
            return 0; // 延迟xx毫秒之后再弹回
        }

        /**
         * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
         *
         * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
         */
        @Override
        public boolean isSupportHorizontalDrag() {
            return false;
        }

        /**
         * 刷新状态改变监听器
         * 状态改变事件 {@link RefreshState}
         *
         * @param refreshLayout RefreshLayout
         * @param oldState      改变之前的状态
         * @param newState      改变之后的状态
         */
        @Override
        public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

        }
    }

}
