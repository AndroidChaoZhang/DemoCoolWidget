package com.zc.democoolwidget.recycleview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.recycleview.excelcalendar.BackCalendarActivity;
import com.zc.democoolwidget.recycleview.lingxing.MainLingXingActivity;
import com.zc.democoolwidget.recycleview.simple.SimpleLayoutActivity;

public class RecycleMainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_main);
    }

    public void startLingXing(View view) {
        startActivity(new Intent(this, MainLingXingActivity.class));
    }

    public void startSimple(View view) {
        SimpleLayoutActivity.startSimpleLayout(this);
    }

    public void startBackCalendar(View view) {
        startActivity(new Intent(this, BackCalendarActivity.class));
    }


    public static void startSimpleLayout (Context context) {
        context.startActivity(new Intent(context, RecycleMainActivity.class));
    }


}
