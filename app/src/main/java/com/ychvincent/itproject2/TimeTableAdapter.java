package com.ychvincent.itproject2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by ychvi on 18/5/2018.
 */

public class TimeTableAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<TimeTableHolder> timeTableHolderList;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardview_timetable_moduletime, cardview_timetable_modulename;

        public ViewHolder(View view) {
            super(view);
            cardview_timetable_modulename = (TextView) view.findViewById(R.id.cardview_timetable_modulename);
            cardview_timetable_moduletime = (TextView)view.findViewById(R.id.cardview_timetable_moduletime);
        }
    }

    public TimeTableAdapter(Context context, List<TimeTableHolder> timeTableHolderList) {
        this.context = context;
        this.timeTableHolderList = timeTableHolderList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_timetable, parent, false);
        ViewHolder v = new ViewHolder(itemView);
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        TimeTableHolder message = timeTableHolderList.get(position);
        message.setData(message.getData());
        message.setTimestamp(message.getTimestamp());

        ((ViewHolder) holder).cardview_timetable_modulename.setText(message.getData());
        ((ViewHolder) holder).cardview_timetable_moduletime.setText(getDate(message.getTimestamp()));


    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    private String getDate(long time_stamp_server) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(time_stamp_server);
    }


}
