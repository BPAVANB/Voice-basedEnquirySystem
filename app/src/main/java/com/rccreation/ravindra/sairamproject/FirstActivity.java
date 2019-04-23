package com.rccreation.ravindra.sairamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class FirstActivity extends AppCompatActivity {


TextView clicktoscan;
    TextView names,classs,rtime,rday,rid,displayid;
    int day, month, year;
    int second, minute, hour;
    Button logout1;
    int period;

SessionHandler sessionHandler;
String iio="http://firststeppschool.com/tempuserinfo.php",AM_PM;
User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

//        user=new User();
//        names=(TextView)findViewById(R.id.updatenamehere) ;
        sessionHandler=new SessionHandler(this);
        logout1=(Button)findViewById(R.id.logout1);
        user=sessionHandler.getUserDetails();

        clicktoscan=(TextView)findViewById(R.id.clicktoscan);


displayid=findViewById(R.id.displayid);


        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

//        displayid.setText(user.getUserid());







userrequest();
clicktoscan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent(FirstActivity.this,ScanActivity.class));

    }
});



logout1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        sessionHandler.logoutUser();
        startActivity(new Intent(FirstActivity.this,HomePage.class));

    }
});



    }


    private void userrequest()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, iio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response ", response);




                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String errorMsg = jObj.getString("message");
                            if (error) {


                                Toast.makeText(getApplicationContext(),
                                        "error:  " + errorMsg, Toast.LENGTH_LONG).show();




                            } else {


                                Toast.makeText(FirstActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                String updatename = jObj.getString("name");
                                names.setText(updatename);

                                user.setSname(updatename);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FirstActivity.this, "response error" + error, Toast.LENGTH_SHORT).show();
                Log.e("Response ", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                params.put("id", user.getUserid());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FirstActivity.this);
        requestQueue.add(stringRequest);





    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
        builder.setTitle("Alert by AITAM Attendance Application");
        builder.setMessage("are you sure to exit?")
                .setCancelable(false)
                .setPositiveButton("exit now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }
}
