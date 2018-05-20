package com.ychvincent.itproject2;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by ychvincent on 10/5/2018.
 */

public class SendMessageToAI extends AsyncTask<String, String, String> {

    URL url;
    //private String path = "http://192.168.0.105:5000/ai/";
    private String path = "";
    public String TAG = "SendMessageToAI";
    public StringBuffer response;
    public String responseText, responseTextToClient;
    public String conversationContent;
    private String Translator_username = "4e23c870-feb0-4fe8-8697-047937a19bf9";
    private String Translator_password = "O6F8FjZ6GNw3";
    public String modelId;

    @Override
    protected String doInBackground(String... strings) {

        try {
            IP myip = new IP();

            conversationContent = strings[0];
            String language = strings[1];
            if(language == "Japanese")
            {
                modelId = "ja-en";
            }
            else if(language == "Brazilian Portuguese")
            {
                modelId = "pt-en";
            }
            else if(language == "French")
            {
                modelId = "fr-en";

            }
            else if( language == "Korean")
            {
                modelId = "ko-en";
            }

            if (language == "UK English") {
                path = "http://"+ myip.getIp() +":5000/ai/" +  conversationContent;
                //System.out.println(path);


                url = new URL(path.replace(" ", "%20"));
                Log.d("path after " + TAG, path);

                //Log.d(TAG, "Server Data:" + path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                //Log.d(TAG, "Response code:" + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response ok: Code= " + responseCode);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();
                    while ((output = in.readLine()) != null) {
                        response.append(output);
                        //Log.d(TAG, "doInBackground: " + response.toString());
                    }
                    responseText = response.toString();
                    in.close();
                } else if (responseCode == HttpURLConnection.HTTP_BAD_METHOD) {
                    return "HTTP Error 405(Bad Method)";
                } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    return "Server is under maintance";

                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    return "Server can not load requested service";

                }


                //Log.d(TAG, ""+ responseText);

                    JSONObject jsonObject = new JSONObject(responseText);
                    // get json first node
                    String AIResponseObject = jsonObject.getString("items");
                    JSONArray jsonArray = new JSONArray(AIResponseObject);
                    // get json key response text
                    String AIResponse = jsonArray.getJSONObject(0).getString("responseText");
                    //Debug use
                    Log.d(TAG, "**AIresponseObject:** " + AIResponseObject);
                    Log.d(TAG, "**AIresponse:**" + AIResponse);

                    responseTextToClient = AIResponse;

            }
            else
            {
                //Log.d("begin of translate" , "step 1...language = " + language );
                //Log.d("begin of translate" , "step 2...text = " + conversationContent );
                //Log.d("begin of translate" , "step 3...start of translator");
                LanguageTranslator languageTranslator = new LanguageTranslator(Translator_username, Translator_password);
                languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
                TranslateOptions translateOptions = new TranslateOptions.Builder()
                        .addText(conversationContent)
                        .modelId(modelId)
                        .build();
                TranslationResult result = languageTranslator.translate(translateOptions).execute();

                //finish translation but haven't return
                String finishTranslate = result.getTranslations().get(0).getTranslation().toString();
                //Log.d("finish of translate" , "step 4...translated = " + finishTranslate);
                path = path+finishTranslate;
                url = new URL(path.replace(" ", "%20"));
                //Log.d(TAG, "Server Data:" + path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                //Log.d(TAG, "Response code:" + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response ok: Code= " + responseCode);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();
                    while ((output = in.readLine()) != null) {
                        response.append(output);
                        //Log.d(TAG, "doInBackground: " + response.toString());
                    }
                    responseText = response.toString();
                    in.close();
                } else if (responseCode == HttpURLConnection.HTTP_BAD_METHOD) {
                    return "HTTP Error 405(Bad Method)";
                } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                    return "Server is under maintance";

                } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    return "Server can not load requested service";

                }


                //Log.d(TAG, ""+ responseText);

                JSONObject jsonObject = new JSONObject(responseText);
                // get json first node
                String AIResponseObject = jsonObject.getString("items");
                JSONArray jsonArray = new JSONArray(AIResponseObject);
                // get json key response text
                String AIResponse = jsonArray.getJSONObject(0).getString("responseText");
                //Debug use
                Log.d(TAG, "**AIresponseObject2:** " + AIResponseObject);
                Log.d(TAG, "**AIresponse2:**" + AIResponse);

                responseTextToClient = AIResponse;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(responseTextToClient);
        return responseTextToClient;
    }
}
