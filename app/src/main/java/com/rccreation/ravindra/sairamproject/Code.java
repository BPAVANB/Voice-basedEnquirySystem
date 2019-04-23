package com.rccreation.ravindra.sairamproject;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class Code extends AppCompatActivity {
   TextView stname,stcode;
    ProgressDialog pd;
    final String TAG = "check";
    String train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_code );
        stcode=findViewById( R.id.stcode );
        stname=findViewById( R.id.stname );

        pd = new ProgressDialog( Code.this );
        pd.setMessage( "Loading . . ." );

        train = getIntent().getStringExtra( "train" );
        String url = "https://api.railwayapi.com/v2/code-to-name/code/"+train+"/apikey/vgma7t3h81/";
        pd.show();
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject obj = new JSONObject(response);
                    JSONArray userArray = obj.getJSONArray( "stations" );

                    // implement for loop for getting users list data
                    for (int i = 0; i < userArray.length(); i++) {
                        // create a JSONObject for fetching single user data
                        JSONObject userDetail = userArray.getJSONObject( i);
                        // fetch email and name and store it in arraylist
                        stname.setText(userDetail.getString( "name" ).toString() );
                        stcode.setText( userDetail.getString( "code" ) .toString());
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

        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );
    }
}
