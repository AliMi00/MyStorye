package com.yahoo.mystorye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestWebActivity extends AppCompatActivity {

    Button btnWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web2);
        btnWeb = findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(btnWebOnClickListener);

    }

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
