package com.yahoo.mystorye;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostRequest extends AsyncTask<String, String, String> {
    private Context _context;
    private HttpPostRequest.OnHttpRequestListener _onHttpRequestListener;

    public interface OnHttpRequestListener{
        void onDataReceived(String value);
    }

    public HttpPostRequest(Context context, HttpPostRequest.OnHttpRequestListener onHttpRequestListener)
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
        String data = params[1]; //data to post
        OutputStream outputStream = null;

        HttpURLConnection connection=null;


        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            outputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(outputStream,"UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();
            result =connection.getResponseMessage();


        }
        catch (Exception e)
        {
            return e.toString();
        }

        if(connection != null){

            connection.disconnect();

        }


        return result;


    }

    @Override
    protected void onPostExecute(String s) {
        if(_onHttpRequestListener!=null)
            _onHttpRequestListener.onDataReceived(s);
    }
}
