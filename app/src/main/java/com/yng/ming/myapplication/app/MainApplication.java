package com.yng.ming.myapplication.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;

import com.yng.ming.myapplication.util.adaptation.ScreenAdaptation;
import com.yng.ming.myapplication.util.language.LocaleUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/12/14.
 * <p>
 * 应用主体, 初始化okhttp
 * 在这里做一些第三方初始化
 */
public class MainApplication extends Application {

    // 全局的上下文对象
    protected static Context context;

    private List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        //针对Android 7 以上 FileUriExposedException : 置入一个不设防的VmPolicy,再用旧的方式直接把file://格式的URI发送出去   http://www.jianshu.com/p/68a4e8132fcd
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        initApp();

        // 初始化屏幕适配 需要传入ui设计给的大小
        new ScreenAdaptation(this, 720, 1280).register();

        // 多语言数据
        Locale _UserLocale = LocaleUtils.getUserLocale(this);
        LocaleUtils.updateLocale(this, _UserLocale);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Locale _UserLocale = LocaleUtils.getUserLocale(this);
        //系统语言改变了应用保持之前设置的语言
        if (_UserLocale != null) {
            Locale.setDefault(_UserLocale);
            Configuration _Configuration = new Configuration(newConfig);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(_UserLocale);
            } else {
                _Configuration.locale = _UserLocale;
            }
            getResources().updateConfiguration(_Configuration, getResources().getDisplayMetrics());
        }
    }

    private void initApp() {
        // http请求
        try {
            OkHttpUtils.initClient(new OkHttpClient.Builder().readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(10, java.util.concurrent.TimeUnit.SECONDS).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}