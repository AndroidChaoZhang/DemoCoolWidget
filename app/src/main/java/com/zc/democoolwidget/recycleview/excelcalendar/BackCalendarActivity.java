package com.zc.democoolwidget.recycleview.excelcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zc.democoolwidget.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NEU on 2018/1/15.
 */

public class BackCalendarActivity extends Activity implements SimpleAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView mList,rv_left,rv_top;
    private SimpleAdapter contentAdapter;
    private TextView tv_travel_price,tv_travel_date_week;
    /**中间内容List*/
    private List<Map<String,Object>> priceList = new ArrayList<>();
    /**出发日期List*/
    private List<Map<String,Object>> startDateList = new ArrayList<>();
    /**返程日期List*/
    private List<Map<String,Object>> backDateList = new ArrayList<>();
    /**选中的坐标信息*/
    private Map<String,Object> selectPriceInfo = new HashMap<>();
    private MyAdapter leftAdapter,topAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_calendar);
        tv_travel_price = (TextView) findViewById(R.id.tv_travel_price);
        tv_travel_date_week = (TextView) findViewById(R.id.tv_travel_date_week);

        final String startDate = DateUtils.getNowTime("yyyy-MM-dd");
        String backDate = DateUtils.getAfterDate(startDate,10);

        startDateList = getDateList(startDate);
        backDateList = getDateList(backDate);
        int width = DpPxSpUtil.dip2px(this,60);
        changeSelectPosition(startDate,backDate,false);

        mList = (RecyclerView) findViewById(R.id.section_list);
        rv_left = (RecyclerView)findViewById(R.id.rv_left);
        rv_top = (RecyclerView)findViewById(R.id.rv_top);
        CardLayoutManager manager = new CardLayoutManager(31,width);
        mList.setHasFixedSize(true);
        ((SimpleItemAnimator)mList.getItemAnimator()).setSupportsChangeAnimations(false);
        mList.getItemAnimator().setChangeDuration(0);
        mList.setItemAnimator(null);
        mList.setLayoutManager(manager);
        contentAdapter = new SimpleAdapter(priceList);
        contentAdapter.setOnItemClickListener(this);
        mList.setAdapter(contentAdapter);

        rv_left.setLayoutManager(new LinearLayoutManager(this));
        leftAdapter = new MyAdapter(backDateList, this);
        rv_left.setAdapter(leftAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_top.setLayoutManager(layoutManager);
        topAdapter = new MyAdapter(startDateList, this);
        rv_top.setAdapter(topAdapter);


        mList.addOnScrollListener(contentScrollListener);
        rv_left.addOnScrollListener(leftScrollListener);
        rv_top.addOnScrollListener(topScrollListener);

        mList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isContentScroll = true;
                isTopLeftScroll = false;
                return false;
            }
        });
        rv_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTopLeftScroll = true;
                isContentScroll = false;
                return false;
            }
        });
        rv_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTopLeftScroll = true;
                isContentScroll = false;
                return false;
            }
        });


        findViewById(R.id.tv_confirm).setOnClickListener(this);

        String startFirstDate = MapUtils.getObject(startDateList.get(0).get("date"));
        String backFirstDate = MapUtils.getObject(backDateList.get(0).get("date"));
        int defaultRowPosition = DateUtils.daysBetween(startFirstDate, startDate);//默认行位置
        int defaultColPosition = DateUtils.daysBetween(backFirstDate, backDate);//默认列位置
        if (defaultRowPosition >=3 || defaultColPosition>=3) {
            mList.smoothScrollBy(width * (defaultRowPosition - 2), width * (defaultColPosition - 2));
        }

        topAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int adapterPosition) {
                Map<String, Object> mapInfo = startDateList.get(adapterPosition);
                String date = MapUtils.getObject(mapInfo.get("date"));
                changeSelectPosition(date,MapUtils.getObject(selectPriceInfo.get("back")),true);
                contentAdapter.reflushView(priceList);
                topAdapter.reflushView(startDateList);
            }
        });
        leftAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int adapterPosition) {
                Map<String, Object> mapInfo = backDateList.get(adapterPosition);
                String date = MapUtils.getObject(mapInfo.get("date"));
                changeSelectPosition(MapUtils.getObject(selectPriceInfo.get("go")),date,true);
                contentAdapter.reflushView(priceList);
                leftAdapter.reflushView(backDateList);
            }
        });

    }

    @Override
    public void onItemClick(int adapterPosition,int rowPosition,int colPosition) {
        selectPriceInfo.putAll(priceList.get(adapterPosition));
        String price = MapUtils.getObject(selectPriceInfo.get("price"));
        if ("--".equals(price)) {
            tv_travel_price.setText("--");
        } else {
            tv_travel_price.setText("¥"+price);
        }
        String goDate = MapUtils.getObject(selectPriceInfo.get("go"));
        String backDate = MapUtils.getObject(selectPriceInfo.get("back"));
        String goDateWeek = DateUtils.getDateAndWeek(goDate, this);
        String backDateWeek = DateUtils.getDateAndWeek(backDate, this);
        tv_travel_date_week.setText(String.format("去程：%1$s\n返程：%2$s", goDateWeek, backDateWeek));

        for (int i = 0; i < startDateList.size(); i++) {
            Map<String, Object> info = startDateList.get(i);
            if (colPosition == i) {
                info.put("isSelect", "1");
            } else {
                info.put("isSelect", "0");
            }
        }
        for (int i = 0; i < backDateList.size(); i++) {
            Map<String, Object> info = backDateList.get(i);
            if (rowPosition == i) {
                info.put("isSelect", "1");
            } else {
                info.put("isSelect", "0");
            }
        }
        leftAdapter.reflushView(backDateList);
        topAdapter.reflushView(startDateList);
    }

    private int amountAxisX = 0;
    private int amountAxisY = 0;
    /**
     * horizontal listener
     */
    private RecyclerView.OnScrollListener contentScrollListener
            = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dx == 0 && dy == 0) {
                return;
            }
            if (isContentScroll) {
                amountAxisX += dx;
                amountAxisY += dy;

//                rv_top.scrollBy(dx, 0);
//                rv_left.scrollBy(0, dy);
                fastScrollTo(amountAxisX, rv_top);
                fastScrollTo(amountAxisY, rv_left);
            }
        }
    };

    private RecyclerView.OnScrollListener leftScrollListener
            = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dx == 0 && dy == 0) {
                return;
            }
            if (isTopLeftScroll) {
                amountAxisY += dy;
                mList.scrollBy(0, dy);
            }
        }
    };
    private RecyclerView.OnScrollListener topScrollListener
            = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dx == 0 && dy == 0) {
                return;
            }
            if (isTopLeftScroll) {
                amountAxisX += dx;
                mList.scrollBy(dx, 0);
            }
        }
    };
    /**中间内容是否滑动*/
    private boolean isContentScroll = true;
    /**顶部左部内容是否滑动*/
    private boolean isTopLeftScroll = false;
    private void fastScrollTo(int amountAxis, RecyclerView recyclerView) {
        int position = 0, width = DpPxSpUtil.dip2px(this,60);
        position += amountAxis / width;
        amountAxis %= width;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //call this method the OnScrollListener's onScrolled will be called，but dx and dy always be zero.
        linearLayoutManager.scrollToPositionWithOffset(position, -(amountAxis));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                if (selectPriceInfo.isEmpty()) {
                    return;
                }
                String goDate = MapUtils.getObject(selectPriceInfo.get("go"));
                String backDate = MapUtils.getObject(selectPriceInfo.get("back"));
                int comp =DateUtils.compare_date(goDate,backDate);
                if (comp==1) {//去程不能大于返程
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("lowCalendarInfo", (Serializable) selectPriceInfo);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
    /**
     * @param isFlushItem   是否刷新item
     */
    private void changeSelectPosition (String startDate,String backDate,boolean isFlushItem) {
        selectPriceInfo.put("go",startDate);
        selectPriceInfo.put("back",backDate);

        String startFirstDate = MapUtils.getObject(startDateList.get(0).get("date"));
        String backFirstDate = MapUtils.getObject(backDateList.get(0).get("date"));
        int defaultRowPosition = DateUtils.daysBetween(startFirstDate, startDate);//默认行位置
        int defaultColPosition = DateUtils.daysBetween(backFirstDate, backDate);//默认列位置
        int selectPosition = defaultRowPosition + defaultColPosition * 31;
        if (priceList.size()>0) {
            for (int i=0;i<priceList.size();i++) {//默认加载内容
                Map<String, Object> mapInfo = priceList.get(i);
                if (isFlushItem) {
                    String rowColSelectString = MapUtils.getObject(mapInfo.get("rowColSelect"));
                    if (!"0".equals(rowColSelectString)) {//局部更新 先将展示的所有的选中数据（红色粉红色）全部清除
                        mapInfo.put("rowColSelect","0");
                        contentAdapter.notifyItemChanged(i);
                    }
                }

                if (selectPosition==i) {
                    mapInfo.put("rowColSelect","2");
                    if (isFlushItem) {
                        contentAdapter.notifyItemChanged(i);
                    }
                } else if (defaultColPosition == (i / 31 ) || defaultRowPosition == (i % 31)) {
                    mapInfo.put("rowColSelect","1");
                    if (isFlushItem) {
                        contentAdapter.notifyItemChanged(i);
                    }
                } else {
                    mapInfo.put("rowColSelect","0");
                }
            }
        } else {//初始化没有请求网络的数据
            Map<String,Object> mapInfo;
            for (int i=0;i<=960;i++) {//默认加载内容
                mapInfo = new ArrayMap<>();
                if (selectPosition==i) {
                    mapInfo.put("rowColSelect","2");
                } else if (defaultColPosition == (i / 31 ) || defaultRowPosition == (i % 31)) {
                    mapInfo.put("rowColSelect","1");
                } else {
                    mapInfo.put("rowColSelect","0");
                }
                mapInfo.put("price","--");
                priceList.add(mapInfo);
            }
        }

        //默认选中top left位置
        int indexStart = 0;
        for (Map<String, Object> startMapInfo:startDateList) {
            if (indexStart == defaultRowPosition) {
                startMapInfo.put("isSelect","1");
            } else {
                startMapInfo.put("isSelect","0");
            }
            indexStart++;
        }

        int indexEnd = 0;
        for (Map<String, Object> backMapInfo :backDateList) {
            if (indexEnd == defaultColPosition) {
                backMapInfo.put("isSelect","1");
            } else {
                backMapInfo.put("isSelect","0");
            }
            indexEnd++;
        }

        String goDateWeek = DateUtils.getDateAndWeek(startDate, this);
        String backDateWeek = DateUtils.getDateAndWeek(backDate, this);
        tv_travel_date_week.setText(String.format("去程：%1$s\n返程：%2$s",goDateWeek,backDateWeek));
    }

    /**获取去程返程的日期List*/
    private List<Map<String,Object>> getDateList (String startDate) {
        String nowDate = DateUtils.getNowTime(DateUtils.DEFAULT_DATE_FORMAT);
        String beforeDate = DateUtils.getAfterDate(startDate, -15);
        String afterDate = DateUtils.getAfterDate(startDate, 15);
        String afterYearDate = DateUtils.getAfterYearDate(1);
        int dayDirection = 1;
        if (beforeDate.compareTo(nowDate) > 0) {//日期比当前日期多15天 取日期
            startDate = beforeDate;
            if (afterDate.compareTo(afterYearDate)  > 0) {//日期超出一年后的日期 取一年后的日期
                dayDirection = -1;
                startDate = afterYearDate;
            }
        } else {
            startDate = nowDate;
        }
        return DateUtils.getAfterDays(startDate, 30 * dayDirection);
    }

}
