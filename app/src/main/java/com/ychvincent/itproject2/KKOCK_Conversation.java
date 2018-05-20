package com.ychvincent.itproject2;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.Analytics;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.logger.api.Logger;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class KKOCK_Conversation extends AppCompatActivity implements Serializable {
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public String AIResponse;
    public String TAG = "Conversation";
    public String stringToAI;
    public String inputmessage;
    public TranslationResult result;
    public String TranslatedString;
    public boolean finished;
    public String sendMessageTranslate;
    LanguageHolder languageHolder = new LanguageHolder();
    private RecyclerView recyclerView;
    private ArrayList messageArrayList;
    private Context context;
    private MessageAdapter mAdapter;
    private ImageButton btnSend;
    private ImageButton btnRecord;
    private EditText et_message;
    private SpeechToText speechService;
    private boolean listening = false;
    private StreamPlayer streamPlayer;
    private boolean permissionToRecordAccepted = false;
    private boolean initialRequest;
    private MicrophoneInputStream capture;
    private com.ibm.watson.developer_cloud.conversation.v1.model.Context mcontext = null;
    private MicrophoneHelper microphoneHelper;
    private String STT_username;
    private String STT_password;
    private String TTS_username;
    private String TTS_password;
    private String analytics_APIKEY;
    private String Translator_username;
    private String Translator_password;
    private Logger myLogger;
    private TextToSpeech textToSpeech;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menu.add (group ID , item_ID, order, item, name);
        menu.add(0, 1, 1, "Language Setting");
        menu.add(1, 2, 2, "Contact us");
        menu.add(1, 3, 3, "Exit");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(final MenuItem item) {


        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            // add more case to add options
            case 1:
                //"Mandarin Chinese" is not available
                final CharSequence[] items = {"Brazilian Portuguese", "French", "Japanese", "UK English"};

                AlertDialog.Builder builder = new AlertDialog.Builder(KKOCK_Conversation.this);
                builder.setTitle(R.string.setting1);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (items[which] == "Brazilian Portuguese") {
                            languageHolder.setSelectedLanguage("Brazilian Portuguese");
                            languageHolder.setLanguageModel("pt-BR_BroadbandModel");

                            //languageHolder.setVoice("Voice.PT_ISABELA");
                            Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();

                        } else if (items[which] == "French") {
                            languageHolder.setSelectedLanguage("French");
                            languageHolder.setLanguageModel("fr-FR_BroadbandModel");
                            //speck fr and translate to english

                            //languageHolder.setVoice("Voice.FR_RENEE");
                            Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();
                        } else if (items[which] == "Japanese") {
                            languageHolder.setSelectedLanguage("Japanese");
                            languageHolder.setLanguageModel("ja-JP_BroadbandModel");
                            //speck jp and translate to english
                            //languageHolder.setVoice("Voice.JA_EMI");
                            Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();

                        }
                        /*
                        else if(items[which] == "Korean")
                        {
                            languageHolder.setSelectedLanguage("Korean");
                            languageHolder.setLanguageModel("ko-KR_BroadbandModel");
                            //speck kr and translate to english
                            languageHolder.setLanguageIdentifier("ko-en");
                            //languageHolder.setVoice("");
                            Toast.makeText(getApplicationContext(),"Sorry, Korean Voice is not available now", Toast.LENGTH_SHORT).show();

                            //Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();
                        }
                        */
                        /*
                        else if(items[which] =="Mandarin Chinese")
                        {

                            languageHolder.setSelectedLanguage("Mandarin Chinese");
                            languageHolder.setLanguageModel("zh-CN_BroadbandModel");
                            Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();
                        }
                        */
                        else if (items[which] == "UK English") {
                            //TODO to be done
                            languageHolder.setSelectedLanguage("UK English");
                            languageHolder.setLanguageIdentifier("en-US_BroadbandModel");
                            //languageHolder.setVoice("en-US_LisaVoice");
                            Toast.makeText(getApplicationContext(), languageHolder.getSelectedLanguage() + " is selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();

                break;
            case 2:
                //Toast toast2=Toast.makeText(this, "2nd", Toast.LENGTH_SHORT);
                //toast2.show();
                break;
            case 3:
                //Toast.makeText(this, "3rd", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkock__conversation);

        // get context
        context = getApplicationContext();
        //default english model
        if (languageHolder.getSelectedLanguage() == null) {
            languageHolder.setSelectedLanguage("UK English");
            languageHolder.setLanguageModel("en-US_BroadbandModel");
        }
        //ibm watson service
        STT_username = context.getString(R.string.STT_username);
        STT_password = context.getString(R.string.STT_password);
        TTS_username = context.getString(R.string.TTS_username);
        TTS_password = context.getString(R.string.TTS_password);
        analytics_APIKEY = context.getString(R.string.mobileanalytics_apikey);
        Translator_username = context.getString(R.string.translator_username);
        Translator_password = context.getString(R.string.translator_password);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnSend = (ImageButton) findViewById(R.id.btn_send);
        btnRecord = (ImageButton) findViewById(R.id.btn_record);
        et_message = (EditText) findViewById(R.id.et_message);

        // recycler view
        messageArrayList = new ArrayList<>();
        mAdapter = new MessageAdapter(messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        //empty edit text
        et_message.setText("");
        this.initialRequest = true;

        //Bluemix Mobile Analytics
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_US_SOUTH);
        //Analytics is configured to record lifecycle events.
        Analytics.init(getApplication(), "kkock", analytics_APIKEY, false, Analytics.DeviceEvent.ALL);
        //Analytics.send();
        myLogger = Logger.getLogger("myLogger");
        // Send recorded usage analytics to the Mobile Analytics Service
        Analytics.send(new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                // Handle Analytics send success here.
            }

            @Override
            public void onFailure(Response response, Throwable throwable, JSONObject jsonObject) {
                // Handle Analytics send failure here.
            }
        });

        // Send logs to the Mobile Analytics Service
        Logger.send(new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                // Handle Logger send success here.
            }

            @Override
            public void onFailure(Response response, Throwable throwable, JSONObject jsonObject) {
                // Handle Logger send failure here.
            }
        });

        //get microphone helper
        microphoneHelper = new MicrophoneHelper(this);

        //watson text to speech service
        textToSpeech = new TextToSpeech();
        textToSpeech.setUsernameAndPassword(TTS_username, TTS_password);

        int permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

        //start of conversation
        conversationStart();



        // send button listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("Clicked", "Clicked");
                    sendMessage();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // record button listener
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordMessage();
                //Toast.makeText(getApplicationContext(), "Recording...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Speech-to-Text Record Audio permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }
                return;
            }

            case MicrophoneHelper.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission to record audio denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // if (!permissionToRecordAccepted ) finish();
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                MicrophoneHelper.REQUEST_PERMISSION);
    }

    //Record a message via Watson Speech to Text
    private void recordMessage()
    {
        //mic.setEnabled(false);
        speechService = new SpeechToText();

        speechService.setUsernameAndPassword(STT_username, STT_password);


        if (listening != true) {
            capture = microphoneHelper.getInputStream(true);
            new Thread(new Runnable() {

                @Override
                public void run()
                {
                    try
                    {

                        speechService.recognizeUsingWebSocket(capture, getRecognizeOptions(), new MicrophoneRecognizeDelegate());

                    }
                    catch (Exception e)
                    {
                        //Log.d("1 ok ", "8787 ok ");
                        e.printStackTrace();
                    }
                }
            }).start();
            listening = true;
            Toast.makeText(KKOCK_Conversation.this, "Listening...Click to Stop", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                microphoneHelper.closeInputStream();
                listening = false;
                Toast.makeText(KKOCK_Conversation.this, "Stopped Listening", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Log.d("Error", "Finally goes here");
                e.printStackTrace();
            }

        }
    }

    //Check Internet Connection
    private boolean checkInternetConnection()
    {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(this, " No Internet Connection available ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //Private Methods - Speech to Text
    private RecognizeOptions getRecognizeOptions() {


        Log.d("recognizeoptions", ""+languageHolder.getLanguageModel());
        return new RecognizeOptions.Builder()
                //.continuous(true)
                .contentType(ContentType.OPUS.toString())
                .model(languageHolder.getLanguageModel())
                .interimResults(true)
                .inactivityTimeout(3000)
                .speakerLabels(false)
                .build();
    }

    private void showMicText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_message.setText(text);
            }
        });
    }

    private void enableMicButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRecord.setEnabled(true);
            }
        });
    }

    private void showError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(KKOCK_Conversation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    public String conversationStart() {

        finished = false;

        try {
            //AIResponse = "hello, how can i help you?";
            AIResponse = new AI().execute().get().toString();
            Log.d(TAG, AIResponse);


            if (languageHolder.getSelectedLanguage() != "UK English") {
                //Log.d(TAG, "Enter IF");
                try {
                    //Log.d(TAG, "go asynctask");

                    String returnTranslatedString = new TranslatorAsyncTask().execute(AIResponse, languageHolder.getSelectedLanguage()).get();
                    MessageHolder message = new MessageHolder();
                    //System.out.println(TAG + ": " + returnTranslatedString);
                    message.setMessage(returnTranslatedString);
                    //message.setMessage(result.toString());
                    message.setId("2");
                    messageArrayList.add(message);
                    this.et_message.setText("");
                    mAdapter.notifyDataSetChanged();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else if (languageHolder.getSelectedLanguage() == "UK English") {
                MessageHolder message2 = new MessageHolder();
                message2.setMessage("You can change the language by the option menu on the top right corner");
                message2.setId("2");
                messageArrayList.add(message2);

                MessageHolder message = new MessageHolder();
                //System.out.println(TAG + ": " + AIResponse);
                message.setMessage(AIResponse);
                //message.setMessage(result.toString());
                message.setId("2");
                messageArrayList.add(message);

                this.et_message.setText("");
                mAdapter.notifyDataSetChanged();
            }
            //TODO here
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    streamPlayer = new StreamPlayer();
                    if (AIResponse != null && !AIResponse.isEmpty())
                    {
                        streamPlayer.playStream(textToSpeech.synthesize(AIResponse, Voice.EN_LISA).execute());
                        //get all watson voice list
                        //List<Voice> voices = textToSpeech.getVoices().execute();
                        //System.out.println(voices);
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Say something first", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            thread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return AIResponse;
    }



    public void sendMessage() throws ExecutionException, InterruptedException
    {
        if (languageHolder.getSelectedLanguage() == "UK English") {
            inputmessage = this.et_message.getText().toString().trim();
            //recycler view change
            MessageHolder messageHolder = new MessageHolder();
            messageHolder.setMessage(inputmessage);
            messageHolder.setId("1");
            messageArrayList.add(messageHolder);
            this.et_message.setText("");
            mAdapter.notifyDataSetChanged();
            stringToAI = new SendMessageToAI().execute(inputmessage, languageHolder.getSelectedLanguage()).get();
            sendMessageTranslate = new TranslatorAsyncTask().execute(stringToAI, languageHolder.getSelectedLanguage()).get();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    streamPlayer = new StreamPlayer();
                    if (sendMessageTranslate != null && !sendMessageTranslate.isEmpty())
                    {
                        if (languageHolder.getSelectedLanguage() == "UK English")
                        {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.EN_LISA).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Korean") {
                            //streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.EN_LISA).execute());
                            Toast.makeText(getApplicationContext(), "Sorry, Korean Voice is not available now", Toast.LENGTH_SHORT).show();
                        } else if (languageHolder.getSelectedLanguage() == "French") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.FR_RENEE).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Japanese") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.JA_EMI).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Brazilian Portuguese") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.PT_ISABELA).execute());
                        }
                    } else
                    {

                        //Toast.makeText(getApplicationContext(), "Say something first", Toast.LENGTH_SHORT).show();
                    }

                }

            });
            //Log.d("234234", "234234");
            thread.start();



            //new a bean to store message received
            MessageHolder outMessage = new MessageHolder();
            if (sendMessageTranslate != null) {
                outMessage.setMessage(sendMessageTranslate);
                //Log.d("outmessage", sendMessageTranslate);
                outMessage.setId("2");
                messageArrayList.add(outMessage);
                this.et_message.setText("");
                mAdapter.notifyDataSetChanged();
                if(sendMessageTranslate.equals("See you later, thanks for visiting" )|| sendMessageTranslate.equals("Have a nice day") || sendMessageTranslate.equals("Bye! Come back again soon."))
                {
                    //Log.d("rating!" , "rating...........pls");
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Please help us improve the system!");
                    builder.setMessage("Is the response useful?");

                    builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNeutralButton("Fine", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            finish();
                        }
                    });

                    builder.setNegativeButton("Disgree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
                //Log.d("this is ok","okokok");
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() > 0) {
                        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount());
                    } else {
                        Toast.makeText(getApplicationContext(), "runOnUiThread error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else
        {
            inputmessage = this.et_message.getText().toString();
            MessageHolder messageHolder = new MessageHolder();
            messageHolder.setMessage(inputmessage);
            messageHolder.setId("1");
            messageArrayList.add(messageHolder);
            this.et_message.setText("");
            mAdapter.notifyDataSetChanged();


            //asynctask to get response from restful server
            //stringToAI ="Do you open today?";
            stringToAI = new SendMessageToAI().execute(inputmessage, languageHolder.getSelectedLanguage()).get();
            sendMessageTranslate = new TranslatorAsyncTask().execute(stringToAI, languageHolder.getSelectedLanguage()).get();
            //Log.d("language holder", languageHolder.getSelectedLanguage());

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    streamPlayer = new StreamPlayer();
                    //Log.d("in side thread", languageHolder.getSelectedLanguage());

                    if (sendMessageTranslate != null && !sendMessageTranslate.isEmpty()) {
                        //Log.d("123123", "123123");
                        if (languageHolder.getSelectedLanguage() == "UK English") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.EN_LISA).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Korean") {
                            //streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.EN_LISA).execute());
                            Toast.makeText(getApplicationContext(), "Sorry, Korean Voice is not available now", Toast.LENGTH_SHORT).show();
                        } else if (languageHolder.getSelectedLanguage() == "French") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.FR_RENEE).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Japanese") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.JA_EMI).execute());
                        } else if (languageHolder.getSelectedLanguage() == "Brazilian Portuguese") {
                            streamPlayer.playStream(textToSpeech.synthesize(sendMessageTranslate, Voice.PT_ISABELA).execute());
                        }
                    } else
                    {

                        //Toast.makeText(getApplicationContext(), "Say something first", Toast.LENGTH_SHORT).show();
                    }

                }

            });

            thread.start();
            //new a bean to store message received
            MessageHolder outMessage = new MessageHolder();
            if (sendMessageTranslate != null) {
                outMessage.setMessage(sendMessageTranslate);
                //Log.d("outmessage", sendMessageTranslate);
                outMessage.setId("2");
                messageArrayList.add(outMessage);
                this.et_message.setText("");
                mAdapter.notifyDataSetChanged();
                //Log.d("this is ok","okokok");
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getItemCount() > 0) {
                        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount());
                    } else {
                        Toast.makeText(getApplicationContext(), "runOnUiThread error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Watson Speech to Text Methods.
    private class MicrophoneRecognizeDelegate implements RecognizeCallback {
        @Override
        public void onTranscription(SpeechResults speechResults) {
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                showMicText(text);
            }
        }

        @Override
        public void onConnected() {
        }

        @Override
        public void onError(Exception e) {
            showError(e);
            enableMicButton();
        }

        @Override
        public void onDisconnected() {
            enableMicButton();
        }

        @Override
        public void onInactivityTimeout(RuntimeException runtimeException) {
        }

        @Override
        public void onListening() {
        }

        @Override
        public void onTranscriptionComplete() {
        }
    }
}

