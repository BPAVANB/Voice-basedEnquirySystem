package com.rccreation.ravindra.sairamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class rcregister extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    EditText rpassword,remail,rid,rname;
    TextView alredy;
    String registerurl,dpvalue,dpvalue2;
    ArrayAdapter<String> adapter,adapter2;
    SessionHandler sessionHandler;
    User user;
    Button signuopbutton;
    Spinner dropdown,dropdown2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcregister);

        dropdown = findViewById(R.id.spinner1);
         dropdown2 = findViewById(R.id.spinner2);

        String[] items = new String[]{"CSE","ECE","Mechanical","IT","Civil"};
        String[] items2 = new String[]{" A "," B "," C "};

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
         adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

        dropdown.setAdapter(adapter);
        dropdown2.setAdapter(adapter2);

        dropdown.setOnItemSelectedListener(this);
        dropdown2.setOnItemSelectedListener(this);


alredy=(TextView)findViewById(R.id.already);
        rpassword=(EditText)findViewById(R.id.registerpassword);
        rname=(EditText)findViewById(R.id.registername);
        remail=(EditText)findViewById(R.id.registeremail);
        rid=(EditText)findViewById(R.id.registerid);
signuopbutton=(Button)findViewById(R.id.signup);
sessionHandler=new SessionHandler(this);

registerurl= "http://firststeppschool.com/tempuserregsister.php";

user=new User();



user=sessionHandler.getUserDetails();

        if (sessionHandler.isLoggedIn())
        {

            if(user.getRole().equals("student"))
            startActivity(new Intent(rcregister.this,FirstActivity.class));

            else if(user.getRole().equals("faculty"))
                startActivity(new Intent(rcregister.this,dataaadhra.class));

        }

signuopbutton.setOnClickListener(this);
      alredy.setOnClickListener(this);




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        }









    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup:

                getUpload();

                break;

            case R.id.already:

                startActivity(new Intent(rcregister.this,FirstActivity.class));

                break;


        }


    }

    private void getUpload() {


        Toast.makeText(this, dropdown.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, dropdown2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerurl,
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

                                Toast.makeText(rcregister.this, "Registered", Toast.LENGTH_SHORT).show();

                                Intent ii=new Intent(rcregister.this,FirstActivity.class);
                                ii.putExtra("idprevious",rid.getText().toString());
                                startActivity(ii);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rcregister.this, "response error" + error, Toast.LENGTH_SHORT).show();
                Log.e("Response ", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id",rid.getText().toString() );
                params.put("name",rname.getText().toString());
                params.put("password",rpassword.getText().toString() );
                params.put("email",remail.getText().toString() );
                params.put("branch",dropdown.getSelectedItem().toString());
                params.put("section",dropdown2.getSelectedItem().toString());


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(rcregister.this);
        requestQueue.add(stringRequest);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        final AlertDialog.Builder ab=new AlertDialog.Builder(rcregister.this);
        ab.setIcon(R.drawable.ic_arrow_back_black_24dp);
        ab.setTitle("would you like to exit");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });

        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ab.setCancelable(true);


            }
        });





        ab.create();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
