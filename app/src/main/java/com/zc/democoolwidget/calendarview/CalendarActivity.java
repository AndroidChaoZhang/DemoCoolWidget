package com.zc.democoolwidget.calendarview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.calendarview.library.DatePickerController;
import com.zc.democoolwidget.calendarview.library.DayPickerView;
import com.zc.democoolwidget.calendarview.library.SimpleMonthAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * https://github.com/henry-newbie/CalendarView
 */
public class CalendarActivity extends AppCompatActivity {

    DayPickerView dayPickerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        context = this;
        dayPickerView = (DayPickerView) findViewById(R.id.dpv_calendar);

        DayPickerView.DataModel dataModel = new DayPickerView.DataModel();
        dataModel.yearStart = 2017;
        dataModel.monthStart = 4;
        dataModel.monthCount = 10;
        dataModel.defTag = "￥100";
        dataModel.leastDaysNum = 2;
        dataModel.mostDaysNum = 100;

        List<SimpleMonthAdapter.CalendarDay> invalidDays = new ArrayList<>();
        SimpleMonthAdapter.CalendarDay invalidDay1 = new SimpleMonthAdapter.CalendarDay(2017, 8, 10);
        SimpleMonthAdapter.CalendarDay invalidDay2 = new SimpleMonthAdapter.CalendarDay(2017, 8, 11);
        SimpleMonthAdapter.CalendarDay invalidDay3 = new SimpleMonthAdapter.CalendarDay(2017, 8, 12);
        invalidDays.add(invalidDay1);
        invalidDays.add(invalidDay2);
        invalidDays.add(invalidDay3);
        dataModel.invalidDays = invalidDays;

        List<SimpleMonthAdapter.CalendarDay> busyDays = new ArrayList<>();
        SimpleMonthAdapter.CalendarDay busyDay1 = new SimpleMonthAdapter.CalendarDay(2017, 8, 20);
        SimpleMonthAdapter.CalendarDay busyDay2 = new SimpleMonthAdapter.CalendarDay(2017, 8, 21);
        SimpleMonthAdapter.CalendarDay busyDay3 = new SimpleMonthAdapter.CalendarDay(2017, 8, 22);
        busyDays.add(busyDay1);
        busyDays.add(busyDay2);
        busyDays.add(busyDay3);
        dataModel.busyDays = busyDays;

//        dataModel.startCalendar = stringXCalendar("2017-5-20");
//        dataModel.endCalendar = stringXCalendar("2017-7-20");

        SimpleMonthAdapter.CalendarDay startCalendar = new SimpleMonthAdapter.CalendarDay(2017, 6, 5);
        SimpleMonthAdapter.CalendarDay endCalendar = new SimpleMonthAdapter.CalendarDay(2017, 6, 20);
        SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays = new SimpleMonthAdapter.SelectedDays<>(startCalendar, endCalendar);
        dataModel.selectedDays = selectedDays;

        SimpleMonthAdapter.CalendarDay tag = new SimpleMonthAdapter.CalendarDay(2017, 7, 15);
        tag.setTag("标签1");

        SimpleMonthAdapter.CalendarDay tag2 = new SimpleMonthAdapter.CalendarDay(2017, 8, 15);
        tag2.setTag("标签2");
        List<SimpleMonthAdapter.CalendarDay> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        dataModel.tags = tags;

        dayPickerView.setParameter(dataModel, new DatePickerController() {
            @Override
            public void onDayOfMonthSelected(SimpleMonthAdapter.CalendarDay calendarDay) {
                Toast.makeText(context,""+ calendarDay.getDate(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(List<SimpleMonthAdapter.CalendarDay> selectedDays) {
                Toast.makeText(context, "onDateRangeSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void alertSelectedFail(FailEven even) {
                Toast.makeText(context, "alertSelectedFail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Calendar stringXCalendar (String stringCalendar) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(stringCalendar);
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar;
    }
}
