package com.yabut.karl.assignment4;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.MenuItem;

public class DatabseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Hospital.db";
    private static final int DATABSE_VERSION = 1;
    private String tables[];
    private String tableCreatorString[];

    public DatabseManager(Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    public void dbInitialize (String[] tables, String tableCreatorString[]){
        this.tables=tables;
        this.tableCreatorString=tableCreatorString;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < tables.length; i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
        for (int i = 0; i < tables.length; i++)
            db.execSQL(tableCreatorString[i]);
    }

    public void createDatabase(Context context){
        SQLiteDatabase mDatabase;
        mDatabase = context.openOrCreateDatabase(
                DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null);
    }
    //delete the database
    public void deleteDatabase(Context context)
    {
        context.deleteDatabase(DATABASE_NAME);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        for (int i=0;i<tables.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);

        // Create tables again
        onCreate(db);
    }
    /////////////////////////
    // Database operations
    /////////////////////////
    // Add a new record
    void addRecord(ContentValues values, String tableName, String fields[],String record[]) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);
        // Insert the row
        db.insert(tableName, null, values);
        db.close(); //close database connection
    }


    // Read all records
    public List getTable(String tableName) {
        List table = new ArrayList(); //to store all rows
        // Select all records
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList row=new ArrayList(); //to store one row
        //scroll over rows and store each row in an array list object
        if (cursor.moveToFirst())
        {
            do
            { // for each row
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                table.add(row); //add row to the list

            } while (cursor.moveToNext());
        }

        // return table as a list
        return table;
    }

    // Update a record
    public int updateRecord(ContentValues values, String tableName, String fields[],String record[]) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);

        // updating row with given id = record[0]
        return db.update(tableName, values, fields[0] + " = ?",
                new String[] { record[0] });
    }

    // Delete a record with a given id
    public void deleteRecord(String tableName, String idName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, idName + " = ?",
                new String[] { id });
        db.close();
    }
}