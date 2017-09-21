package com.yng.ming.myapplication.ui.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wx.goodview.GoodView;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 点赞
 * https://github.com/venshine/GoodView
 * <p>
 * 使用：
 * 1.创建GoodView
 * 2.点击相关视图时，调用方法即可：
 * myGoodView.setImage(R.drawable.good_checked); // 弹出动画图片,可以设置文字或其他
 * myGoodView.show(likeImage); // 显示在指定视图
 */
public class GoodLikeActivity extends BaseActivity {

    @Bind(R.id.likeImage)
    ImageView likeImage;

    GoodView myGoodView;

    // 点赞状态：0-未点赞 1-已点赞
    int likeStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_like);
        setToolbar();
        myGoodView = new GoodView(this);
    }

    private void setToolbar() {
        setTitleText("点赞");
    }

    @OnClick({R.id.likeImage})
    public void onClick(View view) {
        if (likeStatus == 0) {
            likeImage.setImageResource(R.drawable.good_checked);
//            myGoodView.setText("+1");
            myGoodView.setImage(R.drawable.good_checked);
            myGoodView.show(likeImage);
            likeStatus = 1;
        } else {
            likeStatus = 0;
            likeImage.setImageResource(R.drawable.good);
        }
    }

}
