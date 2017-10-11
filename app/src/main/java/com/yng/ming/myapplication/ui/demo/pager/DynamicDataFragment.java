package com.yng.ming.myapplication.ui.demo.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.base.BaseFragment;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by LengM on 2017/10/11 0011
 */
public class DynamicDataFragment extends BaseFragment {

    @Bind(R.id.dynamicDataListView)
    ListView dynamicDataListView;

    List<String> stringList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_data_pager, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    public void setData(List<String> stringList) {
        this.stringList = stringList;
    }

    private void init() {
        dynamicDataListView.setAdapter(new DynamicDataAdapter(getActivity(), stringList));
    }

    private class DynamicDataAdapter extends SuperAdapter<String> {

        DynamicDataAdapter(Context context, List<String> items) {
            super(context, items, R.layout.fragment_dynamic_data_pager_item);
        }

        @Override
        public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
            holder.setText(R.id.dataTextView, item);
        }

    }

}
