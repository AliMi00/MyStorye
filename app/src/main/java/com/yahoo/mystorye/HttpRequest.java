package com.yahoo.mystorye;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class HttpRequest extends AsyncTask<String, Void, String> {

    private Context _context;
    private  OnHttpRequestListener _onHttpRequestListener;

    public interface OnHttpRequestListener{
        void onDataReceived(String value);
    }

    public HttpRequest(Context context, OnHttpRequestListener onHttpRequestListener)
    {
        _context=context;
        _onHttpRequestListener=onHttpRequestListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String result=null;

        HttpURLConnection connection=null;
        BufferedReader reader=null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            result =  buffer.toString();
        }
        catch (Exception e)
        {

        }

        if(connection != null)
            connection.disconnect();

        try {
            if (reader != null)
                reader.close();
        }
        catch (Exception e)
        {

        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(_onHttpRequestListener!=null)
            _onHttpRequestListener.onDataReceived(s);
    }
}
