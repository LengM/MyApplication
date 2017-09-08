package com.yng.ming.myapplication.util.log;

import android.util.Log;

/**
 * 日志工具
 * Created by Albert.Ma on 2016/4/20.
 */
public class Logcat {

    public static final String Logcat = "logcat";//日志标签

    //控制是否打印日志
    public static boolean isLoggerOn = true;


    /**
     * 打印info
     */
    public static void i(String log) {
        if (isLoggerOn && log!=null) {
            Log.i(Logcat, log);
        }
    }

    /**
     * 打印error
     */
    public static void e(String log) {
        if (isLoggerOn && log!=null) {
            Log.e(Logcat, log);
        }
    }
}
