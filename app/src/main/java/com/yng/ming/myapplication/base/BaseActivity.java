package com.yng.ming.myapplication.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.app.MainApplication;
import com.yng.ming.myapplication.util.log.Logcat;

import butterknife.ButterKnife;

/**
 * Activity基础类
 * <p>
 * 使用标题栏方法：
 * 1.在布局文件中引入标题栏:<include layout="@layout/base_tool_bar"/>
 * 2.使用setXXX方法可以对标题栏信息进行设置
 * <p>
 * 其他方法：
 * 1.收起软键盘：hideSoftInput();
 * 2.隐藏标题栏右侧视图(动态需求)：hideRightMenu();
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context baseContext;//上下文
    private final static String TAG = "BaseActivity";
    protected MainApplication mainApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logcat.i(this.toString() + " - ==> onCreate...");
        baseContext = this;

        // 透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
            window.setNavigationBarColor(Color.BLACK);
        }

        // activity管理
        mainApplication = (MainApplication) getApplication();
        mainApplication.getActivityList().add(this);

        // 初始化工具
        Utils.init(mainApplication);
    }

    /**
     * 重写setContentView(int)方法, 添加初始化ButterKnife
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logcat.i(this.toString() + " - ==> onStart...");
    }

    /**
     * 在onStart之后调用，恢复数据
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logcat.i(this.toString() + "- ==> 恢复数据");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logcat.i(this.toString() + " - ==> onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logcat.i(this.toString() + " - ==> onPause...");
    }

    /**
     * finish方法添加动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 重写onStop()方法, 取消Glide加载图片请求
     */
    @Override
    protected void onStop() {
        super.onStop();
        Logcat.i(this.toString() + " - ==> onStop...");
        if (Glide.with(this) != null) {
            Glide.with(this).pauseRequests();
        }
    }

    /**
     * 重写OnDestroy()方法, 销毁时收起软键盘
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logcat.i(this.toString() + " - ==> onDestroy...");
        mainApplication.getActivityList().remove(this);
        hideSoftInput();
    }

    /**
     * 收起键盘
     */
    public void hideSoftInput() {
        try {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Logcat.i(TAG + e.toString());
        }
    }

    //////////////////////////////   EditView点击空白区域隐藏输入法软键盘   //////////////////////////////

    /**
     * EditView点击空白区域隐藏输入法软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * EditView点击空白区域隐藏输入法软键盘:
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * EditView点击空白区域隐藏输入法软键盘:获取InputMethodManager隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    ///////////////////////////////////// 标题栏 ///////////////////////////////////////////////

    public BaseActivity setLeftText(String leftText) {
        TextView baseLeftTextView = (TextView) findViewById(R.id.baseLeftTextView);
        if (leftText != null && !leftText.isEmpty()) {
            baseLeftTextView.setText(leftText);
        }
        return this;
    }

    public BaseActivity setLeftIcon(int leftIcon) {
        TextView baseLeftTextView = (TextView) findViewById(R.id.baseLeftTextView);
        if (leftIcon != 0) {
            Drawable drawable = ContextCompat.getDrawable(baseContext, leftIcon);
            // 设置边界
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            // 画在左边
            baseLeftTextView.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    public BaseActivity setLeftOnClickFinish() {
        RelativeLayout baseLeftToolbar = (RelativeLayout) findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
        return this;
    }

    public BaseActivity setLeftOnClickListener(OnClickListener leftOnClickListener) {
        RelativeLayout baseLeftToolbar = (RelativeLayout) findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setOnClickListener(leftOnClickListener);
        return this;
    }

    public BaseActivity setLeftTextColor(int leftTextColor) {
        TextView baseLeftTextView = (TextView) findViewById(R.id.baseLeftTextView);
        if (leftTextColor != 0) {
            baseLeftTextView.setTextColor(leftTextColor);
        }
        return this;
    }

    public BaseActivity setLeftToolbarPadding(int left, int top, int right, int bottom) {
        RelativeLayout baseLeftToolbar = (RelativeLayout) findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setPadding(left, top, right, bottom);
        return this;
    }

    public BaseActivity setLeftTextSize(int leftTextSize) {
        TextView baseLeftTextView = (TextView) findViewById(R.id.baseLeftTextView);
        if (leftTextSize != 0) {
            baseLeftTextView.setTextSize(leftTextSize);
        }
        return this;
    }

    public BaseActivity setTitleText(String titleText) {
        TextView baseTitle = (TextView) findViewById(R.id.baseTitle);
        if (titleText != null && !titleText.isEmpty()) {
            baseTitle.setText(titleText);
        }
        return this;
    }

    public BaseActivity setTitleTextColor(int titleTextColor) {
        TextView baseTitle = (TextView) findViewById(R.id.baseTitle);
        if (titleTextColor != 0) {
            baseTitle.setTextColor(titleTextColor);
        }
        return this;
    }

    public BaseActivity setTitleTextSize(int titleTextSize) {
        TextView baseTitle = (TextView) findViewById(R.id.baseTitle);
        if (titleTextSize != 0) {
            baseTitle.setTextSize(titleTextSize);
        }
        return this;
    }

    public BaseActivity setRightText(String rightText) {
        TextView baseRightTextView = (TextView) findViewById(R.id.baseRightTextView);
        if (rightText != null && !rightText.isEmpty()) {
            baseRightTextView.setText(rightText);
        }
        return this;
    }

    public BaseActivity setRightIcon(int rightIcon) {
        TextView baseRightTextView = (TextView) findViewById(R.id.baseRightTextView);
        if (rightIcon != 0) {
            Drawable drawable = ContextCompat.getDrawable(baseContext, rightIcon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            baseRightTextView.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    public BaseActivity setRightOnClickListener(OnClickListener rightOnClickListener) {
        RelativeLayout baseRightToolbar = (RelativeLayout) findViewById(R.id.baseRightToolbar);
        if (rightOnClickListener != null) {
            baseRightToolbar.setOnClickListener(rightOnClickListener);
        }
        return this;
    }

    public BaseActivity setRightTextColor(int rightTextColor) {
        TextView baseRightTextView = (TextView) findViewById(R.id.baseRightTextView);
        if (rightTextColor != 0) {
            baseRightTextView.setTextColor(rightTextColor);
        }
        return this;
    }

    public BaseActivity setRightTextSize(int rightTextSize) {
        TextView baseRightTextView = (TextView) findViewById(R.id.baseRightTextView);
        if (rightTextSize != 0) {
            baseRightTextView.setTextSize(rightTextSize);
        }
        return this;
    }

    public BaseActivity setRightToolbarPadding(int left, int top, int right, int bottom) {
        RelativeLayout baseRightToolbar = (RelativeLayout) findViewById(R.id.baseRightToolbar);
        baseRightToolbar.setPadding(left, top, right, bottom);
        return this;
    }

    /**
     * 隐藏标题栏右侧按钮
     */
    protected void hideRightToolbar() {
        RelativeLayout baseRightToolbar = (RelativeLayout) findViewById(R.id.baseRightToolbar);
        baseRightToolbar.setVisibility(View.GONE);
    }

}
