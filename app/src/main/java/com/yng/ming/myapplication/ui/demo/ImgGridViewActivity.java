package com.yng.ming.myapplication.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yng.ming.myapplication.R;
import com.yng.ming.myapplication.adapter.ImageAdapter;
import com.yng.ming.myapplication.base.BaseActivity;
import com.yng.ming.myapplication.base.OnItemClickListener;
import com.yng.ming.myapplication.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 图片列表
 */
public class ImgGridViewActivity extends BaseActivity {

    @Bind(R.id.imgGridView)
    MyGridView imgGridView;

    private ImageAdapter adapter;
    private ArrayList<String> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_grid_view);
        imgList.clear();
        setToolbar();
        setDate();
        adapter = new ImageAdapter(this, imgList, R.layout.grid_view_image_item);
        imgGridView.setAdapter(adapter);
        imgGridView.setOnItemClickListener(onItemClickListener);
    }

    private void setToolbar() {
        setTitleText("图片列表");
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemNoDoubleClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ImgGridViewActivity.this, MultiPreviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("imgList", imgList);
            bundle.putInt("index", position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void setDate() {
        imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=720891101,4253860064&fm=27&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4271910516,424854353&fm=200&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=73823530,1601728755&fm=27&gp=0.jpg");
        imgList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1898549709,1657783017&fm=27&gp=0.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504696121172&di=055a32b79b7a75de0ab7193e8c4d3525&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D624edb750633874488c8273f3966b38c%2Feaf81a4c510fd9f94340d04c2f2dd42a2834a444.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504696135591&di=3996e85d9c3a51469ec9117646beb12b&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1317216917%2C3380671759%26fm%3D214%26gp%3D0.jpg");
    }
}
