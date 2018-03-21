package com.example.java.myapplication;

import android.os.AsyncTask;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.ProtocolException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConexionHTTPGet {

    InputStream is = null;
    JSONObject jObj = new JSONObject();
    String Url = "";
    String result = "";

    public JSONObject makeHttpRequest(String url, List params) throws ExecutionException, InterruptedException, JSONException {
        String paramString = URLEncodedUtils.format(params, "utf-8");
        if (!params.isEmpty()) {
            Url = url + "?" + paramString;
        } else {
            Url = url;
        }
        GetUrlContentTask gc = new GetUrlContentTask();
        result = gc.execute().get();
        jObj = new JSONObject(result);
        return jObj;
    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(Url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null) {
                    content += line + "\n";
                }
                return content;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}