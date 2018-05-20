package com.ychvincent.itproject2;

import android.os.AsyncTask;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ychvincent on 17/5/2018.
 */

public class GetStudentInfo extends AsyncTask<String, String, Boolean>
{
    public String stuid;
    public String stupw;
    public Connection conn;
    public PreparedStatement preparedStatement;
    public ResultSet resultSet;
    public String datafromdb;
    public boolean returnValue;
    public String ip;

    @Override
    protected Boolean doInBackground(String... strings)
    {

        stuid = strings[0];
        stupw = strings[1];
        try
        {

            IP myip = new IP();
            ip=myip.getIp();
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://" +ip+"/sys", "root", "rootpass");
            preparedStatement = conn.prepareStatement("Select student_password from student_info where student_id =" + stuid );
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                datafromdb = resultSet.getString(1);
            }
            if (datafromdb.equals(stupw))
            {
                returnValue =  true;
            }
            else
            {
                returnValue  = false;
            }
        }
        catch(ClassNotFoundException e1)
        {
            e1.printStackTrace();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return returnValue;
    }
}
