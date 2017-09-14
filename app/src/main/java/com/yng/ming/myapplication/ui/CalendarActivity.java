package com.yng.ming.myapplication.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.othershe.calendarview.CalendarView;
import com.othershe.calendarview.DateBean;
import com.othershe.calendarview.listener.OnMonthItemClickListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnClickListener;
import com.yng.ming.myapplication.util.date.DateUtil;
import com.yng.ming.myapplication.view.BottomDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 日历
 */
public class CalendarActivity extends BaseActivity {

    @Bind(R.id.showDateView)
    TextView showDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setToolbar();
    }

    @OnClick({R.id.openCalendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openCalendar:
                showCalendar();
                break;
        }
    }

    private void setToolbar() {
        setTitleText("日历");
        setLeftText("返回");
        setLeftTextSize(12);
    }

    public void showCalendar() {

        // 存储已选择的日期字符串
        final String[] stringDate = {""};

        // 初始化视图
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.dialog_calendar, null);

        // 初始化标题（年月显示和左右箭头）
        final TextView title = (TextView) viewDialog.findViewById(R.id.title);
        // 初始化日历控件
        final CalendarView calendarView = (CalendarView) viewDialog.findViewById(R.id.calendar);

        // 如果有已经选择的日期，那么我们进行一次默认日期设置
        if (!showDateView.getText().toString().isEmpty()) {
            stringDate[0] = showDateView.getText().toString();
            String year = DateUtil.formatString(stringDate[0], "yyyy年MM月dd日", "yyyy");
            String month = DateUtil.formatString(stringDate[0], "yyyy年MM月dd日", "MM");
            String day = DateUtil.formatString(stringDate[0], "yyyy年MM月dd日", "dd");
            int[] dateInit = {Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)};
            calendarView.setDateInit(dateInit);
            // 设置标题显示内容
            title.setText(year + "年" + month + "月");
        } else {
            // 获取默认当前日期
            DateBean d = calendarView.getDateInit();
            // 设置标题显示内容
            title.setText(d.getSolar()[0] + "年" + d.getSolar()[1] + "月");
        }

        calendarView.init();

        // 监听页面修改(左右滑动日历) 修改后修改标题内容和存储日期字符串
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
//                stringDate[0] = date[0] + "年" + date[1] + "月" + date[2] + "日";
            }
        });

        // 监听日历中item点击(左右滑动日历) 点击后修改标题内容和存储日期字符串
        calendarView.setOnItemClickListener(new OnMonthItemClickListener() {
            @Override
            public void onMonthItemClick(View view, DateBean date) {
                title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                stringDate[0] = date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日";
            }
        });

        // 初始化自定义Dialog ==> 弹出在底部，宽度全屏
        final BottomDialog dialog = new BottomDialog(this);
        dialog.setView(viewDialog);
        dialog.show();

        // 左箭头点击事件
        ImageView backMonth = (ImageView) viewDialog.findViewById(R.id.backMonth);
        backMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                calendarView.lastMonth();
            }
        });

        // 右箭头点击事件
        ImageView nextMonth = (ImageView) viewDialog.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                calendarView.nextMonth();
            }
        });

        // 底部取消按钮
        Button cancel = (Button) viewDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                dialog.dismiss();
            }
        });

        // 底部确定按钮
        Button fix = (Button) viewDialog.findViewById(R.id.fix);
        fix.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                showDateView.setText(stringDate[0]);
                dialog.dismiss();
            }
        });

    }

}
