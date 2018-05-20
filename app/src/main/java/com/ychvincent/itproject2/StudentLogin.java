package com.ychvincent.itproject2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class StudentLogin extends AppCompatActivity
{

    public EditText et_inputStuID;
    public EditText et_inputPW;
    public Button btn_login;
    public String savedStuID, savedPW;
    public boolean LoggedIn = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        //find views
        btn_login = (Button)findViewById(R.id.btn_login);
        et_inputStuID =(EditText)findViewById(R.id.et_inputStuID);
        et_inputPW =(EditText)findViewById(R.id.et_inputPW);



        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(et_inputStuID.getText().length() > 9 )
                {
                    et_inputStuID.setError("Student ID should be 9 words long");
                }
                else if(et_inputPW.getText().length() == 0)
                {
                    et_inputPW.setError("Password should not be null");
                }
                else if(et_inputStuID.getText().length() == 0)
                {
                    et_inputStuID.setError("You should enter you student Id");
                }
                else
                {
                    //must to be , from editable to string
                    savedStuID = et_inputStuID.getText().toString();
                    savedPW = et_inputPW.getText().toString();
                    try
                    {
                        LoggedIn = new GetStudentInfo().execute(savedStuID, savedPW).get();
                        if(LoggedIn == true)
                        {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("studentID", savedStuID);
                            editor.putString("studentPW", savedPW);
                            editor.commit();

                            Intent intent = new Intent(StudentLogin.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
