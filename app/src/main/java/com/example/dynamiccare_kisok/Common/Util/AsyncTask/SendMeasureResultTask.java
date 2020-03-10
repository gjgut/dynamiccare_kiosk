package com.example.dynamiccare_kisok.Common.Util.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMeasureResultTask extends AsyncTask<String, Void, JSONObject> {
    @Override
    public JSONObject doInBackground(String... params) {
        JSONObject result = null;
        try {
            String url = "http://www.powerlogmobile.com/kiosk/save/measure";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            byte[] outputInBytes = params[0].getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(outputInBytes);
            os.close();

            int retCode = conn.getResponseCode();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = br.readLine()) != null) {
                response.append(line);
                response.append("");
            }
            br.close();
            JSONParser parser = new JSONParser();
            String res = response.toString();
            Object Obj = parser.parse(res);
            JSONObject jsonObject1 = new JSONObject(response.toString());

            Log.i("response", res.toString());
            result = jsonObject1;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}