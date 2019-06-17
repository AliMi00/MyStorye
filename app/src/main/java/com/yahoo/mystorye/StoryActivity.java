package com.yahoo.mystorye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.List;

import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class StoryActivity extends AppCompatActivity {
    TextView txtAuthor,txtStoryName,txtGenre,txtStory;

    tb_Story FullStory;
    public static int PKStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        txtAuthor = findViewById(R.id.txtAuthor);
        txtGenre =findViewById(R.id.txtGenre);
        txtStoryName =findViewById(R.id.txtStoryName);
        txtStory =findViewById(R.id.txtStory);

        Intent intent = getIntent();
        PKStory =intent.getIntExtra("PK",0);

        tb_StoryDataSource dataSource = new tb_StoryDataSource(StoryActivity.this);
        dataSource.open();
        FullStory = dataSource.find(PKStory);
        dataSource.close();

        txtGenre.setText(FullStory.Genre);
        txtAuthor.setText(FullStory.Author);
        txtStoryName.setText(FullStory.StoryName);
        //txtStory.setText(FullStory.Story);

        ReadStorydb readStorydb = new ReadStorydb(StoryActivity.this,txtStory);
        readStorydb.execute(FullStory.Story);


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
