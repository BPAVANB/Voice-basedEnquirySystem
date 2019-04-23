package com.rccreation.ravindra.sairamproject;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Booking extends AppCompatActivity implements View.OnClickListener {

    Button select, book;
    EditText from, to, date, train;
    private int mYear, mMonth, mDay;
    public String name1,from11,to11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        select = findViewById(R.id.select);
        book = findViewById(R.id.book);
        from = findViewById(R.id.from33);
        to = findViewById(R.id.to33);
        train = findViewById(R.id.tno);
        date = findViewById(R.id.date);
        select.setOnClickListener(this);

//        String from1 = from.getText().toString();


            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name1 = getIntent().getStringExtra("train");
                    from11 = String.valueOf(from.getText().toString());
                    to11 = String.valueOf(to.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), book.class);
                    intent.putExtra("train", name1);
                    intent.putExtra("from", from11);
                    intent.putExtra("to", to11);
                    startActivity(intent);
                }
            });
//        if (from11.matches("")) {
//            Toast.makeText(getApplicationContext(), "Please give source name ", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }
    @Override
    public void onClick(View v) {

        if (v == select) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
