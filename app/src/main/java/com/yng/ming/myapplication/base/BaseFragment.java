package com.yng.ming.myapplication.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
 * 标题使用方法参照BaseActivity
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

    /**
     * 隐藏标题栏右侧按钮
     */
    protected void hideRightMenu() {
        RelativeLayout baseRightToolbar = (RelativeLayout) view.findViewById(R.id.baseRightToolbar);
        baseRightToolbar.setVisibility(View.GONE);
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

    ///////////////////////////////////// 标题栏 ///////////////////////////////////////////////

    public BaseFragment setLeftText(String leftText) {
        TextView baseLeftTextView = (TextView) view.findViewById(R.id.baseLeftTextView);
        if (leftText != null && !leftText.isEmpty()) {
            baseLeftTextView.setText(leftText);
        }
        return this;
    }

    public BaseFragment setLeftIcon(int leftIcon) {
        TextView baseLeftTextView = (TextView) view.findViewById(R.id.baseLeftTextView);
        if (leftIcon != 0) {
            Drawable drawable = ContextCompat.getDrawable(bfActivity, leftIcon);
            // 设置边界
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            // 画在左边
            baseLeftTextView.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    public BaseFragment setLeftOnClickFinish() {
        RelativeLayout baseLeftToolbar = (RelativeLayout) view.findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                getActivity().finish();
            }
        });
        return this;
    }

    public BaseFragment setLeftOnClickListener(OnClickListener leftOnClickListener) {
        RelativeLayout baseLeftToolbar = (RelativeLayout) view.findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setOnClickListener(leftOnClickListener);
        return this;
    }

    public BaseFragment setLeftTextColor(int leftTextColor) {
        TextView baseLeftTextView = (TextView) view.findViewById(R.id.baseLeftTextView);
        if (leftTextColor != 0) {
            baseLeftTextView.setTextColor(leftTextColor);
        }
        return this;
    }

    public BaseFragment setLeftToolbarPadding(int left, int top, int right, int bottom) {
        RelativeLayout baseLeftToolbar = (RelativeLayout) view.findViewById(R.id.baseLeftToolbar);
        baseLeftToolbar.setPadding(left, top, right, bottom);
        return this;
    }

    public BaseFragment setLeftTextSize(int leftTextSize) {
        TextView baseLeftTextView = (TextView) view.findViewById(R.id.baseLeftTextView);
        if (leftTextSize != 0) {
            baseLeftTextView.setTextSize(leftTextSize);
        }
        return this;
    }

    public BaseFragment setTitleText(String titleText) {
        TextView baseTitle = (TextView) view.findViewById(R.id.baseTitle);
        if (titleText != null && !titleText.isEmpty()) {
            baseTitle.setText(titleText);
        }
        return this;
    }

    public BaseFragment setTitleTextColor(int titleTextColor) {
        TextView baseTitle = (TextView) view.findViewById(R.id.baseTitle);
        if (titleTextColor != 0) {
            baseTitle.setTextColor(titleTextColor);
        }
        return this;
    }

    public BaseFragment setTitleTextSize(int titleTextSize) {
        TextView baseTitle = (TextView) view.findViewById(R.id.baseTitle);
        if (titleTextSize != 0) {
            baseTitle.setTextSize(titleTextSize);
        }
        return this;
    }

    public BaseFragment setRightText(String rightText) {
        TextView baseRightTextView = (TextView) view.findViewById(R.id.baseRightTextView);
        if (rightText != null && !rightText.isEmpty()) {
            baseRightTextView.setText(rightText);
        }
        return this;
    }

    public BaseFragment setRightIcon(int rightIcon) {
        TextView baseRightTextView = (TextView) view.findViewById(R.id.baseRightTextView);
        if (rightIcon != 0) {
            Drawable drawable = ContextCompat.getDrawable(bfActivity, rightIcon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            baseRightTextView.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    public BaseFragment setRightOnClickListener(OnClickListener rightOnClickListener) {
        RelativeLayout baseRightToolbar = (RelativeLayout) view.findViewById(R.id.baseRightToolbar);
        if (rightOnClickListener != null) {
            baseRightToolbar.setOnClickListener(rightOnClickListener);
        }
        return this;
    }

    public BaseFragment setRightTextColor(int rightTextColor) {
        TextView baseRightTextView = (TextView) view.findViewById(R.id.baseRightTextView);
        if (rightTextColor != 0) {
            baseRightTextView.setTextColor(rightTextColor);
        }
        return this;
    }

    public BaseFragment setRightTextSize(int rightTextSize) {
        TextView baseRightTextView = (TextView) view.findViewById(R.id.baseRightTextView);
        if (rightTextSize != 0) {
            baseRightTextView.setTextSize(rightTextSize);
        }
        return this;
    }

    public BaseFragment setRightToolbarPadding(int left, int top, int right, int bottom) {
        RelativeLayout baseRightToolbar = (RelativeLayout) view.findViewById(R.id.baseRightToolbar);
        baseRightToolbar.setPadding(left, top, right, bottom);
        return this;
    }

    public BaseFragment setShowDividerView(boolean showDividerView) {
        View baseDividerView = view.findViewById(R.id.baseDividerView);
        baseDividerView.setVisibility(showDividerView ? View.VISIBLE : View.GONE);
        return this;
    }

}
