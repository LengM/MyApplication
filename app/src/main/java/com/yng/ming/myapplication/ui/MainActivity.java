package com.yng.ming.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

import butterknife.OnClick;

/**
 * 主页面导航
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick({R.id.horizontalCheckButton,
            R.id.replaceLanguage,
            R.id.imgGridView,
            R.id.calendar,
            R.id.clearCache,
            R.id.upShowAndEdit,
            R.id.downMenu,
            R.id.backLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.horizontalCheckButton: // 横向滑动选择按钮
                startActivity(new Intent(this, HorizontalCheckActivity.class));
                break;
            case R.id.replaceLanguage: // 更换语言
                startActivity(new Intent(this, ReplaceLanguageActivity.class));
                break;
            case R.id.imgGridView: // 图片列表/多图预览
                startActivity(new Intent(this, ImgGridViewActivity.class));
                break;
            case R.id.calendar: // 日历
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.clearCache: // 清理缓存
                startActivity(new Intent(this, ClearCacheActivity.class));
                break;
            case R.id.upShowAndEdit: // Activity开启动画(从下向上)/带提示输入框
                startActivity(new Intent(this, EditDemoActivity.class));
                /**
                 * 这里使用动画若出现黑色背景，我们只需将应用的主题的Window设置为透明即可
                 * AndroidManifest -> android:theme -> 进入自己定义的主题中 -> 添加 <item name="android:windowIsTranslucent">true</item>
                 */
                this.overridePendingTransition(R.anim.open_show_up, 0);
                break;
            case R.id.downMenu: // 下拉菜单
                startActivity(new Intent(this, DownMenuActivity.class));
                break;
            case R.id.backLayout: // 滑动返回
                startActivity(new Intent(this, BackLayoutActivity.class));
                break;
        }
    }

}
