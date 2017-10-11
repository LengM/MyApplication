package com.yng.ming.myapplication.ui.demo.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.adapter.DynamicDataPagerAdapter;
import com.yng.ming.myapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 左右无限翻页
 */
public class DynamicDataPagerActivity extends BaseActivity {

    @Bind(R.id.dynamicDataViewPager)
    ViewPager dynamicDataViewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_data_pager);
        setToolbar();
        init();
    }

    private void setToolbar() {
        setTitleText("左右无限翻页");
    }

    private void init() {
        setData();
        final DynamicDataPagerAdapter adapter = new DynamicDataPagerAdapter(getSupportFragmentManager(), fragmentList);
        dynamicDataViewPager.setAdapter(adapter);
        dynamicDataViewPager.setCurrentItem(1);
        dynamicDataViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 2) {
                    setData();
                    dynamicDataViewPager.setCurrentItem(1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setData() {
        fragmentList.clear();
        stringList.clear();
        for (int i = 0; i < 10; i++) {
            stringList.add(String.valueOf((int) (Math.random() * 100)));
        }
        DynamicDataFragment fragment = new DynamicDataFragment();
        fragment.setData(stringList);
        fragmentList.add(fragment);
        DynamicDataFragment fragment1 = new DynamicDataFragment();
        fragment1.setData(stringList);
        fragmentList.add(fragment1);
        DynamicDataFragment fragment2 = new DynamicDataFragment();
        fragment2.setData(stringList);
        fragmentList.add(fragment2);
    }

}
