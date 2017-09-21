package com.yng.ming.myapplication.ui.demo;

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
 * https://github.com/rey5137/material
 * <p>
 * 使用：
 * 输入框：<com.rey.material.widget.EditText>
 * 属性相关：
 * app:et_dividerXxx：设置输入框中的横线相关
 * app:et_labelEnable="true"：开启输入时，hint上移到输入内容上方
 * app:et_labelXxx：设置输入时，hint移动到输入框上方的样式
 * app:et_supportMode="helper"：设置下方提示语
 * app:et_supportXxx：设置横线下方的提示语内容样式
 * <p>
 * 方法：
 * 设置错误提示(support内容)：editText.setError("手机号错误！");
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
        setToolbar();
        phoneEditCheck();
    }

    private void setToolbar() {
        setTitleText("带提示输入框");
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
/**
 * 其他属性：
 * <p>
 * et_inputId- 包裹的Id android.widget.EditText。它应该与此视图的Id不同，因此输入视图可以恢复状态。
 * <p>
 * et_labelEnable - 表示它应该显示浮动标签。标签的文字将是输入视图的提示文字。
 * <p>
 * et_labelPadding - 带输入视图的标签填充。
 * <p>
 * et_labelTextSize - 标签文字的大小。
 * <p>
 * et_labelTextColor - 标签文字的颜色。
 * <p>
 * et_labelTextAppearance - 标签文字的外观设置。
 * <p>
 * et_labelEllipsize - 标签文本的省略号设置。
 * <p>
 * et_labelInAnim - 标签显示时的动画。
 * <p>
 * et_labelOutAnim - 标签被隐藏时的动画。
 * <p>
 * et_supportMode - 在输入视图下方显示支持文本的模式。有四种模式：none，helper，helperWithError和charCounter。
 * <p>
 * et_supportMaxChars- 输入视图的最大字符（用于charCounter模式）。0意味着它只会显示字符计数器。
 * <p>
 * et_helper - 帮助文本。
 * <p>
 * et_error - 错误文本。
 * <p>
 * et_supportPadding - 输入视图的支持文本的填充。
 * <p>
 * et_supportTextSize - 支持文本的大小。
 * <p>
 * et_supportTextColor - 支持文本的颜色。
 * <p>
 * et_supportTextErrorColor - 出现错误时支持文本的颜色。
 * <p>
 * et_supportTextAppearance - 支持文本的外观设置。
 * <p>
 * et_supportSingleLine - 表示支持文本应显示为单行。
 * <p>
 * et_supportMaxLines - 支持文本的最大行。
 * <p>
 * et_supportLines - 支持文本的行。
 * <p>
 * et_supportEllipsize - 支持文本的省略号设置。
 * <p>
 * et_dividerColor - 分隔线的颜色。接受颜色值或ColorStateList资源。
 * <p>
 * et_dividerErrorColor - 分频器出现错误时的颜色。
 * <p>
 * et_dividerHeight - 分隔线的高度。
 * <p>
 * et_dividerPadding - 带输入视图的分频器的填充。
 * <p>
 * et_dividerAnimDuration - 分隔线的动画持续时间。
 * <p>
 * et_dividerCompoundPadding - 如果为真，则分隔符应包括复合绘图的填充。
 * <p>
 * et_autoCompleteMode - 指示哪个输入视图被包装。有三种模式：none（android.widget.EditText），single（android.widget.AutoCompleteEditText）和multi（android.widget.MultiAutoCompleteTextView）
 */