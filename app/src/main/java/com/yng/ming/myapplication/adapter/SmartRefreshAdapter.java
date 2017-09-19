package com.yng.ming.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.model.SmartRefreshBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by Bright Lee on 2017/9/19 0019
 */

public class SmartRefreshAdapter extends SuperAdapter<SmartRefreshBean> {

    public SmartRefreshAdapter(Context context, List<SmartRefreshBean> items, @LayoutRes int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SmartRefreshBean item) {
        holder.setText(R.id.nameText, item.getName());
        holder.setText(R.id.phoneText, item.getPhone());
    }

}
