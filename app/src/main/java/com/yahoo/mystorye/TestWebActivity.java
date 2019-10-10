package com.yahoo.mystorye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import database.datasource.tb_StoryDataSource;

public class TestWebActivity extends AppCompatActivity {

    Button btnWeb,btnPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web2);
        btnWeb = findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(btnWebOnClickListener);

        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(btnPostOnClickListener);

    }
    //post btn
    View.OnClickListener btnPostOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tb_StoryDataSource db = new tb_StoryDataSource(TestWebActivity.this);
            db.open();
            db.find(1);

            HttpPostRequest postRequest= new HttpPostRequest(TestWebActivity.this,onHttpRequestListener);

            postRequest.execute("http://192.168.1.6:88/api/values","Testing data");
        }
    };
    //post interface
    HttpPostRequest.OnHttpRequestListener onHttpRequestListener = new HttpPostRequest.OnHttpRequestListener() {
        @Override
        public void onDataReceived(String value) {
            Toast.makeText(TestWebActivity.this, value, Toast.LENGTH_SHORT).show();
        }
    };
    //test web access
    View.OnClickListener btnWebOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WebService webService = new WebService(TestWebActivity.this,onWebServiceListener);
            webService.transmitAsync("api/values");

        }
    };
    WebService.OnWebServiceListener onWebServiceListener = new WebService.OnWebServiceListener() {
        @Override
        public void onDataReceived(String value) {
            Toast.makeText(TestWebActivity.this, value, Toast.LENGTH_SHORT).show();

        }
    };
}
