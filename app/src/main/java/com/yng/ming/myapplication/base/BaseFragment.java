package com.yng.ming.myapplication.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.util.log.Logcat;

import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Fragment基础类
 * 可按照需要添加对应的标题栏, 例如:
 * setToolbar("返回","标题")
 * <p>
 * 详情参照方法
 */
public abstract class BaseFragment extends Fragment {

    private Activity bfActivity;//Fragment所属的Activity

    private final static String TAG = "BaseFragment";
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bfActivity = getActivity();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onCreate...");
    }

    /**
     * 子类onCreateView后,初始化ButterKnife控件
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == view) {
            view = getView();
        }
        //初始化ButterKnife
        ButterKnife.bind(this, getView());
    }


    @Override
    public void onStart() {
        super.onStart();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onStart...");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onResume...");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onPause...");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onStop...");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onDestroyView...");
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
        TextView left = (TextView) view.findViewById(R.id.tv_left);
        left.setText(text);
        RelativeLayout rl_left_toolbar = (RelativeLayout) view.findViewById(R.id.rl_left_toolbar);
        if (icon != 0) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // 设置边界
            left.setCompoundDrawables(drawable, null, null, null);// 画在左边
        }
        rl_left_toolbar.setOnClickListener(listener);
    }

    protected void setLeftView(String text, int icon, int color, OnClickListener listener) {
        TextView left = (TextView) view.findViewById(R.id.tv_left);
        left.setText(text);
        left.setTextColor(getContext().getResources().getColor(color));
        RelativeLayout rl_left_toolbar = (RelativeLayout) view.findViewById(R.id.rl_left_toolbar);
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
                getActivity().finish();
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
     * @param text      中间文字
     * @param textColor 文字颜色
     */
    protected void setToolbar(String text, int textColor) {
        TextView tv_center = (TextView) view.findViewById(R.id.tv_center);
        tv_center.setText(text);
        tv_center.setTextColor(getContext().getResources().getColor(textColor));
    }

    /**
     * 设置左侧文字+中间文字+返回事件
     *
     * @param textLeft 左侧文字
     * @param text     中间文字
     */
    protected void setToolbar(String textLeft, String text, int textColor) {
        setToolbar(text, textColor);
        setLeftView(textLeft);
    }

    /**
     * 中间文字+左侧图标+返回事件
     *
     * @param icon 左侧图标
     * @param text 中间文字
     */
    protected void setToolbar(int icon, String text, int textColor) {
        setToolbar(text, textColor);
        setLeftView(icon);
    }

    /**
     * 左侧文字图标+中间文字+返回事件
     *
     * @param textLeft 左侧文字
     * @param icon     左侧图标
     * @param text     中间文字
     */
    protected void setToolbar(String textLeft, int icon, String text, int textColor) {
        setToolbar(text, textColor);
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
    protected void setToolbar(String textLeft, int icon, OnClickListener listener, String text, int textColor) {
        setToolbar(text, textColor);
        setLeftView(textLeft, icon, listener);
    }

    /**
     * 左侧文字,图标,颜色,事件;中间文字,颜色
     *
     * @param leftText     左侧文字
     * @param leftIcon     左侧图标
     * @param leftColor    左侧文字颜色
     * @param leftListener 左侧点击事件
     * @param centerText   中间文字
     * @param centerColor  中间文字颜色
     */
    protected void setToolbar(String leftText, int leftIcon, int leftColor, OnClickListener leftListener, String centerText, int centerColor) {
        setToolbar(centerText, centerColor);
        setLeftView(leftText, leftIcon, leftColor, leftListener);
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
        RelativeLayout rl_right_toolbar = (RelativeLayout) view.findViewById(R.id.rl_right_toolbar);
        rl_right_toolbar.setOnClickListener(listener);
        // 右侧的按钮
        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_right.setText(rightText);
        tv_right.setTextColor(this.getResources().getColor(rightTextColor));
        if (icon != 0) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); // 设置边界
            tv_right.setCompoundDrawables(drawable, null, null, null);// 画在左边
        }
    }

    /**
     * 重写OnDestroy()方法, 销毁时收起软键盘
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logcat.i(bfActivity + "." + this.toString() + " - ==> onDestroy...");
        hideSoftInput();
    }

    /**
     * 收起键盘
     */
    private void hideSoftInput() {
        try {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext()
                    .getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.i(TAG, "----" + e.toString());
        }
    }
}
