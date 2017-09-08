package com.yng.ming.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Bright on 2017/7/31 0031
 * 解决ScrollView中ListView只显示一行的问题
 * 重写ListView的onMeasure方法，直接给它一个足够大的高度
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        // TODO Auto-generated method stub
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
