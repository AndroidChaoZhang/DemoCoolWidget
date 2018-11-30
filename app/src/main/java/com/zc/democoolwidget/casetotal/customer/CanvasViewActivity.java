package com.zc.democoolwidget.casetotal.customer;

import android.app.Activity;
import android.os.Bundle;

import com.zc.democoolwidget.R;

/**
 * Created by NEU on 2018/3/15.
 * 安卓自定义View进阶
 * http://www.gcssloop.com/customview/CustomViewIndex/
 */

public class CanvasViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_view);

    }

//        PieView view = new PieView(this);
//        setContentView(view);
//
//        ArrayList<PieData> datas = new ArrayList<>();
//        PieData pieData = new PieData("sloop", 60);
//        PieData pieData2 = new PieData("sloop", 30);
//        PieData pieData3 = new PieData("sloop", 40);
//        PieData pieData4 = new PieData("sloop", 20);
//        PieData pieData5 = new PieData("sloop", 20);
//        datas.add(pieData);
//        datas.add(pieData2);
//        datas.add(pieData3);
//        datas.add(pieData4);
//        datas.add(pieData5);
//        view.setData(datas);


}
