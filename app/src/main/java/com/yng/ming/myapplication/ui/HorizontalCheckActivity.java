package com.yng.ming.myapplication.ui;

import android.os.Bundle;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

/**
 * 横向滑动的选择按钮
 */
public class HorizontalCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_check);
        setToolbar();
    }

    private void setToolbar() {
        setTitleText("横向滑动选择");
        setLeftIcon(R.mipmap.ic_arrow_back_black);
        setLeftTextSize(12);
        setLeftToolbarPadding(0, 0, 0, 0);
        setLeftOnClickFinish();
    }

}
