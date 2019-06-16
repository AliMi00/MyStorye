package com.yahoo.mystorye;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManagement;

import database.table.tb_Story;

public class StaticStoryData {


    public static List<tb_Story> insertStory(){
        List<tb_Story> stories= new ArrayList<>();
        if (DatabaseManagement.isFirstTime)
        {

            tb_Story story = new tb_Story();
            story.PKStory = 1;
            story.StoryName = "داستاننننننننن";
            story.Story="دانسااا طولانی ";
            story.Genre = "عاشقانه";
            story.Like = 0;
            story.Rate = 0;
            story.Version = 1;
            story.MarkedPlace = 0;
            story.CreateDate = "1397/01/01" ;
            story.Author = "هما " ;
            stories.add(story);


            tb_Story story1 = new tb_Story();
            story1.PKStory = 2;
            story1.StoryName = "name";
            story1.Story = "dastan 2";
            story1.Genre = "genre";
            story1.Like = 0;
            story1.Rate = 0;
            story1.Version = 1;
            story1.MarkedPlace = 0;
            story1.CreateDate = "date" ;
            story1.Author = "author" ;
            stories.add(story1);

            tb_Story story2 = new tb_Story();
            story2.PKStory = 3;
            story2.StoryName = "name";
            story2.Story = "";
            story2.Genre = "genre";
            story2.Like = 0;
            story2.Rate = 0;
            story2.Version = 1;
            story2.MarkedPlace = 0;
            story2.CreateDate = "date" ;
            story2.Author = "author" ;
            stories.add(story2);

            tb_Story story3 = new tb_Story();
            story3.PKStory = 3;
            story3.StoryName = "name";
            story3.Story = "سیبس";
            story3.Genre = "genre";
            story3.Like = 0;
            story3.Rate = 0;
            story3.Version = 1;
            story3.MarkedPlace = 0;
            story3.CreateDate = "date" ;
            story3.Author = "author" ;
            stories.add(story3);


            DatabaseManagement.isFirstTime =false;
        }
        return stories;
    }

}
