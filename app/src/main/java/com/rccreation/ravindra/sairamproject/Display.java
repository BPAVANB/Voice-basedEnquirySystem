
package com.rccreation.ravindra.sairamproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Display extends AppCompatActivity {


    final String TAG = "check";
    private String train;
    TextView dayst,codest,avilablest;
    TextView runst,from1,to1;
    TextView trainnameT;
    TextView trainnumberT;
    private TextToSpeech textToSpeech;

    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        pd = new ProgressDialog(Display.this);
        pd.setMessage("Loading . . .");
        dayst=(TextView)findViewById(R.id.days);
        runst=(TextView)findViewById(R.id.runs);
        trainnameT=(TextView)findViewById(R.id.trainname);
        trainnumberT=(TextView)findViewById(R.id.trainnumber);
        codest=findViewById( R.id.codest );
        avilablest=findViewById( R.id.availablest );
        from1=findViewById( R.id.from1 );
        to1=findViewById( R.id.to1 );

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




      train=getIntent().getStringExtra("train");
  /*      tex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( Display.this, Routes.class );
                intent.putExtra("train",train );
                startActivity(intent);

            }
        });
*/

                 String url = "https://api.railwayapi.com/v2/route/train/"+train+"/apikey/vgma7t3h81/";
                 pd.show();
                 StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {
                     @Override
                     public void onResponse(String response) {

                         // Log.d(TAG, response);
                         JSONObject jObject = null;
                         try {
                             jObject = new JSONObject( response );
                             JSONObject jsonObject = jObject.getJSONObject( "train" );
                             // String traintname = jsonObject.getString("name");
                             trainnameT.setText( jsonObject.getString( "name" ).toString() );

                             String data = trainnameT.getText().toString().toLowerCase();
                             Log.i("TTS", "button clicked: " + data);
                             int speechStatus = textToSpeech.speak("Train Details for "+data, TextToSpeech.QUEUE_FLUSH, null);

                             if (speechStatus == TextToSpeech.ERROR) {
                                 Log.e("TTS", "Error in converting Text to Speech!");
                             }

                             trainnumberT.setText(  jsonObject.getString( "number" ).toString() );
                             JSONArray jsonarray = new JSONArray( jsonObject.getString( "days" ) );
                             for (int i = 0; i < jsonarray.length(); i++) {

                                 JSONObject jsonobject = jsonarray.getJSONObject( i );
                                 String runs = jsonobject.getString( "runs" );
                                 String day = jsonobject.getString( "code" );


                                 dayst.append( "  " + day.substring( 0, 1 ).trim() + "  " );
                                 runst.append( "  " + runs.trim() + "  " );
                                 pd.hide();


                             }
                             JSONArray jsonarray1 = new JSONArray( jsonObject.getString( "classes" ) );
                             for (int i = 0; i < jsonarray1.length(); i++) {

                                 JSONObject jsonobject1 = jsonarray1.getJSONObject( i );
                                 String codes = jsonobject1.getString( "code" );
                                 String availables = jsonobject1.getString( "available" );


                                 codest.append( "  " + codes.substring( 0, 2 ).trim() + "  " );
                                 avilablest.append( "  " + availables.trim() + "  " );
                                 pd.hide();


                             }
                             JSONArray jsonarray2= jObject.getJSONArray("route"  );
                             JSONObject userDetail = jsonarray2.getJSONObject( 0);
                             JSONObject userDetail1 = jsonarray2.getJSONObject( jsonarray2.length()-1 );
                             JSONObject contact = userDetail.getJSONObject( "station" );
                             from1.append(contact.getString( "name" ) );
                             JSONObject contact1 = userDetail1.getJSONObject( "station" );
                             to1.append(contact1.getString( "name" ) );

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

                 MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );
             }
         }