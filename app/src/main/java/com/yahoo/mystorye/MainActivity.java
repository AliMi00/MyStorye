package com.yahoo.mystorye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import database.DatabaseManagement;
import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class MainActivity extends AppCompatActivity {

    Button btnPopular,btnNew, btnSaved,btnGenre,btnTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        insertStory();

        //test
        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(btnTestOnClickListener);

        btnPopular = findViewById(R.id.btnPopular);
        btnPopular.setOnClickListener(btnPopularOnClickListener);

        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(btnNewOnClickListener);

        btnSaved = findViewById(R.id.btnSaved);
        btnSaved.setOnClickListener(btnSavedOnClickListener);

        btnGenre = findViewById(R.id.btnGenre);
        btnGenre.setOnClickListener(btnGenreOnClickListener);

        setServerRespond();




        //todo connect to the server if internet is connected and get story and Change the popular and New
        //todo if is not connect toast declare method to get stories
        //todo set search button to search in server


    }

    View.OnClickListener btnTestOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this, TestWebActivity.class);
            startActivity(intent);
        }
    };
//btnPopular
    View.OnClickListener btnPopularOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
                   //todo connect server and get the list and delete this code
                    tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
                    dataSource.open();
                    List<tb_Story> lst = dataSource.getLikeList();
                    dataSource.close();
                    return lst;
                }

            };
            StoryListActivity.ListListener =Lst;
            Intent intent=new Intent(MainActivity.this, StoryListActivity.class);
            startActivity(intent);

        }
    };

//btnNew
    View.OnClickListener btnNewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
                    //todo connect server and get the list and delete this code
                    tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
                    dataSource.open();
                    List<tb_Story> lst = dataSource.getNewList();
                    dataSource.close();
                    return lst;
                }

            };
            StoryListActivity.ListListener =Lst;
            Intent intent=new Intent(MainActivity.this, StoryListActivity.class);
            startActivity(intent);
        }
    };

//btnSaves
    View.OnClickListener btnSavedOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
                    tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
                    dataSource.open();
                    List<tb_Story> lst = dataSource.getRateList();
                    dataSource.close();
                    return lst;
                }

            };
            StoryListActivity.ListListener =Lst;
            Intent intent=new Intent(MainActivity.this, StoryListActivity.class);
            startActivity(intent);

        }
    };

//btnGenre
    View.OnClickListener btnGenreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            GenreListActivity.OnListListener Lst = new GenreListActivity.OnListListener() {
                @Override
                public List<String> onListListenerString() {
                    //todo connect server and get the list and delete this code if not connected get from database
                    tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
                    dataSource.open();
                    List<String> lst = dataSource.getGenreList();
                    dataSource.close();
                    return lst;
                }
            };
            GenreListActivity.ListListener =Lst;
            Intent intent=new Intent(MainActivity.this, GenreListActivity.class);
            startActivity(intent);
        }
    };
//Methods
    public void insertStory(){
        if (DatabaseManagement.isFirstTime)
        {
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss ");
            String date = df.format(Calendar.getInstance().getTime());

            for(int i=235 ; i<238 ;i++) {
                tb_Story story = new tb_Story();
                story.PKStory = i;
                story.StoryName = "داستان "+String.valueOf(i);
                story.Story = getTermsStringBase("story.txt");
                story.Genre = "genre";
                story.Like = 0;
                story.Rate = i;
                story.Version = i;
                story.MarkedPlace = i;
                story.CreateDate = date ;
                story.Author = "author" ;

                tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
                dataSource.open();
                dataSource.add(story);
                dataSource.close();
            }
            DatabaseManagement.isFirstTime =false;
        }

    }
    private List<String> getTermsString(String FileName) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        List<String> pagesStory=new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(FileName)));

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
    private String getTermsStringBase(String FileName) {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(FileName)));

            String str;

            while ((str = reader.readLine()) != null ) {
                termsString.append(str+"\n");

            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setServerRespond(){

        WebService webService = new WebService(MainActivity.this, new WebService.OnWebServiceListener() {
            @Override
            public void onDataReceived(String value) {
                if (value ==null){
                    btnPopular.setEnabled(false);
                    btnNew.setEnabled(false);
                    Toast.makeText(MainActivity.this, "خطا در اتصال به سرور", Toast.LENGTH_SHORT).show();

                }
                else
                Toast.makeText(MainActivity.this, "isConnected", Toast.LENGTH_SHORT).show();

            }
        });
        webService.transmitAsync("api/values");

    }

}
