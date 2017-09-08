package com.yng.ming.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yng.ming.myapplication.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private ImgClickListener listener;

    public void setListener(ImgClickListener listener) {
        this.listener = listener;
    }

    public ImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_image_item, parent, false);
            holder.taskImage = (ImageView) convertView.findViewById(R.id.task_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            resetViewHolder(holder);
        }
        String bean = list.get(position);

        //加载网络图片
        final ViewHolder finalHolder = holder;
        Glide.with(context).load(bean).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                finalHolder.taskImage.setImageDrawable(resource);
            }
        });
        holder.taskImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.imgClick(position);
            }
        });

        return convertView;

    }

    private void resetViewHolder(ViewHolder holder) {
    }

    class ViewHolder {
        public ImageView taskImage;
    }

    public interface ImgClickListener {
        void imgClick(int position);
    }

}
