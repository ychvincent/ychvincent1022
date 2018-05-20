package com.ychvincent.itproject2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ychvincent on 17/5/2018.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<AttendanceInfoHolder> attendanceInfoHolderList;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardview_tv_attendtime, cardview_tv_moduletime, cardview_tv_modulename,cardview_tv_moduleid;

        public ViewHolder(View view) {
            super(view);
            cardview_tv_attendtime = (TextView) view.findViewById(R.id.cardview_tv_attendtime);
            cardview_tv_modulename = (TextView)view.findViewById(R.id.cardview_tv_modulename);
            cardview_tv_moduletime = (TextView)view.findViewById(R.id.cardview_tv_moduletime);
            cardview_tv_moduleid = (TextView)view.findViewById(R.id.cardview_tv_moduleid);

        }
    }

    public AttendanceAdapter( Context context, List<AttendanceInfoHolder> attendanceInfoHolderList) {
        this.context = context;
        this.attendanceInfoHolderList = attendanceInfoHolderList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder v = new ViewHolder(itemView);
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        AttendanceInfoHolder message = attendanceInfoHolderList.get(position);
        message.setModuleid(message.getModuleid());
        message.setModulestarttime(message.getModulestarttime());
        message.setModulename(message.getModulename());
        message.setAttendtime(message.getAttendtime());
        message.setGetModuleendtime(message.getGetModuleendtime());

        ((ViewHolder) holder).cardview_tv_moduleid.setText(message.getModuleid());
        ((ViewHolder) holder).cardview_tv_attendtime.setText(message.getAttendtime());
        ((ViewHolder) holder).cardview_tv_modulename.setText(message.getModulename());
        ((ViewHolder) holder).cardview_tv_moduletime.setText(message.getModulestarttime() + " - " + message.getGetModuleendtime());

    }

    @Override
    public int getItemCount()
    {
        return attendanceInfoHolderList.size();
    }



}