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

public class GetStudentDetails extends AsyncTask<String, String, ArrayList>
{
    public ArrayList fetchDetails = new ArrayList();
    public Connection conn;
    public PreparedStatement preparedStatement;
    public ResultSet resultSet;
    public String stuid;
    public String ip;


    @Override
    protected ArrayList doInBackground(String... strings)
    {

        try
        {
            stuid = strings[0];
            IP myip = new IP();
            ip = myip.getIp();

            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + ip +  "/sys", "root", "rootpass");
            preparedStatement = conn.prepareStatement("Select * from student_info where student_id =" + stuid );
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                // start with 1
                fetchDetails.add(resultSet.getString(1));
                fetchDetails.add(resultSet.getString(2));
                fetchDetails.add(resultSet.getString(3));
                fetchDetails.add(resultSet.getString(4));
                fetchDetails.add(resultSet.getString(5));
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



        return fetchDetails;
    }
}
