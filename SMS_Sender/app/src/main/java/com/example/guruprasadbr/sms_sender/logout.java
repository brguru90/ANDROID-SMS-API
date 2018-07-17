package com.example.guruprasadbr.sms_sender;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Guruprasad BR on 24-06-2018.
 */
public class logout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new get_user_permissions().request_permission(this);
        }
        catch (Exception e){}
        setContentView(R.layout.logout);
        Button logout = (Button) findViewById(R.id.button2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "delete from sms_user;";
                if ((MainActivity.mydb.exec(sql)).matches("sucess")) {
                    String status = MainActivity.mydb.exec(sql);
                    Log.d("Logout", status);
                    startActivity(new Intent(logout.this, MainActivity.class));
                } else
                    Toast.makeText(logout.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onBackPressed() {

        moveTaskToBack(true);
        if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
            finishAffinity();
        } else if(Build.VERSION.SDK_INT>=21) {
            finishAndRemoveTask();
        }
/*
        ActivityCompat.finishAffinity(this);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
        //Intent broadcastIntent = new Intent("com.example.guruprasadbr.sms_sender.BroadcastReceiverOnBootComplete");
        //sendBroadcast(broadcastIntent);
    }
}