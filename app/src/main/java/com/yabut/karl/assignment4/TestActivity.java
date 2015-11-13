package com.yabut.karl.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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



    }
}