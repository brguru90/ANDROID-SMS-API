package com.example.guruprasadbr.sms_sender;

import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;

import java.net.InetAddress;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements host {
    public static db_oper mydb;
    public volatile TextView tv=null;
    public static String status="";
    String key=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Activity", "Oncreate");
        super.onCreate(savedInstanceState);
        sendBroadcast(new Intent(MainActivity.this, BroadcastReceiverOnBootComplete.class));
        if(db_oper.db==null)
            mydb=new db_oper(this);
        String sql="select * from sms_user";
        Cursor res=MainActivity.mydb.execQuery(sql);
        res.moveToFirst();
        if(res!=null && res.getCount()>0) {
            res.close();
            startActivity(new Intent(MainActivity.this, logout.class));
        }
        try {
            new get_user_permissions().request_permission(this);
        }
        catch (Exception e){}
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(!web.check_internet()) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                finishAffinity();
            } else if (Build.VERSION.SDK_INT >= 21) {
                finishAndRemoveTask();
            }
        }
        try{
            TextView mobile=((TextView) findViewById(R.id.editText3));
            if(mobile.getText().length()<2) {
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String number = tm.getLine1Number();
                mobile.setText(number.substring(number.length()-10,number.length()));
            }
        }
        catch (Exception e){}
        tv=((TextView) findViewById(R.id.textView));
        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_status();
                tv.setText("");
                Log.e("Status", status);
            }
        });
        tv.setText("");
    }

    protected void check_status(){
        status="";
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            key = clipboard.getText().toString();
            Log.e("key",key);
        }
        catch (Exception e){}
        TextView mobile=((TextView) findViewById(R.id.editText3));
        String mob=mobile.getText().toString();
        check_mobile_no(mob);
    }


    String message;
    String mobno;
    Handler handler;
    protected void check_mobile_no(String number){
        try {
            message="";
            Random rand = new Random();
            message="OTP:";
            for(int i=0;i<4;i++)
                message+= rand.nextInt(9);
            mobno=number;
           // SmsManager smsManager = SmsManager.getDefault();
           // smsManager.sendTextMessage(number, null,  message, null, null);
           //message="OTP:2616";
            //new sendsms(this).sendSMS(this,0,number,null,msg,null,null);
            //SMSUtils.sendSMS(this, number, msg);
            //new send_sms(this).send_sms(number,msg);
            new send_sms(MainActivity.this.getApplicationContext(),this).sendSMS(number, message);
            try{
                Thread.sleep(2000);
            }
            catch (Exception e){}
            SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("guruinfo.epizy.com", MODE_PRIVATE);
            if(!sharedpreferences.getString("status","No status").matches("sent")) {
                Toast.makeText(this,"SMS:"+sharedpreferences.getString("status","No status"),Toast.LENGTH_LONG).show();
                return;
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            int i=0;
                    while(i<5) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        i++;
                    };
            wait_sec=30;
            if(handler==null) {
                handler = new Handler();
                handler.post(updateView);
            }
        }
        catch (Exception e){
            Log.e("SMS=", e.getMessage());
        }
    }
    volatile int  wait_sec=30;
    private Runnable updateView = new Runnable() {
        @Override
        public void run() {
            final String msg=message;
            final String mob_no=mobno;
            if(wait_sec>0) {
                Uri uri = Uri.parse("content://sms/inbox");
                Cursor c = getContentResolver().query(uri, null, null, null, null);
                startManagingCursor(c);
                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {
                        if (c.getString(c.getColumnIndexOrThrow("body")).toString().matches(msg) && c.getString(c.getColumnIndexOrThrow("address")).toString().matches("[0-9+]*"+mob_no+"$")) {
                            Log.e("SMS-OTP", c.getString(c.getColumnIndexOrThrow("address")).toString() + " : " + c.getString(c.getColumnIndexOrThrow("body")).toString());
                            // Toast.makeText(this,c.getString(c.getColumnIndexOrThrow("address")).toString() + " : " + c.getString(c.getColumnIndexOrThrow("body")).toString(),Toast.LENGTH_SHORT).show();
                            login();
                            status="OTP Confirmed";
                            wait_sec=0;
                            handler=null;
                            return;
                        }
                        // Log.e("SMS_"+wait_sec,  c.getString(c.getColumnIndexOrThrow("body")).toString()+" : "+c.getString(c.getColumnIndexOrThrow("address")).toString());
                        c.moveToNext();
                    }
                }
                wait_sec--;
                Log.e("wait_sec",wait_sec+"");
                tv.setText("Waiting For OTP:"+Integer.toString(wait_sec));
                handler.postDelayed(this, 1000);
            }
            else{
                Toast.makeText(MainActivity.this, "OTP Could't confirmed", Toast.LENGTH_SHORT).show();
                wait_sec=0;
                handler=null;
                return;
            }
        }

    };

    public void login(){
        Toast.makeText(MainActivity.this, "OTP confirmed", Toast.LENGTH_SHORT).show();
        String uid=((TextView)findViewById(R.id.editText)).getText().toString();
        String pwd=((TextView)findViewById(R.id.editText2)).getText().toString();
        String[][] para=new String[2][2];
        para[0][0]="user";
        para[0][1]=uid;
        para[1][0]="pwd";
        para[1][1]=pwd;
        String res=new web().post(host_url+"check_pwd.php",key,false,para);
        if(res==null) {
            Log.e("NULL","true");
            return;
        }
        if(res.length()>10) {
            key=null;
            Log.e("URL", host_url+"/check.php?data=" + res);
            // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://guruinfo.epizy.com/projects/check.php?data="+res)));
            String url=host_url+"check.php?data="+res;

            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            catch(Exception e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
            /*
            try {
               String out=Jsoup.connect("http://guruinfo.epizy.com/projects/check.php?data="+res).get().toString();
                Log.e("",out);
                Toast.makeText(MainActivity.this,out,Toast.LENGTH_LONG).show();
            }
            catch (Exception e){}*/
        }
        else {
            TextView mobile=((TextView) findViewById(R.id.editText3));
            String status = "";
            if (res.matches("valid")) {
                String sql = "insert into sms_user values('" + uid + "','"+mobile.getText().toString()+"','"+key+"','"+ pwd + "')";
                status = MainActivity.mydb.exec(sql);
                if(status.matches("sucess"))
                {
                    startActivity(new Intent(MainActivity.this, logout.class));
                }
                else  if (res.matches("duplicate")){
                    Toast.makeText(MainActivity.this,"Registered",Toast.LENGTH_LONG).show();
                }
                else {
                    MainActivity.mydb.delete_table();
                    mydb=new db_oper(this);
                }
                status=res + ":" + status;
                return;
            }
            else{
                Toast.makeText(MainActivity.this,"Authentication Failure",Toast.LENGTH_LONG).show();
            }
        }
        status= res;
    }

    public void onBackPressed() {

        moveTaskToBack(true);
        if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
            finishAffinity();
        } else if(Build.VERSION.SDK_INT>=21){
            finishAndRemoveTask();
        }
        //mydb.close_db();
        /*
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
