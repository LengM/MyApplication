package com.yng.ming.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yng.ming.myapplication.R;

import java.util.List;

public class DownMenuAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private String chosenPrice;

    public DownMenuAdapter(Context context, List<String> list, String chosenPrice) {
        this.context = context;
        this.list = list;
        this.chosenPrice = chosenPrice;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.down_menu_popup_item, parent, false);
            holder.checkLayout = (RelativeLayout) convertView.findViewById(R.id.checkLayout);
            holder.checkView = convertView.findViewById(R.id.checkView);
            holder.tvItemContent = (TextView) convertView.findViewById(R.id.tvItemContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            resetViewHolder(holder);
        }
        String str = list.get(position);

        if (str.equals(chosenPrice)) {
            holder.checkLayout.setBackgroundColor(Color.parseColor("#F2F0F5"));
            holder.checkView.setVisibility(View.VISIBLE);
        } else {
            holder.checkLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.checkView.setVisibility(View.GONE);
        }
        holder.tvItemContent.setText(str);

        return convertView;

    }

    private void resetViewHolder(ViewHolder holder) {
    }

    private class ViewHolder {
        private RelativeLayout checkLayout;
        private View checkView;
        private TextView tvItemContent;
    }

}
