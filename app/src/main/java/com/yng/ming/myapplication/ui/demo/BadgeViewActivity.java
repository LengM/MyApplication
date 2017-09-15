package com.yng.ming.myapplication.ui.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 消息小红点
 * https://github.com/qstumn/BadgeView
 */
public class BadgeViewActivity extends BaseActivity {

    @Bind(R.id.messageImage)
    ImageView messageImage;
    @Bind(R.id.messageLayout)
    LinearLayout messageLayout;

    private int count;
    private Badge badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_view);
        setToolbar();
        setBadgeView();
    }

    private void setToolbar() {
        setTitleText("消息小红点");
    }

    private void setBadgeView() {
        badgeView = new QBadgeView(this)
                .bindTarget(messageLayout)
                .setBadgeNumber(count)
                .setBadgeGravity(Gravity.TOP | Gravity.END)
                .setGravityOffset(40, 10, true)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        count = 0;
                    }
                });
    }

    @OnClick({R.id.addButton})
    public void onClick(View view) {
        ++count;
        badgeView.setBadgeNumber(count);
    }
    /**
     *  code	                            说明
     *  setBadgeNumber	                    设置Badge数字
     *  setBadgeText	                    设置Badge文本
     *  setBadgeTextSize	                设置文本字体大小
     *  setBadgeTextColor	                设置文本颜色
     *  setExactMode	                    设置是否显示精确模式数值
     *  setBadgeGravity	                    设置Badge相对于TargetView的位置
     *  setGravityOffset	                设置外边距
     *  setBadgePadding	                    设置内边距
     *  setBadgeBackgroundColor	            设置背景色
     *  setBadgeBackground	                设置背景图片
     *  setShowShadow	                    设置是否显示阴影
     *  setOnDragStateChangedListener	    打开拖拽消除模式并设置监听
     *  stroke	                            描边
     *  hide	                            隐藏Badge
     */

}
