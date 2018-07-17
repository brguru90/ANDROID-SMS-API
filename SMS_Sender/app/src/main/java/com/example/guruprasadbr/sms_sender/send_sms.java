package com.example.guruprasadbr.sms_sender;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Guruprasad BR on 24-06-2018.
 */
public class send_sms {
    protected  Activity activity;
    protected Context context;
    public send_sms(Context context,Activity activity){
        this.activity=activity;
        this.context=context;
    }

    protected void sendsms(String number,String msg) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
        smsIntent.putExtra("sms_body", msg);

        try {
            activity.startActivity(smsIntent);
            activity.finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
    public  boolean sendSMS(Context ctx, int simID, String toNum, String centerNum, String smsText, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        String name;

        try {
            if (simID == 0) {
                name = "isms";
                // for model : "Philips T939" name = "isms0"
            } else if (simID == 1) {
                name = "isms2";
            } else {
                throw new Exception("can not get service which for sim '" + simID + "', only 0,1 accepted as values");
            }
            Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            method.setAccessible(true);
            Object param = method.invoke(null, name);

            method = Class.forName("com.android.internal.telephony.ISms$Stub").getDeclaredMethod("asInterface", IBinder.class);
            method.setAccessible(true);
            Object stubObj = method.invoke(null, param);
            if (Build.VERSION.SDK_INT < 18) {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, toNum, centerNum, smsText, sentIntent, deliveryIntent);
            } else {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, ctx.getPackageName(), toNum, centerNum, smsText, sentIntent, deliveryIntent);
            }

            return true;
        } catch (ClassNotFoundException e) {
            Log.e("apipas", "ClassNotFoundException:" + e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e("apipas", "NoSuchMethodException:" + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e("apipas", "InvocationTargetException:" + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("apipas", "IllegalAccessException:" + e.getMessage());
        } catch (Exception e) {
            Log.e("apipas", "Exception:" + e.getMessage());
        }
        return false;
    }
    protected void send_sms(String number,String msg){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null,  msg, null, null);
    }

    void sendSMS(String phoneNumber, String message)
    { SharedPreferences sharedpreferences = context.getSharedPreferences("guruinfo.epizy.com", context.MODE_PRIVATE);
        Log.e("Status",sharedpreferences.getString("status", "No status"));
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(SENT),0 );
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                SharedPreferences sharedpreferences = context.getSharedPreferences("guruinfo.epizy.com", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        editor.putString("status", "sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        editor.putString("status", "Generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        editor.putString("status", "No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        editor.putString("status", "Null PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        editor.putString("status", "Radio off");
                        break;
                }
                editor.commit();
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.e("SMS","delivered");
                        //Toast.makeText(context, "SMS delivered",
                               // Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                       // Toast.makeText(context, "SMS not delivered",
                             //   Toast.LENGTH_SHORT).show();
                        Log.e("SMS"," not delivered");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

}
