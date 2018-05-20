package com.ychvincent.itproject2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class KKOCK_Profile extends AppCompatActivity
{

    public TextView tv_stuName;
    public TextView tv_stuProgramme;
    public TextView tv_stuID;
    public ArrayList studentDetails = new ArrayList();
    public String studentID;
    public String TAG = "KKOCK_profile";
    public Context context;
    public List<AttendanceInfoHolder> attendanceInfoHolderList;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkock__profile);

        //find views
        tv_stuName = (TextView)findViewById(R.id.tv_stuName);
        tv_stuProgramme = (TextView)findViewById(R.id.tv_stuProgramme);
        tv_stuID = (TextView)findViewById(R.id.tv_stuID);
        context = getApplicationContext();


        //get sharedpreference data
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        studentID = pref.getString("studentID" , "");


        //connect to db and fetch data
        try
        {

            studentDetails = new GetStudentDetails().execute(studentID).get();
            //Log.d(TAG, "student details"  + studentDetails);
            tv_stuID.setText(studentDetails.get(0).toString());
            tv_stuName.setText(studentDetails.get(2).toString() +" " +  studentDetails.get(3).toString());
            tv_stuProgramme.setText(studentDetails.get(1).toString());

            //fetch data from db (moduleid, modulename, moduletime, attend time)
            attendanceInfoHolderList = new GetModuleInfo().execute(studentID).get();

            //card view
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.profile_recycler_view);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(llm);

            AttendanceAdapter attendanceAdapter = new AttendanceAdapter(context, attendanceInfoHolderList);
            recyclerView.setAdapter(attendanceAdapter);

            //fab events
            FloatingActionButton fab_showqr = (FloatingActionButton)findViewById(R.id.fab_showqr);
            fab_showqr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(KKOCK_Profile.this, Profile_showqr.class);
                    intent.putExtra("stuid", studentDetails.get(0).toString());
                    intent.putExtra("stuProgramme", studentDetails.get(1).toString());
                    startActivity(intent);
                    //Toast.makeText(KKOCK_Profile.this, "Fab clicked", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }


    }
}
