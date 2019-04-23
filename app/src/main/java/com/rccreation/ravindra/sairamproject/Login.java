package com.rccreation.ravindra.sairamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Login extends AppCompatActivity  implements  View.OnClickListener{


    EditText rpassword,rid;
    SessionHandler sessionHandler;
    User user;
    TextView alredy,newuserhere;
    RadioButton st,fa;
    Button signuopbutton;

    String loginurl="http://firststeppschool.com/templogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        alredy=(TextView)findViewById(R.id.newuser);
        rpassword=(EditText)findViewById(R.id.loginpassword);
        rid=(EditText)findViewById(R.id.loginid);
        signuopbutton=(Button)findViewById(R.id.login);

sessionHandler=new SessionHandler(this);
        user=new User();
        user=sessionHandler.getUserDetails();
        signuopbutton.setOnClickListener(this);
        alredy.setOnClickListener(this);

        newuserhere=(TextView)findViewById(R.id.newuser);


        newuserhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,rcregister.class));


            }
        });




    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login:

              getverify();

              break;

            case R.id.already:

                startActivity(new Intent(Login.this,rcregister.class));

                break;


        }


    }

    private void getverify() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response ", response);


                        Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            String errorMsg = jObj.getString("message");
                            if (error) {


                                Toast.makeText(getApplicationContext(),
                                        "error:  " + errorMsg, Toast.LENGTH_LONG).show();



                            } else {

                                String role = jObj.getString("role");
                                Toast.makeText(Login.this, " login", Toast.LENGTH_SHORT).show();
                                sessionHandler.loginUser(rid.getText().toString(),rpassword.getText().toString(),role);

                                if (role.equals("student"))
                                startActivity(new Intent(Login.this,FirstActivity.class));

                                else if (role.equals("faculty"))
                                    startActivity(new Intent(Login.this,dataaadhra.class));


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "response error" + error, Toast.LENGTH_SHORT).show();
                Log.e("Response ", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("password",rpassword.getText().toString());
                params.put("id", rid.getText().toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);





    }


    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
