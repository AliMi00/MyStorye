package com.yahoo.mystorye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class GenreListActivity extends AppCompatActivity {

    public static GenreListActivity.OnListListener ListListener;
    List<String> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        //list of Type here is Genre
        lst=ListListener.onListListenerString();

        //todo Change and set new view for list
        ListView_AdapterString MyAdapter = new ListView_AdapterString(GenreListActivity.this,
                lst,
                R.layout.view_story_list_template,
                clickListener);
        ListView lstStory =findViewById(R.id.lstGenre);
        lstStory.setAdapter(MyAdapter);


    }
    ListView_AdapterString.OnClickListener clickListener = new ListView_AdapterString.OnClickListener() {
        @Override
        public void onClickViewInfo(final String info) {


            //go to the Story Activity
            StoryListActivity.OnListListener Lst = new StoryListActivity.OnListListener() {
                @Override
                public List<tb_Story> onListListener() {
                    tb_StoryDataSource dataSource = new tb_StoryDataSource(GenreListActivity.this);
                    dataSource.open();
                    List<tb_Story> lst = dataSource.getListByGenre(info);
                    dataSource.close();
                    return lst;
                }

            };
            StoryListActivity.ListListener =Lst;
            Intent intent=new Intent(GenreListActivity.this, StoryListActivity.class);
            startActivity(intent);
            finish();


        }
    };
    interface OnListListener{
        List<String> onListListenerString();

    }


}
class ListView_AdapterString extends BaseAdapter {
    private Context _context;
    private List<String> lstData;
    private int _templateId;
    private LayoutInflater inflater = null;

    private OnClickListener _onClickListener;

    public interface OnClickListener {

        void onClickViewInfo(final String info);
    }

    public ListView_AdapterString(Context context, List<String> lst,
                            int templateId,
                            OnClickListener onClickListener) {
        _context = context;
        lstData = lst;
        _templateId = templateId;
        _onClickListener = onClickListener;
        inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView = inflater.inflate(_templateId, null);

        try {

            TextView txtStoryName = rowView.findViewById(R.id.txtStoryName);

            TextView txtGenre = rowView.findViewById(R.id.txtGenre);
            TextView txtLike = rowView.findViewById(R.id.txtLike);
            TextView txtRate = rowView.findViewById(R.id.txtRate);

            txtGenre.setText("");
            txtLike.setText("");
            txtRate.setText("");
            txtStoryName.setText(String.format("%s ",
                    lstData.get(position)));


        } catch (Exception e) {

        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_onClickListener != null)
                    _onClickListener.onClickViewInfo(lstData.get(position));

            }
        });

        return rowView;
    }
}