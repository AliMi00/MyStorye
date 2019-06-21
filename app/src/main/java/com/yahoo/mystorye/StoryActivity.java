package com.yahoo.mystorye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class StoryActivity extends AppCompatActivity {
    TextView txtPageNumber,txtStoryName,txtGenre,txtStory;
    Button btnGoPage,btnNextPage,btnPrePage;
    EditText edtPageNumber;

    tb_Story FullStory;
    public int PKStory;
    int PageNumber = 0;

    List<String> StoryPages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        txtPageNumber = findViewById(R.id.txtPageNumber);
        txtGenre =findViewById(R.id.txtGenre);
        txtStoryName =findViewById(R.id.txtStoryName);
        txtStory =findViewById(R.id.txtStory);

        edtPageNumber =findViewById(R.id.edtPageNumber);

        btnGoPage = findViewById(R.id.btnGoPage);
        btnGoPage.setOnClickListener(btnGoPageOnClickListener);
        btnNextPage = findViewById(R.id.btnNextPage);
        btnNextPage.setOnClickListener(btnNextPageOnClickListener);
        btnPrePage = findViewById(R.id.btnPrePage);
        btnPrePage.setOnClickListener(btnPrePageOnClickListener);


        Intent intent = getIntent();
        PKStory =intent.getIntExtra("PK",0);

        tb_StoryDataSource dataSource = new tb_StoryDataSource(StoryActivity.this);
        dataSource.open();
        FullStory = dataSource.find(PKStory);
        dataSource.close();

        StoryPages = getTermsString(FullStory.Story);
        txtGenre.setText(FullStory.Genre);
        PageNumber=FullStory.MarkedPlace;
        txtPageNumber.setText(String.valueOf(PageNumber));
        txtStoryName.setText(FullStory.StoryName);

        txtStory.setText(StoryPages.get(PageNumber));


    }

    View.OnClickListener btnNextPageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PageNumber++;
            txtStory.setText(StoryPages.get(PageNumber));
            txtPageNumber.setText(String.valueOf(PageNumber));
            tb_StoryDataSource dataSource = new tb_StoryDataSource(StoryActivity.this);
            dataSource.open();
            dataSource.updatePage(PKStory,PageNumber);
            dataSource.close();


        }
    };

    View.OnClickListener btnPrePageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PageNumber--;
            txtStory.setText(StoryPages.get(PageNumber));
            txtPageNumber.setText(String.valueOf(PageNumber));
            tb_StoryDataSource dataSource = new tb_StoryDataSource(StoryActivity.this);
            dataSource.open();
            dataSource.updatePage(PKStory,PageNumber);
            dataSource.close();

        }
    };

    View.OnClickListener btnGoPageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PageNumber=Integer.valueOf(edtPageNumber.getText().toString());
            txtStory.setText(StoryPages.get(PageNumber));
            txtPageNumber.setText(String.valueOf(PageNumber));
            tb_StoryDataSource dataSource = new tb_StoryDataSource(StoryActivity.this);
            dataSource.open();
            dataSource.updatePage(PKStory,PageNumber);
            dataSource.close();

        }
    };



    //get Story Pages
    private List<String> getTermsString(String Story) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        List<String> pagesStory=new ArrayList<>();
        try {
            reader = new BufferedReader(new StringReader(Story));


            String str;
            int i =0;
            while ((str = reader.readLine()) != null ) {
                termsString.append(str+"\n");
                i++;
                while (i==25){
                    pagesStory.add(termsString.toString());
                    termsString.delete(0,termsString.length());
                    i=0;
                }


            }

            reader.close();
            return pagesStory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //    ReadStorydb.OnStoryListener onStoryListener = new ReadStorydb.OnStoryListener() {
//        @Override
//        public void onStoryListener(String story) {
//
//            txtStory.setText(story);
//
//
//        }
//    };
}
class ReadStorydb extends AsyncTask<String,Void,String>{

    ProgressDialog dialog;
    private Context _context;

 private TextView _txtStory;


    public ReadStorydb(Context context,TextView txtStory)
    {
        _context=context;
        _txtStory = txtStory;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog= new ProgressDialog(_context);
        dialog.setTitle("Loading");
        dialog.setMessage("در حال بار گزاری داستان ");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... tb_stories) {
//        if(_onStoryListener!=null)
//            _onStoryListener.onStoryListener(tb_stories[0]);
        _txtStory.setText(tb_stories[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();


    }
//    interface OnStoryListener{
//        public void onStoryListener(String story);
//    }

}
