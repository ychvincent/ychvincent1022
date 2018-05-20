package com.ychvincent.itproject2;

import android.os.AsyncTask;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ychvincent on 17/5/2018.
 */

public class GetModuleInfo extends AsyncTask<String, String, ArrayList>
{
    public ArrayList returnInfo = new ArrayList();
    public String studentid;
    public String ip;
    public Connection conn;
    public PreparedStatement preparedStatement;
    public ResultSet resultSet;




    @Override
    protected ArrayList doInBackground(String... strings)
    {

        studentid = strings[0];
        try
        {


            IP myip = new IP();
            ip = myip.getIp();
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + ip + "/sys", "root", "rootpass");
            preparedStatement = conn.prepareStatement("SELECT module_info.module_id, module_info.module_name, module_info.module_starttime, module_info.module_endtime, itproject.attend_time from itproject,module_info where itproject.student_id = " + studentid);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                // start with 1
                returnInfo.add(new AttendanceInfoHolder(resultSet.getString(1).toString(), resultSet.getString(2).toString(), resultSet.getString(3).toString(), resultSet.getString(4).toString(), resultSet.getString(5).toString()));


            }




        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


        return returnInfo;
    }
}

