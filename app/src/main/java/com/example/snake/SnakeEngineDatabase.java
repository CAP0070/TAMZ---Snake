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
//        viewAll();
    }

    public static Cursor getEntries() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            addData();
            getEntries();
        }

        return res;
    }

    public static void UpdateData(String coordinates, String time, String score,
                                  String direction, String food, String sound, String fps) {
        boolean isUpdate = myDb.updateData("1", coordinates, time, score, direction, food, sound, fps);
    }

    public void viewAll() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            addData();
            return;
        }
    }

    public static void addData(){
        myDb.insertData("12x6y", "0", "0", "1", "4x6y", "1", "10");
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
