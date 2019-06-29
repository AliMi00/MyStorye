package com.yahoo.mystorye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import database.datasource.tb_StoryDataSource;
import database.table.tb_Story;

public class TestStoryActivity extends AppCompatActivity {

    TextView txtStoryName,txtGenre,txtAuthor;
    Button btnGoPage,btnMax,btnMin;
    ListView lstStory;
    EditText edtPageNumber;
    tb_Story FullStory;
    public int PKStory;
    ListStory_Adapter MyAdapter;
    float textSize = 14;
    int PageNum;

    List<String> StoryPages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_story);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        txtStoryName = findViewById(R.id.txtStoryName);
        txtGenre = findViewById(R.id.txtGenre);
        txtAuthor = findViewById(R.id.txtauthor);
        edtPageNumber = findViewById(R.id.edtPageNumber);

        btnGoPage = findViewById(R.id.btnGoPage);
        btnGoPage.setOnClickListener(btnGoPageOnClickListener);

        btnMax = findViewById(R.id.btnMax);
        btnMax.setOnClickListener(btnMaxOnClickListener);

        btnMin = findViewById(R.id.btnMin);
        btnMin.setOnClickListener(btnMinOnClickListener);


        //load list
        Intent intent = getIntent();
        PKStory =intent.getIntExtra("PK",0);

        tb_StoryDataSource dataSource = new tb_StoryDataSource(TestStoryActivity.this);
        dataSource.open();
        FullStory = dataSource.find(PKStory);
        dataSource.close();
        PageNum=FullStory.MarkedPlace;

        StoryPages = getTermsString(FullStory.Story);

        MyAdapter = new ListStory_Adapter(TestStoryActivity.this,
                StoryPages,
                R.layout.view_story_template,
                clickListener);
        lstStory =findViewById(R.id.StoryTestList);
        lstStory.setAdapter(MyAdapter);
        lstStory.setSelection(PageNum);

        txtStoryName.setText(FullStory.StoryName);
        txtAuthor.setText(FullStory.Author);
        txtGenre.setText(FullStory.Genre);

    }

    @Override
    protected void onPause() {
        super.onPause();
        setPageNumber(lstStory.getLastVisiblePosition());
    }

    @Override
    protected void onStop() {
        super.onStop();
        setPageNumber(lstStory.getLastVisiblePosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setPageNumber(lstStory.getLastVisiblePosition());
    }

    View.OnClickListener btnGoPageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lstStory.setSelection(Integer.valueOf(edtPageNumber.getText().toString())-1);
            hideSoftKeyBoardOnTabClicked(v);
            setPageNumber(Integer.valueOf(edtPageNumber.getText().toString()));
            edtPageNumber.setText("");
        }
    };

    ListStory_Adapter.OnClickListener clickListener = new ListStory_Adapter.OnClickListener() {
        @Override
        public void onClickViewInfo(int PageStory) {
            setPageNumber(PageStory);
        }
    };
    View.OnClickListener btnMaxOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setPageNumber(lstStory.getLastVisiblePosition());
            textSize=textSize+2;
            MyAdapter.size = textSize;
            lstStory.setAdapter(MyAdapter);
            lstStory.setSelection(PageNum);



        }
    };
    View.OnClickListener btnMinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(textSize>3){
                setPageNumber(lstStory.getLastVisiblePosition());
                textSize=textSize-2;
                MyAdapter.size = textSize;
                lstStory.setAdapter(MyAdapter);
                lstStory.setSelection(PageNum);

            }
        }
    };

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
                    termsString.append("صفحه "+String.valueOf(pagesStory.size()+1)+ "\n");
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

    private void hideSoftKeyBoardOnTabClicked(View v) {
        if (v != null && TestStoryActivity.this != null) {
            InputMethodManager imm = (InputMethodManager) TestStoryActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    private void setPageNumber(int pageNumber){

        PageNum = pageNumber;
        tb_StoryDataSource dataSource = new tb_StoryDataSource(TestStoryActivity.this);
        dataSource.open();
        dataSource.updatePage(PKStory,pageNumber);
        dataSource.close();
    }

}
class ListStory_Adapter extends BaseAdapter
{
    private Context _context;
    private List<String> lstData;
    private int _templateId;
    private LayoutInflater inflater=null;
    TextView StoryText;
    public static float size=14;

    private OnClickListener _onClickListener;

    public interface OnClickListener{

        public void onClickViewInfo(int Page);
    }

    public ListStory_Adapter(Context context, List<String> lst,
                            int templateId,
                            OnClickListener onClickListener)
    {
        _context=context;
        lstData=lst;
        _templateId=templateId;
        _onClickListener=onClickListener;
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
        final View rowView;

        rowView=inflater.inflate(_templateId, null);

        try {

            StoryText = rowView.findViewById(R.id.StoryText);
            StoryText.setText(lstData.get(position));
            ChangeSize(size);

        }
        catch (Exception e){

        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_onClickListener!=null)
                    _onClickListener.onClickViewInfo( position);

            }
        });


        return rowView;
    }
    public void ChangeSize(float size){
        StoryText.setTextSize(size);
    }

}