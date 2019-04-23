package com.rccreation.ravindra.sairamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class BetStation extends AppCompatActivity {
    ProgressDialog pd;
    final String TAG = "check";
    private String train;

    ArrayList<String> trainname = new ArrayList<>();

    ArrayList<String> trainnum = new ArrayList<>();

    ArrayList<String> time = new ArrayList<>();

    ArrayList<String> arr = new ArrayList<>();

    ArrayList<String> dep = new ArrayList<>();

    ArrayList<String> from = new ArrayList<>();

    ArrayList<String> to = new ArrayList<>();

    ArrayList<String> day = new ArrayList<>();

    ArrayList<String> runs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Intent intent = getIntent();
        setContentView( R.layout.activity_bet_station );
        pd = new ProgressDialog( BetStation.this );
        pd.setMessage( "Loading . . ." );
        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.recyclerView2 );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( linearLayoutManager );

        train = intent.getStringExtra( "train" );
        String train1, train11;
        train11 = intent.getStringExtra( "train" );
        train1= intent.getStringExtra( "train" );

        String url = "https://api.railwayapi.com/v2/between/source/" + train1 + "/dest/" + train11 + "/date/25-09-2018/apikey/vgma7t3h81/";
        pd.show();
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject( response );
                    JSONArray userArray = obj.getJSONArray( "trains" );
                    for (int i = 0; i < userArray.length(); i++) {
                        // create a JSONObject for fetching single user data
                        JSONObject userDetail = userArray.getJSONObject( i );
                        // fetch email and name and store it in arraylist
                        trainname.add( userDetail.getString( "name" ) );
                        trainnum.add( userDetail.getString( "number" ) );
                        time.add( userDetail.getString( "travel_time" ) );
                        arr.add( userDetail.getString( "dest_arrival_time" ) );
                        dep.add( userDetail.getString( "src_departure_time" ) );
                        JSONObject contact = userDetail.getJSONObject( "from_station" );
                        from.add( contact.getString( "name" ) );
                        JSONObject contact1 = userDetail.getJSONObject( "to_station" );
                        dep.add( contact1.getString( "name" ) );
                        JSONArray jsonarray1 = new JSONArray( userDetail.getString( "days" ) );
                       /* for (int k = 0; k < jsonarray1.length(); k++) {

                            JSONObject jsonobject = jsonarray1.getJSONObject( k );
                            String ru = jsonobject.getString( "runs" );
                            String days = jsonobject.getString( "code" );



                            day.append( "  " + days.substring( 0,1 ).trim() + "  " );
                            runs.append( "  " + ru.trim() + "  " );

                        }*/
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
        } );


        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter2 customAdapter = new CustomAdapter2( BetStation.this, trainname,trainnum,time,arr,dep,from,to,day,runs );
        recyclerView.setAdapter( customAdapter ); // set the Adapter to RecyclerView
        MySingleton.getInstance( getApplicationContext() ).addToRequestQueue( stringRequest );
    }
}