package com.rccreation.ravindra.sairamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Qrcoderesult extends AppCompatActivity {

    String s,s1,s2,s3,s4;
    Button chcl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcoderesult);

 s=getIntent().getStringExtra("number");
         s2=getIntent().getStringExtra("name");
         s3=getIntent().getStringExtra("pc");
         s4=getIntent().getStringExtra("state");

        TextView yy =findViewById(R.id.longtexts);

          yy.setText(s);

          chcl=findViewById(R.id.chcl);

          chcl.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  startActivity(new Intent(Qrcoderesult.this,dataaadhra.class));

              }
          });

        Toast.makeText(this, "name__"+s2, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "pc__"+s3, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "state__"+s4, Toast.LENGTH_SHORT).show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://firststeppschool.com/tempaadhar.php" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response ", response);


                        Toast.makeText(Qrcoderesult.this, ""+response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String errorMsg = jObj.getString("message");
                            if (error) {


                                Toast.makeText(getApplicationContext(),
                                        "error: " + errorMsg, Toast.LENGTH_LONG).show();



                            } else {


                                Toast.makeText(Qrcoderesult.this, " updated ", Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Qrcoderesult.this, "response error" + error, Toast.LENGTH_SHORT).show();
                Log.e("Response ", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("name",s2);
                params.put("pc",s3);
                params.put("state",s4);
                params.put("number",s);



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Qrcoderesult.this);
        requestQueue.add(stringRequest);

    }
}
