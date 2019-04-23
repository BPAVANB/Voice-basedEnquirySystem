package com.rccreation.ravindra.sairamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Pnrweb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnrweb);
        WebView mywebview = (WebView) findViewById(R.id.webView1);
//        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(mywebview.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.loadUrl("https://www.railyatri.in/pnr-status");
    }
}
