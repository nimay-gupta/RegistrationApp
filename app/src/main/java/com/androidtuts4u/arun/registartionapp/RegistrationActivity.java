package com.androidtuts4u.arun.registartionapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.androidtuts4u.arun.registartionapp.database.Entity;
import com.androidtuts4u.arun.registartionapp.database.MyAppDatabase;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    String name, desc, date;
    int parent;
    private EditText etName, etdesc;
    private Button btRegister, bDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        etName = (EditText) findViewById(R.id.et_name);
        etdesc = (EditText) findViewById(R.id.et_desc);
        bDate = (Button) findViewById(R.id.b_date);
        btRegister = (Button) findViewById(R.id.bt_registration);
        parent = getIntent().getIntExtra("PARENT_ID", 0);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                desc = etdesc.getText().toString();
                date = bDate.getText().toString();

                Entity to_add = new Entity();
                to_add.setName(name);
                to_add.setDescription(desc);
                to_add.setDate(date);
                to_add.setParent_id(parent);
                if(date.equals("")) {
                    date = MyAppDatabase.getAppDatabase(getApplicationContext()).dao().findById(parent).getDate();
                }
                MyAppDatabase.getAppDatabase(getApplicationContext()).dao().insertAll(to_add);

//                if (rowId != -1) {
//                    Toast.makeText(RegistrationActivity.this, "User regstered succesfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "Something Went Wrong! ", Toast.LENGTH_SHORT).show();
//
//                }


            }
        });

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                bDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.setCancelable(false);
            }
        });

    }
}


