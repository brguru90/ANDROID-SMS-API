package com.example.guruprasadbr.sms_sender;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.IBinder;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class waiting_for_sms extends Service implements host {
    public db_oper mydb;
    SQLiteDatabase db=null;
    android.os.Handler mHandler;
    String responce_str=null;

    public waiting_for_sms() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
       // return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        /*
        if(!web.check_internet()) {
            Toast.makeText(this,""+web.check_internet(),Toast.LENGTH_SHORT).show();
            return START_NOT_STICKY;
        }*/
        mHandler = new android.os.Handler();
        mydb=new db_oper(openOrCreateDatabase("my_sms_api", MODE_PRIVATE, null));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 20);//valume
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 50);//beep length
        //Toast.makeText(this,"Running...",Toast.LENGTH_SHORT).show();
        try {
            String uid = null;
            String sql = "select * from sms_user";
            Cursor res = mydb.execQuery(sql);
            if (res != null && res.getCount()>0) {
                res.moveToFirst();
                    uid = res.getString(0);
                    send(uid);
                    recieve(uid);
            }
        }
        catch (Exception e){
            Toast.makeText(this,"Service Excption:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY ;
    }
    private  void send(String uid){
        String responce_str=null;
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String number = tm.getLine1Number();
        }
        catch (Exception e){}


        try {
            String sql="select * from sms_user where uid='"+uid+"'";
            Cursor res=mydb.execQuery(sql);
            if(res!=null && res.getCount()>0) {
                res.moveToFirst();
                String[][] data=new String[2][2];
                while (!res.isAfterLast()) {
                    responce_str=null;
                    data[0][0]="uid";
                    data[0][1]=res.getString(0);
                    data[1][0]="pwd";
                    data[1][1]=res.getString(3);
                    if(!web.check_internet()) {
                        return;
                    }
                    responce_str=new web().post(host_url+"get_pending_sms.php",res.getString(2), true,data);
                    if((responce_str.split("<sep2>")).length>1)
                        responce_str=(responce_str.split("<sep2>"))[1];
                    if(responce_str.matches("empty"))
                        return;

                    String[] sms=responce_str.split("<sep>");
                    new send_sms(this,null).sendSMS(sms[3], sms[2]);
                    SharedPreferences sharedpreferences = this.getSharedPreferences("guruinfo.epizy.com", MODE_PRIVATE);
                    if(sharedpreferences.getString("status","No status").matches("sent"))
                    {
                        String[][] data2=new String[4][2];
                        data2[0][0]="type";
                        data2[0][1]="sent";
                        data2[1][0]="data";
                        data2[1][1]=sms[0]+"<sep>"+res.getString(1);
                        data2[2][0]="uid";
                        data2[2][1]=res.getString(0);
                        data2[3][0]="pwd";
                        data2[3][1]=res.getString(3);
                        String updt_status=new web().post(host_url+"update_sms.php", res.getString(2), true, data2);
                       // Toast.makeText(waiting_for_sms.this,"Sucess: to send:"+sharedpreferences.getString("status","No status")+":"+updt_status, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Toast.makeText(waiting_for_sms.this,"Failed: to send:"+sharedpreferences.getString("status","No status"), Toast.LENGTH_SHORT).show();
                    }
                    SharedPreferences.Editor editor = editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                   // onHandleIntent(intent);*/
                    res.moveToNext();
                }
            }
        }
        catch (Exception e){
            Toast.makeText(this, "doit(): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void recieve(String uid){
        try {
            String sql = "select * from sms_user where uid='" + uid + "'";
            Cursor res = mydb.execQuery(sql);
            if (res != null && res.getCount() > 0) {
                res.moveToFirst();
                String[][] data = new String[2][2];
                while (!res.isAfterLast()) {
                    data[0][0] = "uid";
                    data[0][1] = res.getString(0);
                    data[1][0] = "pwd";
                    data[1][1] = res.getString(3);
                    if (!web.check_internet()) {
                        return;
                    }
                    responce_str = new web().post(host_url+"get_last_sent_sms.php", res.getString(2), true, data);

                    if((responce_str.split("<sep2>")).length>1)
                        responce_str=(responce_str.split("<sep2>"))[1];
                    if (responce_str.matches("empty"))
                        return;
                    //Toast.makeText(this,responce_str,Toast.LENGTH_LONG).show();
                String[] sms=responce_str.split("<sep>");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //Date sent_date = sdf.parse(sms[2]);
               // long sent_millis = sent_date.getTime();
                String[] sent_time=(sms[2].split(" "))[1].split(":");
                int sent_millis=Integer.parseInt(sent_time[0])*3600+Integer.parseInt(sent_time[1])*60+Integer.parseInt(sent_time[2]);
                Uri uri = Uri.parse("content://sms/inbox");
                Cursor c = getContentResolver().query(uri, null, null, null, null);
                if (c.moveToFirst()) {
                    String text=null;
                    for (int i = 0; i < c.getCount(); i++) {
                        if (c.getString(c.getColumnIndexOrThrow("address")).toString().matches("[0-9+]*"+sms[1]+"$")) {
                            Long rec_milSec= c.getLong(c.getColumnIndexOrThrow("date"));
                            String date = sdf.format(new Date(rec_milSec));
                            String id=sms[0];
                            String number=c.getString(c.getColumnIndexOrThrow("address")).toString();
                            String msg=c.getString(c.getColumnIndexOrThrow("body")).toString();
                            String[] recv_time=(date.split(" "))[1].split(":");
                            int recv_millis=Integer.parseInt(recv_time[0])*3600+Integer.parseInt(recv_time[1])*60+Integer.parseInt(recv_time[2]);
                           // Toast.makeText(this,responce_str+"\n"+id+":"+uid+":"+number + " : " + msg+":"+date+"\n"+recv_millis+"\n"+sent_millis+"\n"+ Math.abs(sent_millis-recv_millis),Toast.LENGTH_LONG).show();
                            if( Math.abs(sent_millis-recv_millis)<300) {
                                data = new String[4][2];
                                data[0][0] = "uid";
                                data[0][1] = res.getString(0);
                                data[1][0] = "pwd";
                                data[1][1] = res.getString(3);
                                data[2][0]="data";
                                data[2][1]=id+"<sep>"+uid+"<sep>"+msg;
                                data[3][0]="type";
                                data[3][1]="recv";
                                String resp=new web().post(host_url+"update_sms.php", res.getString(2), true, data);
                                //Toast.makeText(this,resp, Toast.LENGTH_LONG).show();
                            }
                            //int rec_milsec=Integer.parseInt(rec_date[0]*rec_date[1]
                            break;
                        }
                        c.moveToNext();
                    }
                }
                    res.moveToNext();
                }
            }
        }
        catch (Exception e){
            Toast.makeText(this, "recieve(): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    protected void onHandleIntent(Intent intent) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(waiting_for_sms.this, responce_str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
