package com.example.guruprasadbr.sms_sender;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.widget.Toast;

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {
    public static Context ctx;
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        try {
            new get_user_permissions().request_permission(new MainActivity());
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);
        }
        catch (Exception e){
            Log.e("Boot SMS_SERVICE",e.getMessage());
        }*/

        Intent serviceIntent = new Intent(context, waiting_for_sms.class);
        startServiceByAlarm(context);
       // Toast.makeText(context,"Broadcast Service started",Toast.LENGTH_SHORT).show();
    }
    public static PendingIntent pendingIntent;
    public static  AlarmManager alarmManager;
    private void startServiceByAlarm(Context context)
    {

        // Get alarm manager.
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // Create intent to invoke the background service.
        Intent intent = new Intent(context, waiting_for_sms.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            alarmManager.cancel(pendingIntent);
            //pendingIntent.cancel();
        }
        catch (Exception e){ }
        long startTime = System.currentTimeMillis();
        long intervalTime = 10*1000;

        String message = "SMS API Service Started ";


        // Create repeat alarm.
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Toast.makeText(context, "1)"+message, Toast.LENGTH_SHORT).show();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
        }
        else
        {
            Toast.makeText(context, "2)"+message, Toast.LENGTH_SHORT).show();
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, intervalTime, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
        }
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);//valume
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }
}