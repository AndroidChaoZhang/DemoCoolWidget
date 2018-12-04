package com.zc.democoolwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zc.democoolwidget.airchina.seat.AirChinaSeatActivity;
import com.zc.democoolwidget.calendarview.CalendarActivity;
import com.zc.democoolwidget.casetotal.MainCaseTotalActivity;
import com.zc.democoolwidget.imagegaosi.ImageGaosiActivity;
import com.zc.democoolwidget.magicindicator.ExampleMainActivity;
import com.zc.democoolwidget.recycleview.excelcalendar.BackCalendarActivity;
import com.zc.democoolwidget.rippleview.RippleViewActivity;
import com.zc.democoolwidget.springindicator.SpringIndicatorActivity;
import com.zc.democoolwidget.switchbutton.SwitchButtonActivity;
import com.zc.democoolwidget.water.WaterActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private List<String> dataList = new ArrayList<>(Arrays.asList("0.自定义类似的ToggButton(动画)", "1.点击涟漪动画RippleView", "2.类似Airchina的日期控件","3.Drawerlayout左侧菜单滑出炫动（Deleted）"
            ,"4.magicindicator滚动的viewPager GroupButton","5.水滴viewpager可爱骷髅头","6.高斯模糊图片","7.签到水波纹扩散动画",
            "8.国航往返日历excel以及菱形案例","9.总结案例","10.国航的飞机座位图","11.RxJava2的学习"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv_main = (ListView) findViewById(R.id.lv_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lv_main.setAdapter(adapter);
        lv_main.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this,SwitchButtonActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,RippleViewActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,CalendarActivity.class));
                break;
            case 4:
                startActivity(new Intent(this,ExampleMainActivity.class));
                break;
            case 5:
                startActivity(new Intent(this,SpringIndicatorActivity.class));
                break;
            case 6:
                startActivity(new Intent(this,ImageGaosiActivity.class));
                break;
            case 7:
                startActivity(new Intent(this,WaterActivity.class));
                break;
            case 8:
                startActivity(new Intent(this,BackCalendarActivity.class));
                break;
            case 9:
                startActivity(new Intent(this,MainCaseTotalActivity.class));
                break;
            case 10:
                startActivity(new Intent(this,AirChinaSeatActivity.class));
                break;
            case 11:
                break;
        }
    }

}
