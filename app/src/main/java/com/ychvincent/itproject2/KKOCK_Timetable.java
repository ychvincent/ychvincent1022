package com.ychvincent.itproject2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.TimeZone;

public class KKOCK_Timetable extends AppCompatActivity {

    public CompactCalendarView calendarView;
    public Long timestamp;
    public List<TimeTableHolder> timeTableHolderList ;
    public Context context;
    public RecyclerView timetable_recycler_view;
    public ArrayList al = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkock__timetable);
        calendarView = (CompactCalendarView)findViewById(R.id.compactCalendar);
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        calendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);
        calendarView.setUseThreeLetterAbbreviation(true);
        //card view
        timetable_recycler_view = (RecyclerView)findViewById(R.id.timetable_recycler_view);
        timetable_recycler_view.setHasFixedSize(true);


        try {
            timetable_recycler_view.setHasFixedSize(true);
            Event event1 = new Event(Color.RED,timeStampConverter("2018-05-25"), "IT Project" );
            calendarView.addEvent(event1);
            Event event2 = new Event(Color.RED, timeStampConverter("2018-05-29"), "IT Project Demo");
            calendarView.addEvent(event2);

            calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    al.clear();
                    //fetch data
                   al.add(calendarView.getEvents(dateClicked).get(0).getTimeInMillis());
                   al.add(calendarView.getEvents(dateClicked).get(0).getData().toString());

                   timeTableHolderList = al;


                    Log.d("test", "testing" + timeTableHolderList);
                    Toast.makeText(getApplicationContext(), "You have a lesson of " + calendarView.getEvents(dateClicked).get(0).getData().toString(), Toast.LENGTH_SHORT).show();


                    LinearLayoutManager llm = new LinearLayoutManager(context);
                    timetable_recycler_view.setLayoutManager(llm);

                    TimeTableAdapter timeTableAdapter = new TimeTableAdapter(context, timeTableHolderList);
                    timetable_recycler_view.setAdapter(timeTableAdapter);
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Long timeStampConverter(String date) throws ParseException {
        String input = date;
        Long tmp;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = (Date)formatter.parse(input);
        timestamp = myDate.getTime();
        return timestamp;
    }

}
