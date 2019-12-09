package com.example.snake;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class SnakeEngineDatabase {
    private static DatabaseHelper myDb;
    private static Context context;

    public SnakeEngineDatabase(Context cntx){
        context = cntx;
//        context.deleteDatabase("Snake.db");

        myDb = new DatabaseHelper(context);
//        addData();
        viewAll();
    }

    public static String getEntries() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            return "12x6y1s0t0";
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            return res.getString(1);
        }
        return res.getString(1);
    }

    public static void UpdateData(String coordinates) {
        boolean isUpdate = myDb.updateData("1", coordinates);
    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            addData();
            return;
        }
    }

    public void addData(){
        boolean isInserted = myDb.insertData("12x6y1s0t0");
    }

    public void deleteData(){
        Integer deletedRows = myDb.deleteData("1");
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
