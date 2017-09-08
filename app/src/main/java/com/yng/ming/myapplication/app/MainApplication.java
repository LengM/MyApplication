package com.yng.ming.myapplication.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.yng.ming.myapplication.receiver.CallReceiver;
import com.yng.ming.myapplication.util.adaptation.ScreenAdaptation;
import com.yng.ming.myapplication.util.language.LocaleUtils;
import com.yng.ming.myapplication.util.video.CallManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Iterator;
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
    private CallReceiver callReceiver;

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

        // 初始化环信
        initHyphenate();
        initEaseUI();
        initVolley();
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

    /**
     * 初始化环信sdk，并做一些注册监听的操作
     */
    private void initHyphenate() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
         * name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }

        // 初始化sdk的一些配置
        EMOptions options = new EMOptions();
        options.setAutoLogin(true);
        // 动态设置appkey，如果清单配置文件设置了 appkey，这里可以不用设置
        // options.setAppKey("yunshangzhijia#yunyue");

        // 设置小米推送 appID 和 appKey
        options.setMipushConfig("2882303761517573806", "5981757315806");

        // 设置消息是否按照服务器时间排序
        options.setSortMessageByServerTime(false);

        // 初始化环信SDK,一定要先调用init()
        EMClient.getInstance().init(context, options);

        // 开启 debug 模式
        EMClient.getInstance().setDebugMode(true);

        // 设置通话广播监听器
        IntentFilter callFilter = new IntentFilter(
                EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        // 注册通话广播接收者
        context.registerReceiver(callReceiver, callFilter);

        // 通话管理类的初始化
        CallManager.getInstance().init(context);
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return processName;
    }

    ///////////////////////////////////    环信easeui    ////////////////////////////////////

    private static RequestQueue requestQueue;

    /**
     * EaseUI初始化
     */
    private void initEaseUI() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        //初始化
        EaseUI.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源 log:ONE SDK
        EMClient.getInstance().setDebugMode(true);
    }
    ///////////////////////////////////    Volley    ////////////////////////////////////

    /**
     * 设置Volley随应用启动创建单独的请求序列
     */
    private void initVolley() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}