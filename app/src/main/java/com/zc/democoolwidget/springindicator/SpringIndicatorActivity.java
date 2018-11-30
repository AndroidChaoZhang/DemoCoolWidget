package com.zc.democoolwidget.springindicator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.springindicator.library.SpringIndicator;
import com.zc.democoolwidget.springindicator.library.adapter.ModelPagerAdapter;
import com.zc.democoolwidget.springindicator.library.adapter.PagerModelManager;
import com.zc.democoolwidget.springindicator.library.viewpager.ScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/chenupt/SpringIndicator
 */
public class SpringIndicatorActivity extends AppCompatActivity {

    ScrollerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_indicator);

        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed(1000);

        // just set viewPager
        springIndicator.setViewPager(viewPager);

    }

    private List<String> getTitles(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        return list;
    }

    private List<Integer> getBgRes(){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.bg1);
        list.add(R.drawable.bg2);
        list.add(R.drawable.bg3);
        list.add(R.drawable.bg4);
        list.add(R.drawable.bg1);
        return list;
    }

}
