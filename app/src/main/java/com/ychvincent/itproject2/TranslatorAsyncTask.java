package com.ychvincent.itproject2;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ychvincent on 14/5/2018.
 */

public class TranslatorAsyncTask extends AsyncTask<String, String, String> {
    private String Translator_username = "4e23c870-feb0-4fe8-8697-047937a19bf9";
    private String Translator_password = "O6F8FjZ6GNw3";

    public String returnTranslated;

    public String modelType;

    @Override
    protected String doInBackground(String... strings) {
        String AIResponse = strings[0];
        Log.d("AIresponse", AIResponse);
        String selectedLanguage = strings[1];

        Log.d("SelectLanguage", "" + selectedLanguage);
        if (selectedLanguage == "Brazilian Portuguese") {
            modelType = "en-pt";
        } else if (selectedLanguage == "French") {
            modelType = "en-fr";
        } else if (selectedLanguage == "Japanese") {
            modelType = "en-ja";
        } else if (selectedLanguage == "Korean") {
            modelType = "en-ko";

        }
        if (selectedLanguage != "UK English") {
            LanguageTranslator languageTranslator = new LanguageTranslator(Translator_username, Translator_password);
            languageTranslator.setEndPoint("https://gateway.watsonplatform.net/language-translator/api");
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(AIResponse)
                    .modelId(modelType)
                    .build();
            Log.d("Testing", "Type, text = " + modelType +  " , " + AIResponse);

            TranslationResult result = languageTranslator.translate(translateOptions).execute();

            //list of ibm watson translator model type
            //TranslationModels models = languageTranslator.listModels().execute();
            //System.out.println(models);

            String getjson = result.toString();
            try {
                JSONObject jsonObject = new JSONObject(getjson);
                String AIResponseObject = jsonObject.getString("translations");
                JSONArray jsonArray = new JSONArray(AIResponseObject);
                returnTranslated = jsonArray.getJSONObject(0).getString("translation");
                System.out.println("getstring translation =  " + returnTranslated);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //get all supported language
            //IdentifiableLanguages languages = languageTranslator.listIdentifiableLanguages().execute();
            //System.out.println(languages);
        } else {
            returnTranslated = strings[0];
        }

        return returnTranslated;
    }
}