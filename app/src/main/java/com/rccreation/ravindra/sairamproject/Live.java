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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Live extends AppCompatActivity {
    ArrayList<String> trainname = new ArrayList<>();
    ArrayList<String> scharr = new ArrayList<>();
    ArrayList<String> schdep = new ArrayList<>();
    ArrayList<String> actarr = new ArrayList<>();
    ArrayList<String> actdep = new ArrayList<>();



    ProgressDialog pd;
    final String TAG = "check";
    private String train,date11;
    private TextToSpeech textToSpeech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_live );
        pd = new ProgressDialog( Live.this );
        pd.setMessage( "Loading . . ." );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-YYYY ");
        date11 = mdformat.format(calendar.getTime());

        train = getIntent().getStringExtra( "train" );
        //Calendar calendar=Calendar.getInstance();
        //SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        //String strDate = "Current Date : " + mdformat.format(calendar.getTime());

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

        String url = "https://api.railwayapi.com/v2/live/train/"+train+"/station/CHE/date/05-03-2019/apikey/vgma7t3h81/";
        pd.show();
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // get JSONObject from JSON file
                    JSONObject obj = new JSONObject(response);
                    // fetch JSONArray named users

                    JSONArray userArray = obj.getJSONArray( "status" );

                    // implement for loop for getting users list data
                    for (int i = 0; i < userArray.length(); i++) {
                        // create a JSONObject for fetching single user data
                        JSONObject userDetail = userArray.getJSONObject( i );
                        // fetch email and name and store it in arraylist
                        schdep.add(userDetail.getString( "schdep" ) );
                        scharr.add( userDetail.getString( "scharr" ) );
                        actdep.add(userDetail.getString( "actdep" ) );
                        actarr.add( userDetail.getString( "actarr" ) );
                       // create a object for getting contact data from JSONObject


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
        CustomAdapter1 customAdapter = new CustomAdapter1( trainname, scharr, schdep,actarr,actdep);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( pd!=null && pd.isShowing() )
        {
            pd.cancel();
        }
    }

}
