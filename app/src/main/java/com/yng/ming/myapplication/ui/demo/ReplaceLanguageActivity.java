package com.yng.ming.myapplication.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.ui.MainActivity;
import com.yng.ming.myapplication.util.language.LocaleUtils;

import butterknife.OnClick;

/**
 * 更换语言(中英切换)
 * https://github.com/cekiasoo/SwitchLanguage
 */
public class ReplaceLanguageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_language);
        setToolbar();
    }

    private void setToolbar() {
        setTitleText("更换语言");
    }

    @OnClick({R.id.btn_en, R.id.btn_ch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_en:
                if (LocaleUtils.needUpdateLocale(this, LocaleUtils.LOCALE_ENGLISH)) {
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_ENGLISH);
                    restartAct();
                }
                break;
            case R.id.btn_ch:
                if (LocaleUtils.needUpdateLocale(this, LocaleUtils.LOCALE_CHINESE)) {
                    LocaleUtils.updateLocale(this, LocaleUtils.LOCALE_CHINESE);
                    restartAct();
                }
                break;
        }
    }

    /**
     * 重启当前Activity
     */
    private void restartAct() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //清除Activity退出和进入的动画
        overridePendingTransition(0, 0);
    }

}
