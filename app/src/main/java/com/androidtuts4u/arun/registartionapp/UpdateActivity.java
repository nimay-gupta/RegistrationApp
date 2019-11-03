package com.androidtuts4u.arun.registartionapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androidtuts4u.arun.registartionapp.database.Entity;
import com.androidtuts4u.arun.registartionapp.database.MyAppDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    EditText upName, upDescription;
    Button btUpdate, bDate;
    int parent;
    List<Entity> nodeList;
    String name, desc, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        upName = (EditText) findViewById(R.id.et_up_name);
        bDate = (Button) findViewById(R.id.b_up_date);
        upDescription = (EditText) findViewById(R.id.et_up_description);
        btUpdate = (Button) findViewById(R.id.bt_update);
        parent = getIntent().getIntExtra("PARENT_ID", 0);

        final int rowId = getIntent().getIntExtra("USERID", -1);

        nodeList = new ArrayList<Entity>();
        nodeList.clear();

        final Entity to_update = MyAppDatabase.getAppDatabase(getApplicationContext()).dao().findById(rowId);

        MyAppDatabase.getAppDatabase(getApplicationContext()).dao().delete(to_update);

        upName.setText(to_update.getName());
        bDate.setText(to_update.getDate());
        upDescription.setText(to_update.getDescription());

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this,
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

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = upName.getText().toString();
                date = bDate.getText().toString();
                desc = upDescription.getText().toString();

                to_update.setDate(date);
                to_update.setDescription(desc);
                to_update.setName(name);
                to_update.setParent_id(parent);

                try {
                    MyAppDatabase.getAppDatabase(getApplicationContext()).dao().insertAll(to_update);

                    Toast.makeText(UpdateActivity.this, "User Details Upated succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                    Toast.makeText(UpdateActivity.this, "User Updation Failed", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

}
