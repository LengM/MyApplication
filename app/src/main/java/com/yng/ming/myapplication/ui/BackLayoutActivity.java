package com.yng.ming.myapplication.ui;

import android.os.Bundle;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

/**
 * 滑动返回
 * https://github.com/anzewei/ParallaxBackLayout
 */
@ParallaxBack(edge = ParallaxBack.Edge.LEFT, edgeMode = ParallaxBack.EdgeMode.FULLSCREEN)
public class BackLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_layout);
        setToolbar();
    }

    private void setToolbar() {
        setTitleText("滑动返回");
    }

    /**
     * 使用手册：
     * 1.首先需要在Application中注册：
     * registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
     *
     * 2.在需要滑动的Activity上添加注解：@ParallaxBack
     *
     * 3.该注解的属性：
     *
     * （1）edge：滑动方向
     *  ① LEFT：默认，从左向右滑动
     *  ② RIGHT：从右向左滑动
     *  ③ TOP：从上向下滑动
     *  ④ BOTTOM：从下向上滑动
     *
     * （2）layout：滑动效果
     *  ① PARALLAX：默认，视差返回
     *  ② COVER：当前Activity在滑动
     *  ③ SLIDE：当前Activity和上级一起滑动
     *
     *  （3）edgeMode：触发方式：
     *  ① EDGE：默认，屏幕边框触发
     *  ② FULLSCREEN：全屏触发
     *  注意：在使用全屏触发时，会导致视图中的其他滚动效果失效
     */

}
