package com.yahoo.mystorye;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import database.datasource.tb_StoryDataSource;
import database.structure.tb_StoryStructure;
import database.table.tb_Story;

public class MainActivity extends AppCompatActivity {

    Button btnPopular,btnNew,btnRate,btnGenre;


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPopular = findViewById(R.id.btnPopular);
        btnPopular.setOnClickListener(btnPopularOnClickListener);

        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(btnNewOnClickListener);

        btnRate = findViewById(R.id.btnRate);
        btnRate.setOnClickListener(btnRateOnClickListener);

        btnGenre = findViewById(R.id.btnGenre);
        btnGenre.setOnClickListener(btnGenreOnClickListener);

        //todo connect to the server if internet is connected and get story and save to database
        //todo if is not connect toast declare method to get stories
        //todo set search button to search in database


    }

    View.OnClickListener btnPopularOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
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
            finish();


        }
    };
    View.OnClickListener btnNewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
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
            finish();

        }
    };

    View.OnClickListener btnRateOnClickListener = new View.OnClickListener() {
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
            finish();

        }
    };

    View.OnClickListener btnGenreOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            GenreListActivity.OnListListener Lst = new GenreListActivity.OnListListener() {
                @Override
                public List<String> onListListenerString() {
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
            finish();


        }
    };

}
