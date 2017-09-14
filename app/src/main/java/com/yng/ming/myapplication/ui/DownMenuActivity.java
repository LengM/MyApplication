package com.yng.ming.myapplication.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.adapter.DownMenuAdapter;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 下拉菜单
 */
public class DownMenuActivity extends BaseActivity {

    @Bind(R.id.priceText)
    TextView priceText;
    @Bind(R.id.priceImage)
    ImageView priceImage;
    @Bind(R.id.languageText)
    TextView languageText;
    @Bind(R.id.languageImage)
    ImageView languageImage;

    @Bind(R.id.conditionLayout)
    LinearLayout conditionLayout;
    // 阴影遮罩层
    @Bind(R.id.grayTransparent)
    View grayTransparent;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_menu);
        setToolbar();
    }

    private void setToolbar() {
        setTitleText("下拉菜单");
    }

    @OnClick({R.id.priceLayout, R.id.languageLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.priceLayout:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                final List<String> price = new ArrayList<>();
                price.add("不限");
                price.add("由低到高");
                price.add("由高到低");
                showSearchMenu(conditionLayout, price, new OnItemClickListener() {
                    @Override
                    public void onItemNoDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                        priceImage.setImageResource(R.mipmap.ic_triangle_unselected);
                        priceText.setText(price.get(position));
                    }
                });
                break;
            case R.id.languageLayout:
                break;
        }
    }

    public void showSearchMenu(View view, List<String> popupData, OnItemClickListener listener) {

        if (popupWindow != null && popupWindow.isShowing()) {
            // 关闭popupWindow
            popupWindow.dismiss();
        }
        final View popupView = getLayoutInflater().inflate(R.layout.down_menu_popup, null);
        popupWindow = new PopupWindow(popupView, ListPopupWindow.MATCH_PARENT, ListPopupWindow.MATCH_PARENT,
                true);

        // 设置弹出的popupWindow不遮挡软键盘
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 如果focusable为false，在一个Activity弹出一个PopupWindow，按返回键，由于PopupWindow没有焦点，会直接退出Activity。
        // 如果focusable为true，PopupWindow弹出后，所有的触屏和物理按键都有PopupWindows处理。
        popupWindow.setFocusable(true);// 可以试试设为false的结果
        popupWindow.setOutsideTouchable(true); // 点击外部消失

        // 必须设置的选项
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                closePopupWindow();
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                priceImage.setImageResource(R.mipmap.ic_triangle_unselected);
                grayTransparent.setVisibility(View.GONE);
            }
        });
        // 将window视图显示在点击按钮下面
        popupWindow.showAsDropDown(view, 0, 0);
        ListView listView = (ListView) popupView.findViewById(R.id.popListView);
        listView.setAdapter(new DownMenuAdapter(this, popupData, priceText.getText().toString()));
        listView.setOnItemClickListener(listener);
        priceImage.setImageResource(R.mipmap.ic_triangle_selected);

        grayTransparent.setVisibility(View.VISIBLE);

    }

}
