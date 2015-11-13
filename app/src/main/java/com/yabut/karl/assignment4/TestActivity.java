package com.yabut.karl.assignment4;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    private static final String tables[]={"tbl_patient","tbl_test", "tbl_nurse", "tbl_doctor"};
    //
    private static final String tableCreatorString[] ={
            "CREATE TABLE tbl_patient (patientId INTEGER PRIMARY KEY AUTOINCREMENT , firstname TEXT, lastname TEXT, department TEXT, room INTEGER, doctorId INTEGER, FOREIGN KEY(doctorId) REFERENCES tbl_doctor(doctorId));",
            "CREATE TABLE tbl_test (testId INTEGER PRIMARY KEY AUTOINCREMENT , BPL TEXT, BPH TEXT, temperature DOUBLE, patientId INTEGER, FOREIGN KEY (patientId) REFERENCES tbl_patient(patientId));",
            "CREATE TABLE tbl_nurse (nurseId INTEGER PRIMARY KEY AUTOINCREMENT , firstname TEXT, lastname TEXT, department TEXT);",
            "CREATE TABLE tbl_doctor (doctorId INTEGER PRIMARY KEY AUTOINCREMENT , firstname TEXT, lastname TEXT, department TEXT);",

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final DatabseManager db = new DatabseManager(this);

        db.dbInitialize(tables, tableCreatorString);
        final String fields[] = {"firstname", "lastname"};
        final String records[] = new String[2];

        final Button loginButton = (Button) findViewById(R.id.buttonLogin);
        final EditText doctorLogin = (EditText) findViewById(R.id.doctorLogin);
        final EditText nurseLogin = (EditText) findViewById(R.id.nurseLogin);

        //saves name on database either doctor or nurse
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets whichever is typed on edit text
                records[1] = doctorLogin.getText().toString();
                records[1] = nurseLogin.getText().toString();
                //this goes into the database, puts whatever is sent through edit text
                ContentValues values = new ContentValues();
                for (int i=1;i<records.length;i++)
                    values.put(fields[i],records[i]);
                //add the row to the database / whichever table
                db.addRecord(values, "tbl_doctor", fields,records);
                db.addRecord(values, "tbl_nurse", fields,records);


            }
        });

        final Button display = (Button) findViewById(R.id.buttonDisplay);
        final TextView displayresult = (TextView) findViewById(R.id.TextViewDetails);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reading all records
                List table = db.getTable("tbl_doctor");
                //Intent displayIntent = new Intent(TestActivity.this, DisplayActivity.class);
                //startActivity(displayIntent);
                for (Object o : table) {
                    ArrayList row = (ArrayList)o;
                    // Writing table to log
                    String output="";
                    for (int i=0;i<row.size();i++)
                    {
                        output+= row.get(i).toString() + " ";
                        output+="\n";
                    }
                    displayresult.setText(output);

                }

            }
        });

    }
}