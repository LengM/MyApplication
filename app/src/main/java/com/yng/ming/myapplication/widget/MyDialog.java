package com.yng.ming.myapplication.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.yng.ming.myapplication.R;

/**
 * 自定义弹出框
 * 使用透明的背景,这样dialog_background的圆角就能显示出来
 * Created by Albert.Ma on 2017/8/7 0007.
 */

public class MyDialog extends Dialog {

    public MyDialog(@NonNull Context context, View view) {
        super(context, R.style.MyDialogStyle);
        setContentView(view);  //设置view
    }

}
