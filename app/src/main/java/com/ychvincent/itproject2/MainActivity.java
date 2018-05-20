package com.ychvincent.itproject2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Activity acitivity;

    private TextView tv_jsonArea;
    private Button btn_conversation, btn_profile, btn_timetable;
    public String TAG = "MainActivity";
    public LanguageHolder languageHolder = new LanguageHolder();
    public String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        studentID = pref.getString("studentID", "0");
        //if haven't register
        if(studentID.equals("0") || studentID.equals(""))
        {
            Intent intent = new Intent(MainActivity.this, StudentLogin.class);
            startActivity(intent);
        }

        //find views
        btn_conversation = (Button)findViewById(R.id.btn_conversation);
        btn_profile =(Button)findViewById(R.id.btn_profile);
        btn_timetable = (Button)findViewById(R.id.btn_timetable);

        //onclick events
        btn_conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KKOCK_Conversation.class);
                startActivity(intent);
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, KKOCK_Profile.class);
                startActivity(intent);
            }
        });
        btn_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KKOCK_Timetable.class);
                startActivity(intent);
            }
        });


    }
}
