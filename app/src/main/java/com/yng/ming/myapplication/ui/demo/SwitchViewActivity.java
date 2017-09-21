package com.yng.ming.myapplication.ui.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnClickListener;
import com.yng.ming.myapplication.widget.SwitchView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 仿IOS开关按钮
 * http://blog.csdn.net/yan8024/article/details/46817593
 * <p>
 * 使用：
 * <SwitchView></SwitchView>
 * 常用属性：
 * app:isOpened="false"             // 设置默认状态
 * app:primaryColor="@color/blue"   // 设置按钮开启状态时，背部的颜色
 * app:primaryColorDark="#e60012"   // 设置开关的圆形按钮外圈颜色
 * 常用方法：
 * isOpened()：查看按钮的开关状态
 * setOpened(true)：设置按钮的开关状态
 * <p>
 * 注意：使用开关按钮时，点击事件不能添加防抖判断
 */
public class SwitchViewActivity extends BaseActivity {

    @Bind(R.id.switchChangeView)
    SwitchView switchChangeView;
    @Bind(R.id.openButtonText)
    TextView openButtonText;

    // 是否开启防抖
    @Bind(R.id.switchAntiShake)
    SwitchView switchAntiShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_view);
        setToolbar();
        noAntiShake();
    }

    private void setToolbar() {
        setTitleText("开关按钮");
    }

    @OnClick({R.id.switchAntiShake})
    public void onClick(View view) {
        if (switchAntiShake.isOpened()) {
            antiShake();
        } else {
            noAntiShake();
        }
    }

    private void antiShake() {
        switchChangeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                checkSwitch();
            }
        });
    }

    private void noAntiShake() {
        switchChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSwitch();
            }
        });
    }

    private void checkSwitch() {
        if (switchChangeView.isOpened()) {
            openButtonText.setVisibility(View.VISIBLE);
        } else {
            openButtonText.setVisibility(View.GONE);
        }
    }

}
