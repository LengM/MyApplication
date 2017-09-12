package com.yng.ming.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick({R.id.horizontalCheckButton, R.id.replaceLanguage, R.id.imgGridView, R.id.calendar, R.id.clearCache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.horizontalCheckButton:
                startActivity(new Intent(this, HorizontalCheckActivity.class));
                break;
            case R.id.replaceLanguage:
                startActivity(new Intent(this, ReplaceLanguageActivity.class));
                break;
            case R.id.imgGridView:
                startActivity(new Intent(this, ImgGridViewActivity.class));
                break;
            case R.id.calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.clearCache:
                startActivity(new Intent(this, ClearCacheActivity.class));
                break;
        }
    }

}
