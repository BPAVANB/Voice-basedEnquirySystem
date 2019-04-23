package com.rccreation.ravindra.sairamproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class dataaadhra extends AppCompatActivity {

    private List<Student> cartparents;

    ListView list;

    ArrayAdapter<String> iii,iii2;
    TableLayout tbl;
    SpotsDialog spotsDialog;
    int datetoday,month,year,hour,min,sec;
    String iio = "http://firststeppschool.com/tempaadhardatabase.php";


    ArrayList<HashMap<String, String>> matchFixtureList,matchFixtureList2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);

        GregorianCalendar datee = new GregorianCalendar();
        spotsDialog=new SpotsDialog(this);
        spotsDialog.setIcon(R.drawable.qrcode);


        datetoday = datee.get(Calendar.DAY_OF_MONTH);
        month = datee.get(Calendar.MONTH);
        year = datee.get(Calendar.YEAR);

        sec = datee.get(Calendar.SECOND);
        min = datee.get(Calendar.MINUTE);
        hour = datee.get(Calendar.HOUR);

       tbl = (TableLayout) findViewById(R.id.tblLayout);
        matchFixtureList = new ArrayList<HashMap<String, String>>();

        matchFixtureList2 = new ArrayList<HashMap<String, String>>();



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        String[] days = new String[30];
        days[0]=sdf.format(date);

        for(int i = 1; i< 30; i++){
            cal.add(Calendar.DAY_OF_MONTH,-1);
            date=cal.getTime();
            days[i]=sdf.format(date);
        }

        for(int i = (days.length-1); i >= 0; i--){




        }


        Toast.makeText(this, ""+sdf.format(cal.getTime()), Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, iio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(CartActivity.this, "response" + response, Toast.LENGTH_SHORT).show();
                        try {
spotsDialog.show();

                            JSONArray jsArr = new JSONArray(response);




                            //converting the string to json array object

                            Log.e("response", response+"\nlength:"+jsArr.length());


                            for (int i = 0; i < jsArr.length(); i++) {

                                JSONObject c = jsArr.getJSONObject(i);
                                String id = c.getString("name");

                                String status = c.getString("postal");

                                String day = c.getString("state");

                                String period = c.getString("number");
//                                Log.e("response", "id:" + id + " status:" + status + " Date:" + day + " period:" + period);
                                HashMap<String, String> matchFixture = new HashMap<String, String>();

                                matchFixture.put("name", id);
                                matchFixture.put("postal", status);
                                matchFixture.put("state", day);
                                matchFixture.put("number", period);
                                matchFixtureList.add(matchFixture);
                            }

                            Log.e("matfixlist size", matchFixtureList.size() + "");


                            View oline = new View(getApplicationContext());
                            oline.setBackgroundColor(getResources().getColor(R.color.backgroundcolor));
                            oline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,1));
                            tbl.addView(oline);
                            for (int i = 0; i < matchFixtureList.size(); i++) {


                                TableRow tblRow = new TableRow(getApplicationContext());
                                TextView id = new TextView((getApplicationContext()));
                                TextView status = new TextView((getApplicationContext()));
                                TextView day = new TextView((getApplicationContext()));
                                TextView period = new TextView((getApplicationContext()));
                                id.setGravity(Gravity.CENTER_HORIZONTAL);
                                status.setGravity(Gravity.CENTER_HORIZONTAL);
                                day.setGravity(Gravity.CENTER_HORIZONTAL);
                                period.setGravity(Gravity.CENTER_HORIZONTAL);
                                View v = new View(getApplicationContext());

                                v.setBackgroundColor(getResources().getColor(R.color.backgroundcolor));
                                v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));

                                View v2 = new View(getApplicationContext());
                                v2.setBackgroundColor(getResources().getColor(R.color.backgroundcolor));
                                v2.setLayoutParams(new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT));

//                                Log.e("response", "id:" + id + " status:" + status + " Date:" + day + " period:" + period);
                                HashMap<String, String> matF = new HashMap<>();
                                matF = matchFixtureList.get(i);
                                id.setText(matF.get("name"));
                                id.setTextSize(12);
                                status.setText(matF.get("postal"));
                                status.setTextSize(12);
                                day.setText(matF.get("state"));
                                day.setTextSize(12);
                                period.setText(matF.get("number"));
                                period.setTextSize(12);
//                                tblRow.addView(v);

                                tblRow.addView(id);
                                tblRow.addView(status);
                                tblRow.addView(day);
                                tblRow.addView(period);
                                tbl.addView(tblRow);
                                tbl.addView(v2);
                                tbl.addView(v);

                                spotsDialog.dismiss();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();


                            spotsDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(dataaadhra.this, "response error" + error, Toast.LENGTH_SHORT).show();
                Log.e("Response ", error.toString());

                spotsDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(dataaadhra.this);
        requestQueue.add(stringRequest);







    }





}
