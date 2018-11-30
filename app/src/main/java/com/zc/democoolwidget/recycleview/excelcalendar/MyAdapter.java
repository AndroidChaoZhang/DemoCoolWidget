package com.zc.democoolwidget.recycleview.excelcalendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.democoolwidget.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZC on 2018/1/14.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public List<Map<String,Object>> datas = null;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MyAdapter(List<Map<String,Object>> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void reflushView(List<Map<String,Object>> dateList) {
        this.datas = dateList;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_back_calendar_lefttop,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Map<String, Object> mapInfo = datas.get(position);
        String date = MapUtils.getObject(mapInfo.get("date"));
        String isSelect = MapUtils.getObject(mapInfo.get("isSelect"));
        String dayWeekEnd = getWhatDayWeek(date, context.getResources());
        if (!TextUtils.isEmpty(date) && date.length()>=5) {
            date = date.substring(5,date.length());
        }
        if ("周六".equals(dayWeekEnd) || "周日".equals(dayWeekEnd)) {
            if ("1".equals(isSelect)) {//默认选中
                date = date+"<br><font color='#FFFFFF'>"+ dayWeekEnd +"</font>";
            } else {
                date = date+"<br><font color='#66C3D5'>"+ dayWeekEnd +"</font>";
            }
        } else {
            date = date+"<br>"+ dayWeekEnd;
        }
        viewHolder.mTextView.setText(Html.fromHtml(date));

        if ("1".equals(isSelect)) {//默认选中
            viewHolder.mTextView.setBackgroundResource(R.color.be_gray);
            viewHolder.mTextView.setTextColor(Color.WHITE);
        } else {
            viewHolder.mTextView.setBackgroundResource(R.color.base_line);
            viewHolder.mTextView.setTextColor(Color.parseColor("#696969"));
        }

        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }catch (Exception e){}
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_price);
        }
    }


    public interface  OnItemClickListener {
        void onItemClick(int adapterPosition);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public static String getWhatDayWeek(String dateStr, Resources rs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            return "";
        }

        String week = "";
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

}
