package com.yahoo.mystorye;

import android.content.Context;
import android.provider.DocumentsContract;

import com.google.gson.Gson;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class WebService {

    public Object values;
    private final String serverAddress="http://192.168.1.6/";
    private  Context _context;
    private OnWebServiceListener _onWebServiceListener;

    public interface OnWebServiceListener{

        public void onDataReceived(String value);

    }

    public WebService(Context context,OnWebServiceListener onWebServiceListener)
    {
        _context=context;
        _onWebServiceListener = onWebServiceListener;
    }

    public void transmitAsync(String value)
    {
        HttpRequest request=new HttpRequest(_context,onHttpRequestListener);
        String url=serverAddress + value;
        request.execute(url);

    }


    private String FetchData(String value)
    {
        Document doc=null;
        InputStream in=null;

        in=new ByteArrayInputStream(value.getBytes(java.nio.charset.Charset.forName("UTF-8")));

        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(in);
        }
        catch (Exception e)
        {


        }


        doc.getDocumentElement().normalize();

        NodeList resultElement = doc.getElementsByTagName("LoginStatus");

        if(resultElement.getLength()>0){
            return   resultElement.item(0).getChildNodes().item(0).getNodeValue();
        }

        return null;

    }


    HttpRequest.OnHttpRequestListener onHttpRequestListener=new HttpRequest.OnHttpRequestListener() {
        @Override
        public void onDataReceived(String value) {
            //String result=FetchData(value);

            if(_onWebServiceListener!=null)
             //   _onWebServiceListener.onDataReceived(result);
                _onWebServiceListener.onDataReceived(value);

        }
    };
}

