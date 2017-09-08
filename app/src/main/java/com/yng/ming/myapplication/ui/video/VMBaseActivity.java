package com.yng.ming.myapplication.ui.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yng.ming.myapplication.util.log.Logcat;

/**
 * Created by lzan13 on 2015/7/4.
 * Activity 的基类，做一些子类公共的工作
 */
public class VMBaseActivity extends AppCompatActivity {

    protected String className = this.getClass().getSimpleName();

    // 当前界面的上下文菜单对象
    protected VMBaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logcat.i("%s onCreate" + className);
        activity = this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logcat.i("%s onRestart" + className);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logcat.i("%s onStart" + className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logcat.i("%s onResume" + className);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logcat.i("%s onPause" + className);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logcat.i("%s onStop" + className);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logcat.i("%s onDestroy" + className);
        activity = null;
    }

    /**
     * 自定义 Activity 结束方法
     */
    protected void onFinish() {
        activity.finish();
    }
}
