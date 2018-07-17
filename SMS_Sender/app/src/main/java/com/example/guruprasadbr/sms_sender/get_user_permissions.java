package com.example.guruprasadbr.sms_sender;

import android.Manifest;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Guruprasad BR on 24-06-2018.
 */
public class get_user_permissions extends AppCompatActivity {
    private RequestPermissionHandler mRequestPermissionHandler;
    public get_user_permissions(){
        mRequestPermissionHandler=new RequestPermissionHandler();
    }

    protected void request_permission(Activity activity) {
        mRequestPermissionHandler.requestPermission(activity, new String[]{
                Manifest.permission.RECEIVE_BOOT_COMPLETED,Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.VIBRATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(get_user_permissions.this, "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(get_user_permissions.this, "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
