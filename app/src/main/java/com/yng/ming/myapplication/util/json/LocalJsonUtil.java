package com.yng.ming.myapplication.util.json;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Bright Lee on 2017/9/19 0019
 * 解析本地Json
 */
public final class LocalJsonUtil {

    private LocalJsonUtil() {
    }

    public static String getJson(Context context, String jsonFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open(jsonFileName), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line.trim());
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
