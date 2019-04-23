package com.rccreation.ravindra.sairamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
        TextView user,state1,pincode,aadhar;
        ImageView prevv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user=findViewById(R.id.name11);
        state1=findViewById(R.id.state11);
        prevv=findViewById(R.id.prevv);
        aadhar=findViewById(R.id.ano);
        pincode=findViewById(R.id.pincode);
        String user1=getIntent().getStringExtra("name");
        user.setText(user1);
        String aadhar1=getIntent().getStringExtra("aadhar");
        aadhar.setText(aadhar1);
        String pincode1=getIntent().getStringExtra("pin");
        pincode.setText(pincode1);
        String state=getIntent().getStringExtra("state");
        state1.setText(state);
        prevv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
}
