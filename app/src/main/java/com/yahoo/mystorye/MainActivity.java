package com.yahoo.mystorye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
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
    AdView adViewBottom;
    InterstitialAd intAd;
    List<tb_Story> lst2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding banner ads
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-8126152475553425~4180777880");
        adViewBottom =findViewById(R.id.adViewBottom);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBottom.loadAd(adRequest);

        //MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");
        intAd = new InterstitialAd(getApplicationContext());
        intAd.setAdUnitId("ca-app-pub-8126152475553425/7758072570");
        intAd.loadAd(new AdRequest.Builder().build());

        insertStory();

        //test
        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(btnTestOnClickListener);

        btnPopular = findViewById(R.id.btnPopular);
        btnPopular.setOnClickListener(btnPopularOnClickListener);

        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(btnNewStoryOnClickListener);

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

    View.OnClickListener btnNewStoryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WebService webService = new WebService(MainActivity.this,onWebServiceListener);
            webService.transmitAsync("api/StoryTemp");

            StoryListActivity.storyList =lst2;
            Intent intent=new Intent(MainActivity.this, StoryListActivity.class);
            startActivity(intent);
        }
    };

//btnPopular
    View.OnClickListener btnPopularOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {

            LoadMyList myList = new LoadMyList(MainActivity.this);
            myList.execute();

        }
    };


//webservice
    WebService.OnWebServiceListener onWebServiceListener = new WebService.OnWebServiceListener() {
    @Override
    public void onDataReceived(String value) {
        Gson gson = new Gson();
         StoryListActivity.storyList =  gson.fromJson(value,new TypeToken<List<tb_Story>>() {}.getType());
         lst2 =  gson.fromJson(value,new TypeToken<List<tb_Story>>() {}.getType());
    }
};

//btnSaves
    View.OnClickListener btnSavedOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
            dataSource.open();
            List<tb_Story> lst = dataSource.getRateList();
            dataSource.close();

            intAd.show();
            StoryListActivity.storyList =lst;
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
    //add static stories to db
    //todo add more stories to assets and here
    public void insertStory(){
        if (DatabaseManagement.isFirstTime)
        {
           addStoryToDbFromAssets(1,"عادت می کنم","adat.txt","عاشقانه","زویا پیرزاد");
            addStoryToDbFromAssets(2,"خانه مرگ","death.txt","ترسناک","ار ال استاین");
            addStoryToDbFromAssets(3,"راز الن","ellen.txt","عاشقانه","جین بوکر");
            addStoryToDbFromAssets(4,"کیمیاگر","kimi.txt","عاشقانه","پاولو کویلو");
            addStoryToDbFromAssets(5,"کشیش ویکفیلد","story2.txt","خانوادگی"," الیور گلدسمیت ");


            DatabaseManagement.isFirstTime =false;
        }

    }
    //not use but make string from assets
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
    //read the file and make string
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
    //add story to db
    public void addStoryToDbFromAssets(int pk,String StName,String Story,String Genre,String Author){
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd  'at' HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());

        tb_Story story = new tb_Story();
        story.id = pk;
        story.storyName = StName;
        story.storyText = getTermsStringBase(Story);
        story.genre = Genre;
        story.like = 0;
        story.rate = 0;
        story.version = 1;
        story.markedPlace = 0;
        story.createDate = date ;
        story.author = Author ;

        tb_StoryDataSource dataSource = new tb_StoryDataSource(MainActivity.this);
        dataSource.open();
        dataSource.add(story);
        dataSource.close();
    }
    //check for connecting to server
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


    //get stories from web api and go to story list activity async
    public class LoadMyList extends AsyncTask<Void ,Void,Void> {

        Context _Context;
        ProgressDialog proDialog;
        String apiUrl = "api/StoryTemp";

        public LoadMyList(Context context)
        {
            _Context = context;
        }
        public LoadMyList(Context context , String apiUrl)
        {
            this.apiUrl = apiUrl;
            _Context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            proDialog = new ProgressDialog(_Context);
            proDialog.setTitle("منتظر باشید");
            proDialog.setMessage("Loding...");
            proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //proDialog.setIcon(R.drawable.)
            proDialog.setCancelable(false);
            proDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //get stories list from web api
            WebService webService = new WebService(MainActivity.this, new WebService.OnWebServiceListener() {
                @Override
                public void onDataReceived(String value) {
                    Gson gson = new Gson();
                    StoryListActivity.storyList =  gson.fromJson(value,new TypeToken<List<tb_Story>>() {}.getType());
                    lst2 =  gson.fromJson(value,new TypeToken<List<tb_Story>>() {}.getType());
                }
            });
            //Assign api address
            webService.transmitAsync(apiUrl);
            //add story list to activity to show
            StoryListActivity.storyList =lst2;


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //load storyList Activity
            Intent intent=new Intent(MainActivity.this, StoryListActivity.class);
            startActivity(intent);
            proDialog.dismiss();
        }
    }

}
