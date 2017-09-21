package com.yng.ming.myapplication.ui.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.util.cache.DataCleanUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 清理缓存
 */
public class ClearCacheActivity extends BaseActivity {

    @Bind(R.id.clearCache)
    Button clearCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        setToolbar();
        init();
    }

    private void setToolbar() {
        setTitleText("清理缓存");
    }

    @OnClick({R.id.clearCache})
    public void onClick(View view) {
        // 清理缓存方法
        DataCleanUtil.clearAllCache(baseContext);
        init();
    }

    private void init() {
        try {
            // 获取缓存大小
            clearCache.setText(DataCleanUtil.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
