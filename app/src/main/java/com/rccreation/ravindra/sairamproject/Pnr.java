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

import org.json.JSONException;
import org.json.JSONObject;

public class Pnr extends AppCompatActivity {

    final String TAG = "check";
    private String train;

    TextView pnrno, tno, tname, bd, froms, tos, resu, bdu, classs;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pnr );
        pd = new ProgressDialog( Pnr.this );
        pd.setMessage( "Loading . . ." );

        pnrno = (TextView) findViewById( R.id.pnr );
        tno = (TextView) findViewById( R.id.tno );
        tname = (TextView) findViewById( R.id.tname );
        bd = (TextView) findViewById( R.id.bd );
        froms = findViewById( R.id.from );
        tos = findViewById( R.id.to );
        resu = findViewById( R.id.res );
        bdu = findViewById( R.id.bdu );
        classs = findViewById( R.id.classs );



        train = getIntent().getStringExtra( "train" );


        String url = "https://api.railwayapi.com/v2/pnr-status/pnr/"+train+"/apikey/vgma7t3h81/";
        pd.show();
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {

                // Log.d(TAG, response);
                try {
                    JSONObject jObject = new JSONObject( response );
                    pnrno.setText( jObject.getString( "pnr" ).toString() );
                    bd.setText( jObject.getString( "doj" ).toString() );
                    JSONObject jsonObject = jObject.getJSONObject( "from_station" );
                    froms.setText( jsonObject.getString( "name" ).toString() );

                    JSONObject jsonObject1 = jObject.getJSONObject( "to_station" );
                    tos.setText( jsonObject1.getString( "name" ).toString() );

                    JSONObject jsonObject2 = jObject.getJSONObject( "boarding_point" );
                    bdu.setText( jsonObject2.getString( "name" ).toString() );

                    JSONObject jsonObject3 = jObject.getJSONObject( "reservation_upto" );
                    resu.setText( jsonObject3.getString( "name" ).toString() );
                    JSONObject jsonObject4 = jObject.getJSONObject( "train" );
                    tname.setText(jsonObject4.getString( "name" ).toString() );
                    tno.setText(jsonObject4.getString( "number" ).toString() );

                    JSONObject jsonObject5 = jObject.getJSONObject( "journey_class" );
                    classs.setText(jsonObject5.getString( "code" ).toString() );

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


