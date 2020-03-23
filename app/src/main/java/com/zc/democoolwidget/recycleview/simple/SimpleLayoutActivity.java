package com.zc.democoolwidget.recycleview.simple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zc.democoolwidget.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleLayoutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_simple);
        initRecycleView();
    }

    private void initRecycleView() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(""+i);
        }
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new CustomLayoutManager());
        recycler_view.setAdapter(new MyAdapter(list));
    }

    static class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        int i = 0;

        public MyAdapter(@Nullable List<String> data) {
            super(R.layout.item_text, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ((TextView)helper.getView(R.id.tv_msg_item)).setText("测试数据："+item);
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("MyLog","=====onCreateViewHolder"+i++);
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    public static void startSimpleLayout (Context context) {
        context.startActivity(new Intent(context,SimpleLayoutActivity.class));
    }


}
