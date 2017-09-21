package com.yng.ming.myapplication.ui.demo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnClickListener;
import com.yng.ming.myapplication.widget.swipe.EasySwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 简单的item侧滑
 * https://github.com/anzaizai/EasySwipeMenuLayout
 *
 * 使用：
 * 在item的布局中，在item的布局外套一层<com.yng.ming.myapplication.widget.swipe.EasySwipeMenuLayout>
 *     一般情况下该布局的宽match_parent，高wrap_content
 *     需要指定item内容的布局id，例如：app:contentView="@+id/easyContentLayout"
 *     左滑时出现的右侧布局，例如：app:rightMenuView="@+id/deleteText"
 *     右滑时出现的左侧布局，例如：app:leftMenuView="@+id/collectText"
 *
 *     左右两侧滑动出现的布局，在EasySwipeMenuLayout中添加两个布局即可
 */
public class EasySwipeActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.easySwipeRefresh)
    SmartRefreshLayout easySwipeRefresh;

    List<String> list = new ArrayList<>();
    EasySwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_swipe);
        setToolbar();
        init();
    }

    private void setToolbar() {
        setTitleText("简单侧滑按钮");
    }

    private void init() {
        adapter = new EasySwipeAdapter(R.layout.easy_swipe_item, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        easySwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                easySwipeRefresh.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        setDate();
                        easySwipeRefresh.finishRefresh();
                    }
                }, 2000);
            }
        });
        setDate();
    }

    private void setDate() {
        for (int i = 0; i < 20; i++) {
            list.add("item " + i);
        }
        adapter.addData(list);
        adapter.notifyDataSetChanged();
    }

    public class EasySwipeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public EasySwipeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            helper.setText(R.id.easyContent, item);
            helper.getView(R.id.easyContentLayout).setOnClickListener(new OnClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.easySwipeMenuLayout);
                    easySwipeMenuLayout.resetStatus();
                }
            });
            helper.getView(R.id.deleteText).setOnClickListener(new OnClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    Toast.makeText(EasySwipeActivity.this, "你点击了删除按钮", Toast.LENGTH_SHORT).show();
                    EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.easySwipeMenuLayout);
                    easySwipeMenuLayout.resetStatus();
                }
            });
        }
    }

}
