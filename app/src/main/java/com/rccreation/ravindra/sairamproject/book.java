package com.rccreation.ravindra.sairamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class book extends AppCompatActivity {
    TextView train,source,dest,doj,name;
    public String name1,source1,dest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        train=findViewById(R.id.trainname);
        source=findViewById(R.id.sna);
        dest=findViewById(R.id.dna);
        doj=findViewById(R.id.doj);
        name=findViewById(R.id.pn);
        name1=getIntent().getStringExtra("train");
        source1=String.valueOf(getIntent().getStringExtra("from"));
        dest1=String.valueOf(getIntent().getStringExtra("to"));
        name.setText(name1);
        source.setText(source1);
        dest.setText(dest1);
        train.setText("Konark Express");
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        doj.setText(formattedDate);

    }
}
