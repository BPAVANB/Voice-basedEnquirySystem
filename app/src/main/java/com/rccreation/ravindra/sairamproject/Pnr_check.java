package com.rccreation.ravindra.sairamproject;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Pnr_check extends AppCompatActivity {
    EditText editText;
    Button button;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pnr_check );
        editText = findViewById( R.id.value );
        button = findViewById( R.id.check );

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.UK);

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

            button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String train = String.valueOf( editText.getText().toString() );
                    Intent intent = new Intent( getApplicationContext(), Pnr.class );
                    intent.putExtra( "train", train );
                    startActivity( intent );

                    Log.i("TTS", "button clicked: " + train);
                    int speechStatus = textToSpeech.speak(train, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                }

            } );
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

