package com.yng.ming.myapplication.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.adapter.IndexListAdapter;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnItemClickListener;
import com.yng.ming.myapplication.database.IndexDatabase;
import com.yng.ming.myapplication.model.IndexBean;
import com.yng.ming.myapplication.ui.demo.BRVAHSwipeActivity;
import com.yng.ming.myapplication.ui.demo.BackLayoutActivity;
import com.yng.ming.myapplication.ui.demo.BadgeViewActivity;
import com.yng.ming.myapplication.ui.demo.CalendarActivity;
import com.yng.ming.myapplication.ui.demo.ClearCacheActivity;
import com.yng.ming.myapplication.ui.demo.DownMenuActivity;
import com.yng.ming.myapplication.ui.demo.EasySwipeActivity;
import com.yng.ming.myapplication.ui.demo.EditDemoActivity;
import com.yng.ming.myapplication.ui.demo.GoodLikeActivity;
import com.yng.ming.myapplication.ui.demo.HorizontalCheckActivity;
import com.yng.ming.myapplication.ui.demo.ImgGridViewActivity;
import com.yng.ming.myapplication.ui.demo.ReplaceLanguageActivity;
import com.yng.ming.myapplication.ui.demo.SmartRefreshActivity;
import com.yng.ming.myapplication.util.json.LocalJsonUtil;
import com.yng.ming.myapplication.util.keyboard.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主页面导航
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.searchEdit)
    EditText searchEdit;
    @Bind(R.id.indexListView)
    ListView indexListView;

    IndexListAdapter adapter;
    List<IndexBean> beanList = new ArrayList<>();

    private IndexDatabase database; // 数据库
    private Cursor indexCursor; // 操作数据库的光标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        searchListener();
    }

    /**
     * 初始化:初始化数据库、读取json、设置adapter
     */
    private void init() {
        // 初始化数据库
        database = new IndexDatabase(this);
        // 清除数据库内容
        database.clear();
        // 解析json文件
        String json = LocalJsonUtil.getJson(this, "index.json");
        // 解析json字符串
        beanList = new Gson().fromJson(json, new TypeToken<List<IndexBean>>() {
        }.getType());
        // 存入数据库
        for (IndexBean bean : beanList) {
            database.insert(bean.getId(), bean.getMessage());
        }
        // 适配器
        adapter = new IndexListAdapter(this, beanList, R.layout.main_index_item);
        indexListView.setAdapter(adapter);
        // item点击事件
        indexListView.setOnItemClickListener(onItemClickListener);

        // 解决键盘隐藏后，输入框的焦点还在的问题
        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                Log.d("keyboard", "keyboard visible: " + isVisible);
                if (!isVisible) {
                    searchEdit.setGravity(Gravity.CENTER);
                    searchEdit.setCursorVisible(false);
                } else {
                    searchEdit.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    searchEdit.setPadding(15, 0, 0, 0);
                    searchEdit.setCursorVisible(true);
                }
            }
        });
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemNoDoubleClick(AdapterView<?> parent, View view, int position, long id) {
            switch (beanList.get(position).getId()) {
                case "0": // 横向滑动选择按钮
                    startActivity(new Intent(MainActivity.this, HorizontalCheckActivity.class));
                    break;
                case "1": // 更换语言
                    startActivity(new Intent(MainActivity.this, ReplaceLanguageActivity.class));
                    break;
                case "2": // 图片列表/多图预览
                    startActivity(new Intent(MainActivity.this, ImgGridViewActivity.class));
                    break;
                case "3": // 日历
                    startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                    break;
                case "4": // 清理缓存
                    startActivity(new Intent(MainActivity.this, ClearCacheActivity.class));
                    break;
                case "5": // Activity开启动画(从下向上)/带提示输入框
                    startActivity(new Intent(MainActivity.this, EditDemoActivity.class));
                    MainActivity.this.overridePendingTransition(R.anim.open_show_up, 0);
                    break;
                case "6": // 下拉菜单
                    startActivity(new Intent(MainActivity.this, DownMenuActivity.class));
                    break;
                case "7": // 滑动返回
                    startActivity(new Intent(MainActivity.this, BackLayoutActivity.class));
                    break;
                case "8": // 消息小红点
                    startActivity(new Intent(MainActivity.this, BadgeViewActivity.class));
                    break;
                case "9": // 点赞
                    startActivity(new Intent(MainActivity.this, GoodLikeActivity.class));
                    break;
                case "10": // 刷新加载
                    startActivity(new Intent(MainActivity.this, SmartRefreshActivity.class));
                    break;
                case "11": // 简单的侧滑功能
                    startActivity(new Intent(MainActivity.this, EasySwipeActivity.class));
                    break;
                case "12": //滑动删除/item拖拽
                    startActivity(new Intent(MainActivity.this, BRVAHSwipeActivity.class));
                    break;
            }
        }
    };

    /**
     * 搜索框相关监听
     */
    private void searchListener() {
        // 搜索框输入监听
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) { // 输入内容为空
                    if (adapter != null) {
                        adapter.clear();
                    }
                    if (!beanList.isEmpty()) {
                        beanList.clear();
                    }
                    indexCursor = database.selectAll();
                    if (indexCursor.getCount() > 0) {
                        indexCursor.moveToFirst();
                        for (int i = 0; i < indexCursor.getCount(); i++) {
                            IndexBean indexBean = new IndexBean();
                            indexBean.setId(indexCursor.getString(0));
                            indexBean.setMessage(indexCursor.getString(1));
                            beanList.add(indexBean);
                            indexCursor.moveToNext();
                        }
                    }
                } else { // 输入内容不为空
                    if (adapter != null) {
                        adapter.clear();
                    }
                    if (!beanList.isEmpty()) {
                        beanList.clear();
                    }
                    indexCursor = database.searchFuzzy(s.toString());
                    if (indexCursor.getCount() > 0) {
                        indexCursor.moveToFirst();
                        for (int i = 0; i < indexCursor.getCount(); i++) {
                            IndexBean indexBean = new IndexBean();
                            indexBean.setId(indexCursor.getString(0));
                            indexBean.setMessage(indexCursor.getString(1));
                            beanList.add(indexBean);
                            indexCursor.moveToNext();
                        }
                    }
                }
            }
        });
    }

}
