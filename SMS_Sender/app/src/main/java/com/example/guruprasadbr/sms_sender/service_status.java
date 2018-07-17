package com.example.guruprasadbr.sms_sender;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Guruprasad BR on 27-06-2018.
 */
public class service_status implements host {
    String file_name="/sdcard/sms_satus.txt";
    public void set_running(){
        try {
            File file = new File(file_name);
            file.createNewFile();

            FileOutputStream data = new FileOutputStream(file);
            data.write(host_url.getBytes());
                    data.close();
        }
        catch (Exception e){}
    }
    public void set_stopped(){
        try{
            File file = new File(file_name);
            file.delete();
        }
        catch (Exception e){}
    }
    public boolean get_satus(){
        try{
            File file = new File(file_name);
                return file.exists();
        }
        catch (Exception e){}
        return false;
    }

}
