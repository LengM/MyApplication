package com.yng.ming.myapplication.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.rey.material.widget.EditText;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 带提示输入框
 */
public class EditDemoActivity extends BaseActivity {

    @Bind(R.id.phoneNumEditText)
    EditText phoneNumEditText;
    @Bind(R.id.getVerifyCode)
    Button getVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_demo);
        phoneEditCheck();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.close_hide_down);
    }

    private void phoneEditCheck() {
        phoneNumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    getVerifyCode.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.getVerifyCode})
    public void onClick() {
        if (phoneNumEditText.getText().toString().length() < 11) {
            phoneNumEditText.setError(this.getString(R.string.phoneError));
        }
    }

}
