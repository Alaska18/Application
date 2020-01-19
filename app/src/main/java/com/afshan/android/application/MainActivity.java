package com.afshan.android.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringJoiner;


public class MainActivity extends AppCompatActivity {
     Button connect;
     EditText editText;
     String mAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect = findViewById(R.id.button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 makeConnection();
            }
        });
    }
    public void makeConnection()
    {
        String result;
          editText = findViewById(R.id.ipaddress);
          mAddress = editText.getText().toString();
          new Task().execute(mAddress);

    }
   public class Task extends AsyncTask<String, String, String>
   {
       @Override
       protected String doInBackground(String... params) {
           URL url = null;
           Double lat = 12.92372;
           Double Long = 77.4965071;
           String result = null;
           Map<String,String> arguments = new HashMap<>();
           arguments.put("id", "abcdefgh");
           StringJoiner sj = new StringJoiner("&");
           try{for(Map.Entry<String,String> entry : arguments.entrySet())
               sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                       + URLEncoder.encode(entry.getValue(), "UTF-8"));}
           catch(UnsupportedEncodingException i)
           {
               System.out.println(i);
           }
           byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
           //byte[] out = "{\"id\":\"abcdefgh\",\"latitude\":\"12.000\"}".getBytes(StandardCharsets.UTF_8);
           int Length = out.length;
           try
           {
                url = new URL(mAddress);
           }
           catch (MalformedURLException m)
           {
               System.out.println("okay");
           }
           try{
           URLConnection urlConnection = url.openConnection();
           HttpURLConnection h = null;
           h = (HttpURLConnection) urlConnection;
           h.setRequestMethod("POST");
           h.setConnectTimeout(1000);
           h.setReadTimeout(1000);
           h.setDoOutput(true);
           h.setFixedLengthStreamingMode(Length);
           h.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
           //h.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           h.connect();
               BufferedOutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
               //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
               os.write(out);
              // writer.flush();
               //writer.close();
               os.close();
           System.out.println("here");
           if(h.getResponseCode() != 200)
           {
               System.out.println("ok" + h.getResponseCode() + h.getResponseMessage());
           }
           else
           {
               result = "notok";
           }
           }
           catch (IOException i)
           {
              System.out.println("ok2" + i);
           }
           return result;


       }
   }
}
