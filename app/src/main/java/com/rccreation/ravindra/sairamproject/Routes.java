package com.rccreation.ravindra.sairamproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Routes extends AppCompatActivity {
    ArrayList<String> personNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ProgressDialog pd;
    final String TAG = "check";
    private String train;
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_routes );
        pd = new ProgressDialog( Routes.this );
        pd.setMessage( "Loading . . ." );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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



        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //Intent intent=getIntent();
        train=getIntent().getStringExtra("train");


        String url = "https://api.railwayapi.com/v2/route/train/"+train+"/apikey/vgma7t3h81/";

        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // get JSONObject from JSON file
                    JSONObject obj = new JSONObject(response);
                    JSONObject jsonObject = obj.getJSONObject( "train" );
                    // String traintname = jsonObject.getString("name");
                    String data= jsonObject.getString( "name" ).toString().toLowerCase() ;

                    Log.i("TTS", "button clicked: " + data);
                    int speechStatus = textToSpeech.speak("Train Route for "+data, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }

                    // fetch JSONArray named users
                    JSONArray userArray = obj.getJSONArray( "route" );
                    pd.setMessage( "Loading . . ." );
                    pd.show();
                    // implement for loop for getting users list data
                    for (int i = 0; i < userArray.length(); i++) {
                        // create a JSONObject for fetching single user data
                        JSONObject userDetail = userArray.getJSONObject( i );
                        // fetch email and name and store it in arraylist
                        mobileNumbers.add(userDetail.getString( "schdep" ) );
                        emailIds.add( userDetail.getString( "scharr" ) );
                        // create a object for getting contact data from JSONObject
                        JSONObject contact = userDetail.getJSONObject( "station" );
                        // fetch mobile number and store it in arraylist
                        personNames.add(contact.getString( "name" ) );
                        code.add(  contact.getString( "code" ));
                        pd.hide();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) {
                    Log.d( TAG, error.toString() );
                    Toast.makeText( getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG ).show();
                }
            }
        }
        );


        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter( Routes.this, personNames, emailIds, mobileNumbers,code);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );
    }

}


