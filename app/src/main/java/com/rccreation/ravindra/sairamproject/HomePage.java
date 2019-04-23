package com.rccreation.ravindra.sairamproject;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;
import java.util.regex.*;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    EditText ed;
    private static final int REQUEST_CODE = 1234;
    Button button;
    Button speak;
    private TextToSpeech textToSpeech;
    ImageView profile,previous,recent;
    private static final String TAG = PackageManager.class.getName();

    public  String pn,state,aadhar,pc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_page );
        ed = (EditText) findViewById( R.id.search );
        profile=findViewById(R.id.profile);
        previous=findViewById(R.id.previous);
//        recent=findViewById(R.id.recent);
        speak = (Button) findViewById( R.id.assist );
       pn=getIntent().getStringExtra("name");
        state=getIntent().getStringExtra("state");
        pc=getIntent().getStringExtra("pc");
        aadhar=getIntent().getStringExtra("number");
        String languageToLoad  = "te"; // your language code
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        pn=getIntent().getStringExtra("name");
previous.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HomePage.this,FirstActivity.class);
        startActivity(intent);
    }
});

profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HomePage.this,Profile.class);
        intent.putExtra("aadhar",aadhar);
        intent.putExtra("pin",pc);
        intent.putExtra("state",state);
        intent.putExtra("name",pn);
        startActivity(intent);
    }
});


        textToSpeech = new TextToSpeech(getApplicationContext(),  new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        int speechStatus = textToSpeech.speak("Welcome "+pn, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }

        PackageManager pm = getPackageManager();
        List <ResolveInfo> activities = pm.queryIntentActivities( new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH ), 0 );
        if (activities.size() == 0) {
            speak.setEnabled( false );
            ed.setText("Recognizer not present");
        }
        ed.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                speak.setEnabled( false );
            }
        } );

    }



    public void speakButtonClicked(View v) {
        startVoiceRecognitionActivity();
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        intent.putExtra( RecognizerIntent.EXTRA_PROMPT, "Voice searching..." );
        startActivityForResult( intent, REQUEST_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList <String> matches = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
            if (!matches.isEmpty()) {
                final String Query = matches.get( 0 );
                ed.setText( Query );
                speak.setEnabled( true );
                String data1 = ed.getText().toString();

                Log.i("TTS", "button clicked: " + data1);
                int speechStatus = textToSpeech.speak(data1, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }

                String train=String.valueOf(ed.getText().toString());
                String b2=".*live.*";
                String b1=".*route.*";
                String b3=".*details.*";
                String b4=".*code.*";
                String b5=".*PNR.*";
                String b7=".*book.*";
                String b8=".*between.*";
                String b9=".*fare.*";
                String b6="[0-9]+";
                String[] arrOfStr = train.split(" ");
                if (Pattern.matches( b1, train)) {
                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Routes.class );
                    intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b2, train)){
                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Liveweb.class );
                    intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b3, train)){
                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Display.class );
                    intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b6, train)){
                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Display.class );
                    intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b4, train)){
                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Code.class );
                    intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b7, train)){
//                    String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Booking.class );
                    intent.putExtra( "train", pn );
                    startActivity( intent );
                }
                else if(Pattern.matches( b5, train)){
                    //String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Pnrweb.class );
                    //intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b8, train)){
                    //String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Betst.class );
                    //intent.putExtra( "train", a );
                    startActivity( intent );
                }
                else if(Pattern.matches( b9, train)){
                    //String a = arrOfStr[arrOfStr.length - 1];
                    Intent intent = new Intent( getApplicationContext(), Fare.class );
                    //intent.putExtra( "train", a );
                    startActivity( intent );
                }




            }
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

}
