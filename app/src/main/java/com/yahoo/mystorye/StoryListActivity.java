package com.yahoo.mystorye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class StoryListActivity extends AppCompatActivity {

    public static OnListListener ListListener;

    ListView lstStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        lstStory=findViewById(R.id.lstStory);

        LoadMyList loadList = new LoadMyList(StoryListActivity.this,ListListener,clickListener);
        loadList.execute();
    }
    ListView_Adapter.OnClickListener clickListener = new ListView_Adapter.OnClickListener() {
        @Override
        public void onClickViewInfo( int PKStory) {

            //todo set to go to Story Activity
            //go to the Story Activity
            Intent intent = new Intent(StoryListActivity.this,TestStoryActivity.class);

            intent.putExtra("PK",PKStory);
            //StoryActivity.PKStory = PKStory;
            startActivity(intent);


        }
    };
    //interface for get list
    interface OnListListener{
        public List<tb_Story> onListListener();
    }
    public class LoadMyList extends AsyncTask<Void ,Void,Void>{

        Context _Context;
        StoryListActivity.OnListListener _onListListener;
        ListView_Adapter.OnClickListener _clickListener;
        List<tb_Story> lst;
        ListView_Adapter MyAdapter;
        ProgressDialog proDialog;

        public LoadMyList(Context context,
                           StoryListActivity.OnListListener onListListener,
                           ListView_Adapter.OnClickListener clickListener) {
            _Context = context;
            _onListListener = onListListener;
            _clickListener = clickListener;
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
            try {
                if(_onListListener.onListListener()!=null)
                    lst = _onListListener.onListListener();

                MyAdapter = new ListView_Adapter(_Context,
                        lst,
                        R.layout.view_story_list_template,
                        _clickListener);
                lstStory =findViewById(R.id.lstStory);
                return null;
            }
            catch (Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lstStory.setAdapter(MyAdapter);
            proDialog.dismiss();
        }
    }


}

