package com.ychvincent.itproject2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by ychvincent on 10/5/2018.
 */

public class AI extends AsyncTask<String, String, String>
{
    URL url;
    //private String path = "http://192.168.0.105:5000/ai";
    private String path = "";
    String TAG = "AI";
    StringBuffer response;
    String responseText, responseTextToClient;


    @Override
    protected String doInBackground(String... strings) {
        try
        {
            IP myip = new IP();

            path = "http://" + myip.getIp() + ":5000/ai";

            url = new URL(path);
            Log.d(TAG, "Server Data:" + path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            //Log.d(TAG, "Response code:" + responseCode);
            if(responseCode ==HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();
                while((output = in.readLine())!=null)
                {
                    response.append(output);
                }
                in.close();
            }
            else
            {
                return "Unable to Connect to Server";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        responseText = response.toString();
        //Log.d(TAG, ""+ responseText);
        try
        {

            JSONObject jsonObject = new JSONObject(responseText);
            String AIResponseObject = jsonObject.getString("items");
            JSONArray jsonArray = new JSONArray(AIResponseObject);
            String AIResponse = jsonArray.getJSONObject(0).getString("response");
            //Debug use
            //Log.d(TAG, "**AIresponseObject:** " + AIResponseObject);
            //Log.d(TAG, "**AIresponse:**" + AIResponse);

            responseTextToClient = AIResponse;


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return responseTextToClient;
    }
}
