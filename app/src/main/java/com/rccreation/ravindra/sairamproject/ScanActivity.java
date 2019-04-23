package com.rccreation.ravindra.sairamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;
SessionHandler sessionHandler;
int hour,minute,second,day,month,year,period;
String uu9;


User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        sessionHandler=new SessionHandler(this);
        user=new User();
        user=sessionHandler.getUserDetails();

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);




        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);





        Toast.makeText(this, ""+period, Toast.LENGTH_SHORT).show();





    }

    @Override
    public void onScanned(Barcode barcode) {


        barcodeReader.playBeep();
        uu9=barcode.displayValue;



        getdataffrominput();

        // ticket details activity by passing barcode

         int start=uu9.indexOf("uid");

         Intent intent = new Intent(ScanActivity.this, HomePage.class);
           intent.putExtra("number", uu9.substring(68,80).toString());
           intent.putExtra("name",uu9.substring(88,104).toString());
          intent.putExtra("pc",uu9.substring(284,290).toString());
          intent.putExtra("state",uu9.substring(245,255).toString());

         startActivity(intent);


    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }



    private void getdataffrominput()
    {
        int start=uu9.indexOf("uid");
        int end=uu9.length();
        end=end-3;
        StringBuffer sb=new StringBuffer(100);

        for(int i=start;i<=end;i++)
        {
            sb.append(uu9.charAt(i));
        }


    }



}
