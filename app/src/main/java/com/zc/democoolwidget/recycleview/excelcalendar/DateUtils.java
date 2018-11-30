package com.zc.democoolwidget.recycleview.excelcalendar;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zc.democoolwidget.recycleview.excelcalendar.MyAdapter.getWhatDayWeek;

/**
 * Created by NEU on 2018/2/5.
 */

public class DateUtils {

    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static String DEFAULT_DATE_WITHOUT_YEAR_FORMAT = "MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            DEFAULT_DATE_FORMAT);

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time1 = cal.getTimeInMillis();
        try {
            cal.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /***
     * 获取时间格式  yy-mm 周几
     * @param date
     * @param context
     * @return
     */
    public static String getDateAndWeek(String date, Context context) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(
                DEFAULT_DATE_WITHOUT_YEAR_FORMAT);
        try {
            result = sdf1.format(sdf.parse(date));
            result = result + " "+getWhatDayWeek(date,context.getResources());
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * date1<date2  return -1
     */
    public static int compare_date(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (ParseException e) {}
        return 0;
    }

    public static String getNowTime(String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());
        result = sdf.format(curDate);
        return result;
    }

    public static String getAfterDate (String day, int i) {
        Calendar c = Calendar.getInstance();
        Date beforeDate = null;
        try {
            beforeDate = dateFormat.parse(day);
            c.setTime(beforeDate);
            c.add(Calendar.DATE, i);
            day = dateFormat.format(c.getTime());
        } catch (ParseException e) {
        }
        return day;
    }

    /**
     * 获取前后日期 i为年 向后推迟年，负数时向前提前i年
     */
    public static String getAfterYearDate (int i) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, i);
        return dateFormat.format(c.getTime());
    }

    /***获取某段时间前后多少天的所有日期 */
    public static List<Map<String,Object>> getAfterDays(String dateStart, int days) {
        List<Map<String,Object>> dateList = new ArrayList();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        try {
            calBegin.setTime(dateFormat.parse(dateStart));
            if (days <0) {
                calBegin.add(Calendar.DATE, days-1);
            }
            Map<String,Object> mapInfo ;
            for (int i =0;i<=Math.abs(days);i++) {
                mapInfo = new HashMap<>();
                if (i!=0) {
                    calBegin.add(Calendar.DATE, 1);
                }
                mapInfo.put("date",dateFormat.format(calBegin.getTime()));
                dateList.add(mapInfo);
            }
        } catch (ParseException e) {
        }
        return dateList;
    }

}
