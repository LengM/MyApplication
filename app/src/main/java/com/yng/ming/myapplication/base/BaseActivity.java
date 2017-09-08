package com.yng.ming.myapplication.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.app.MainApplication;
import com.yng.ming.myapplication.util.log.Logcat;

import butterknife.ButterKnife;

/**
 * Activity基础类
 * 可按照需要添加对应的标题栏, 例如:
 * setToolbar("返回","标题")
 * <p>
 * 标题右侧点击可添加弹出框，需要传入弹出框内容(List<String>)以及弹出框中item点击事件
 * <p>
 * 详情参照方法
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context baContext;//上下文
    private final static String TAG = "BaseActivity";
    protected MainApplication mainApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logcat.i(this.toString() + " - ==> onCreate...");
        baContext = this;
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

    /** ------------------------------------左侧toolbar按钮设置---------------------------------------- **/

    /**
     * 左侧文字+左侧图标+自定义点击事件
     *
     * @param text     左侧文字
     * @param icon     左侧图标
     * @param listener 点击事件
     */
    protected void setLeftView(String text, int icon, OnClickListener listener) {
        TextView left = (TextView) findViewById(R.id.tv_left);
        left.setText(text);
        RelativeLayout rl_left_toolbar = (RelativeLayout) findViewById(R.id.rl_left_toolbar);
        if (icon != 0) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // 设置边界
            left.setCompoundDrawables(drawable, null, null, null);// 画在左边
        }
        rl_left_toolbar.setOnClickListener(listener);
    }

    /**
     * 左侧文字+自定义点击事件
     *
     * @param text     左侧文字
     * @param listener 点击事件
     */
    protected void setLeftView(String text, OnClickListener listener) {
        setLeftView(text, 0, listener);
    }

    /**
     * 左侧图标+自定义点击事件
     *
     * @param icon     左侧图标
     * @param listener 点击事件
     */
    protected void setLeftView(int icon, OnClickListener listener) {
        setLeftView("", icon, listener);
    }

    /**
     * 左侧图标文字+返回事件
     *
     * @param text 左侧文字
     * @param icon 左侧图标
     */
    protected void setLeftView(String text, int icon) {
        setLeftView(text, icon, new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
    }

    /**
     * 左侧图标+返回事件
     *
     * @param icon 左侧图标
     */
    protected void setLeftView(int icon) {
        setLeftView("", icon);
    }

    /**
     * 左侧文字+返回事件
     *
     * @param text 左侧文字
     */
    protected void setLeftView(String text) {
        setLeftView(text, 0);
    }

    /** ------------------------------------中间toolbar按钮设置---------------------------------------- **/

    /**
     * 仅标题
     *
     * @param text            中间文字
     * @param centerTextColor 文字颜色
     */
    protected void setToolbar(String text, int centerTextColor) {
        TextView tv_center = (TextView) findViewById(R.id.tv_center);
        tv_center.setText(text);
        tv_center.setTextColor(this.getResources().getColor(centerTextColor));
    }

    /**
     * 设置左侧文字+中间文字+返回事件
     *
     * @param textLeft 左侧文字
     * @param text     中间文字
     */
    protected void setToolbar(String textLeft, String text, int centerTextColor) {
        setToolbar(text, centerTextColor);
        setLeftView(textLeft);
    }

    /**
     * 中间文字+左侧图标+返回事件
     *
     * @param icon 左侧图标
     * @param text 中间文字
     */
    protected void setToolbar(int icon, String text, int centerTextColor) {
        setToolbar(text, centerTextColor);
        setLeftView(icon);
    }

    /**
     * 左侧文字图标+中间文字+返回事件
     *
     * @param textLeft 左侧文字
     * @param icon     左侧图标
     * @param text     中间文字
     */
    protected void setToolbar(String textLeft, int icon, String text, int centerTextColor) {
        setToolbar(text, centerTextColor);
        setLeftView(textLeft, icon);
    }

    /**
     * 左侧问题图标+自定义点击事件+中间文字
     *
     * @param textLeft 左侧文字
     * @param icon     左侧图标
     * @param listener 点击事件
     * @param text     中间文字
     */
    protected void setToolbar(String textLeft, int icon, OnClickListener listener, String text, int centerTextColor) {
        setToolbar(text, centerTextColor);
        setLeftView(textLeft, icon, listener);
    }

    /** ------------------------------------右侧toolbar按钮设置---------------------------------------- **/

    /**
     * 右侧文字+右侧图标+自定义点击事件
     *
     * @param rightText 右侧文字
     * @param icon      右侧图标
     * @param listener  点击事件
     */
    protected void setRightView(String rightText, int icon, OnClickListener listener, int rightTextColor) {
        RelativeLayout rl_right_toolbar = (RelativeLayout) findViewById(R.id.rl_right_toolbar);
        rl_right_toolbar.setOnClickListener(listener);
        // 右侧的按钮
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText(rightText);
        tv_right.setTextColor(this.getResources().getColor(rightTextColor));
        if (icon != 0) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // 设置边界
            tv_right.setCompoundDrawables(drawable, null, null, null);// 画在左边
        }
    }

    protected void hideRightMenu() {
        RelativeLayout rl_right_toolbar = (RelativeLayout) findViewById(R.id.rl_right_toolbar);
        rl_right_toolbar.setVisibility(View.GONE);
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
            Log.i(TAG, "----" + e.toString());
        }

    }

    //////////////////////////////   EditView点击空白区域隐藏输入法软键盘   //////////////////////////////

    /**
     * EditView点击空白区域隐藏输入法软键盘
     *
     * @param ev
     * @return
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
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * EditView点击空白区域隐藏输入法软键盘:获取InputMethodManager隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
