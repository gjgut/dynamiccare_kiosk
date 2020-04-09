package com.PowerLog.Common.Util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, JSONObject> {
    @Override
    public JSONObject doInBackground(String... params) {
        JSONObject result=null;
        try {
            String url = params[0];
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            byte[] outputInBytes = params[1].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write( outputInBytes );
            os.close();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append("");
            }
            br.close();
            String res = response.toString();
            JSONObject jsonObject1 = new JSONObject(response.toString());

            Log.i("response",res);
            result = jsonObject1;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}