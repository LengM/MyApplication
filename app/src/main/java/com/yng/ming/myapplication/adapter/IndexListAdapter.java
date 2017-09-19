package com.yng.ming.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.model.IndexBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by Bright Lee on 2017/9/19 0019
 */

public class IndexListAdapter extends SuperAdapter<IndexBean> {

    public IndexListAdapter(Context context, List<IndexBean> items, @LayoutRes int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, IndexBean item) {
        holder.setText(R.id.indexButton, item.getMessage());
    }

}
