package com.yahoo.mystorye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.security.PublicKey;

import database.table.tb_Story;

public class StoryActivity extends AppCompatActivity {
    TextView txtAuthor,txtStoryName,txtGenre,txtStory;
    public static OnStoryListener onStoryListener;
    public static tb_Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        txtAuthor = findViewById(R.id.txtAuthor);
        txtGenre =findViewById(R.id.txtGenre);
        txtStoryName =findViewById(R.id.txtStoryName);
        txtStory =findViewById(R.id.txtStory);

        //story = onStoryListener.onStoryListener();
        txtStory.setText(story.Story);
        txtGenre.setText(story.Genre);
        txtAuthor.setText(story.Author);
        txtStoryName.setText(story.StoryName);


    }
public interface OnStoryListener{
        public tb_Story onStoryListener();
}
}
