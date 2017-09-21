package com.yng.ming.myapplication.ui.demo;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.util.log.Logcat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 滑动删除/item拖拽
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class BRVAHSwipeActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.easySwipeRefresh)
    SmartRefreshLayout easySwipeRefresh;

    List<String> list;
    BRVAHSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_swipe);
        setToolbar();
        init();
    }

    private void setToolbar() {
        setTitleText("滑动删除/item拖拽");
    }

    private void init() {

        setDate();
        easySwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                easySwipeRefresh.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setDate();
                        adapter.notifyDataSetChanged();
                        easySwipeRefresh.finishRefresh();
                    }
                }, 2000);
            }
        });
    }

    private void setDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item " + i);
        }
        adapter = new BRVAHSwipeAdapter(R.layout.easy_swipe_item, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemDragAndSwipeCallback callback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnItemDragListener(onItemDragListener);
        adapter.setOnItemSwipeListener(onItemSwipeListener);
        adapter.enableDragItem(itemTouchHelper, R.id.easyContentLayout, true);
        adapter.enableSwipeItem();
    }

    /**
     * 这里需要使用BaseItemDraggableAdapter
     */
    public class BRVAHSwipeAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {

        public BRVAHSwipeAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.easyContent, item);
        }

    }

    /**
     * 侧滑功能
     */
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemSwipeStart ---------");
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- clearItemSwipe ---------");
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemSwiped ---------");
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            Logcat.i("--------- onItemSwipeMoving ---------");
        }
    };

    /**
     * 拖拽
     */
    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemDragStart ---------");
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            Logcat.i("--------- onItemDragMoving ---------");
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Logcat.i("--------- onItemDragEnd ---------");
        }
    };

}
