package com.example.guruprasadbr.sms_sender;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Guruprasad BR on 6/15/2018.
 */
class db_oper  {
    public static SQLiteDatabase db=null;
   // Activity activity;
    public db_oper(SQLiteDatabase db){
        this.db=db;
    }
    public db_oper(Context c){
      //  this.activity=activity;
        try {
            db = c.openOrCreateDatabase("my_sms_api", c.MODE_PRIVATE, null);
            db.execSQL("create table if not exists sms_user(" +
                    "uid varchar(20) primary key," +
                    "number varchar(20)," +
                    "key varchar(100)," +
                    "pwd varchar(20) not null"+
                    ")");
            Log.e("Connection","Sucess");
            //Toast.makeText(c,"Connection sucess",Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException e) {
            Log.e("Error: ",e.getMessage());
           // Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void delete_table(){
        try{
            db.execSQL("drop table sms_user; ");
            Log.e("Table", "deleted");
        }
        catch (SQLiteException e) {
            Log.e("Error: ",e.getMessage());
        }
    }
    public String exec(String sql)
    {
        Log.e("Status: ","exec");
        if(!db.isOpen()) return "db_closed";
        try {
            db.execSQL(sql);
            Log.e("Status: ", "exec sucess");
            return "sucess";
        }
        catch (SQLiteException e) {
            Log.e("Error: ", e.getMessage());
            Pattern mypattern = Pattern.compile("unique", Pattern.CASE_INSENSITIVE);
            Matcher mymatcher= mypattern.matcher(e.getMessage());
            if(mymatcher.find())
                return "duplicate";
            else
                return "SQLite fail";
        }
    }
    Cursor execQuery(String sql)
    {
        try {
            return db.rawQuery(sql,null);
        }
        catch (SQLiteException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
    }
    public void close_db(){
        //Toast.makeText(activity, "Disconnecting DB", Toast.LENGTH_LONG).show();
        if(db.isOpen()) {
            db.close();

            Log.e("Status: ", "DB closed");
        }
    }
}
