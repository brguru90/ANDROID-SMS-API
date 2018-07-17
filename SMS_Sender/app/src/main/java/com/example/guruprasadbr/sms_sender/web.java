package com.example.guruprasadbr.sms_sender;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Guruprasad BR on 23-06-2018.
 */
public class web extends AppCompatActivity {
    HttpURLConnection conn;
    BufferedReader reader=null;
    protected String post(String website,String cookie,boolean getdata,String[][] para){
        String text = "";
        try {

            //String cookie = android.webkit.CookieManager.getInstance().getCookie(website);
            //text+=cookie;
            String data=null;
            if(para.length>=1)
                data = URLEncoder.encode(para[0][0], "UTF-8") + "=" + URLEncoder.encode(para[0][1], "UTF-8");
            for(int i=1;i<para.length;i++)
                data += "&" + URLEncoder.encode(para[i][0], "UTF-8") + "=" + URLEncoder.encode(para[i][1], "UTF-8");

            URL url = new URL(website);

            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
           // conn.setRequestProperty("Cookie", "__test=d6217b21c15986fbddd97ae802755500; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/ ");
            conn.setRequestProperty("Cookie", cookie);
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setUseCaches(true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if(data!=null) {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
            }
            int i=0;
            while(conn.getResponseCode()!= HttpsURLConnection.HTTP_OK && i<1000)
            {
                Thread.sleep(10);
                i++;
            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line=null;
            while((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            text= sb.toString();
            Log.e("RAW=", text);

            if(text.length()<10 || getdata==true)
                return text.substring(0, text.length()-1);
            else {

                return text.substring(380, 412) + "," + text.substring(428, 460) + "," + text.substring(476, 508);
            }
        }
        /*
        catch(UnsupportedEncodingException e){
            text+="Encodding: "+e.getMessage();
        }
        catch(MalformedURLException e){
            text+="URL: "+e.getMessage();
        }
        catch(IOException e){
            text+="open connection: "+e.getMessage();
        }
         catch (Exception e){
              text+="OThers: "+e.getMessage();
          }
        */
        catch (Exception e){
            text=e.getMessage();
        }
        finally
        {
            try
            {
                reader.close();
                conn.disconnect();
            }

            catch(Exception ex) { }
        }
        return text;
    }

    public static boolean check_internet(){
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
          //  if(ipAddr.equals(""))
                return true;

        } catch (Exception e) {}
        return false;
    }
}
