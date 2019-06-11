package database.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import database.structure.tb_StoryStructure;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManagement;
import database.table.tb_Story;

public class tb_StoryDataSource {
    private SQLiteDatabase database;
    private DatabaseManagement dbManagement;
    private AliList _AliList;
    private String[] allColumns={
            tb_StoryStructure.colPKStory,
            tb_StoryStructure.colStoryName ,
            tb_StoryStructure.colStory,
            tb_StoryStructure.colAuthor,
            tb_StoryStructure.colCreateDate,
            tb_StoryStructure.colVersion,
            tb_StoryStructure.colMarkedPlace,
            tb_StoryStructure.colGenre,
            tb_StoryStructure.colRate,
            tb_StoryStructure.colLike

    };

    public tb_StoryDataSource(Context context)
    {
        dbManagement=new DatabaseManagement(context);
    }
    public tb_StoryDataSource(Context context, AliList aliList)
    {
        dbManagement=new DatabaseManagement(context);
        _AliList = aliList;
    }

    public void open()
    {
        database = dbManagement.getWritableDatabase();
    }

    public void close()
    {
        dbManagement.close();
        database.close();
    }

    public long queryNumEntries()
    {
        return DatabaseUtils.queryNumEntries(database, tb_StoryStructure.tableName);
    }

    public long add(tb_Story data) {
        ContentValues values = new ContentValues();

        values.put(tb_StoryStructure.colMarkedPlace, data.MarkedPlace);
        values.put(tb_StoryStructure.colPKStory, data.PKStory);
        values.put(tb_StoryStructure.colStory, data.Story);
        values.put(tb_StoryStructure.colAuthor, data.Author);
        values.put(tb_StoryStructure.colCreateDate, data.CreateDate);
        values.put(tb_StoryStructure.colGenre, data.Genre);
        values.put(tb_StoryStructure.colStoryName, data.StoryName);
        values.put(tb_StoryStructure.colVersion, data.Version);
        values.put(tb_StoryStructure.colRate, data.Rate);
        values.put(tb_StoryStructure.colLike, data.Like);


        return database.insert(tb_StoryStructure.tableName, null, values);
    }

    public void update(tb_Story data)
    {
        if(find(data.PKStory)==null)
            return;

        ContentValues values = new ContentValues();

        values.put(tb_StoryStructure.colMarkedPlace, data.MarkedPlace);
        values.put(tb_StoryStructure.colPKStory, data.PKStory);
        values.put(tb_StoryStructure.colStory, data.Story);
        values.put(tb_StoryStructure.colAuthor, data.Author);
        values.put(tb_StoryStructure.colCreateDate, data.CreateDate);
        values.put(tb_StoryStructure.colGenre, data.Genre);
        values.put(tb_StoryStructure.colVersion, data.Version);
        values.put(tb_StoryStructure.colStoryName, data.StoryName);
        values.put(tb_StoryStructure.colRate, data.Rate);
        values.put(tb_StoryStructure.colLike, data.Like);

        database.update(tb_StoryStructure.tableName,
                values,
                tb_StoryStructure.colPKStory+"="+data.PKStory
                ,null);

    }

    public void deleteAll()
    {
        database.delete(tb_StoryStructure.tableName, null,null);
    }

    public void delete(int id)
    {

        database.delete(tb_StoryStructure.tableName,
                tb_StoryStructure.colPKStory +"=" + id,
                null);
    }

    public void delete(String phone)
    {
        //Phone='0918'
        database.delete(tb_StoryStructure.tableName,
                tb_StoryStructure.colStoryName +"='" + phone +"'",
                null);
    }

    public tb_Story find(int id)
    {
        Cursor cursor=database.query(tb_StoryStructure.tableName,
                allColumns,
                tb_StoryStructure.colPKStory+"="+id,
                null,null,null,null,null);

        if(cursor.getCount()==0)
            return null;

        cursor.moveToFirst();

        tb_Story data = convertToRecord(cursor);

        cursor.close();

        return data;
    }

    public tb_Story find(String Name)
    {
        Cursor cursor=database.query(tb_StoryStructure.tableName,
                allColumns,
                tb_StoryStructure.colStoryName+"='"+Name+"'",
                null,null,null,null,null);

        if(cursor.getCount()==0)
            return null;

        cursor.moveToFirst();

        tb_Story data = convertToRecord(cursor);

        cursor.close();

        return data;
    }


    public List<tb_Story> getList()
    {
        if(_AliList != null) {
            return  _AliList.aliList();}
        else {
            List<tb_Story> lstData = new ArrayList<>();

            Cursor cursor = database.query(tb_StoryStructure.tableName,
                    allColumns,
                    null,
                    null, null, null,
                    tb_StoryStructure.colPKStory + " DESC");

            if (cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                tb_Story data = convertToRecord(cursor);
                lstData.add(data);
                cursor.moveToNext();
            }

            cursor.close();
            return lstData;
        }
    }
    public List<tb_Story> getListByGenre(String Genre)
    {
        if(_AliList != null) {
            return  _AliList.aliList();}
        else {
            List<tb_Story> lstData = new ArrayList<>();

            Cursor cursor = database.query(tb_StoryStructure.tableName,
                    allColumns,
                    tb_StoryStructure.colGenre+ "= '"+Genre+"'" ,
                    null, null, null,
                    tb_StoryStructure.colPKStory + " DESC");

            if (cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                tb_Story data = convertToRecord(cursor);
                lstData.add(data);
                cursor.moveToNext();
            }

            cursor.close();
            return lstData;
        }
    }
    public List<tb_Story> getLikeList()
    {
            List<tb_Story> lstData = new ArrayList<>();
            Cursor cursor = database.query(tb_StoryStructure.tableName,
                    allColumns,
                    null,
                    null, null, null,
                    tb_StoryStructure.colLike + " DESC");

            if (cursor.getCount() == 0)
                return null;

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                tb_Story data = convertToRecord(cursor);
                lstData.add(data);
                cursor.moveToNext();
            }

            cursor.close();
            return lstData;
    }

    public List<tb_Story> getRateList()
    {
        List<tb_Story> lstData = new ArrayList<>();
        Cursor cursor = database.query(tb_StoryStructure.tableName,
                allColumns,
                null,
                null, null, null,
                tb_StoryStructure.colRate + " DESC");

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            tb_Story data = convertToRecord(cursor);
            lstData.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return lstData;
    }
    public List<tb_Story> getNewList()
    {
        List<tb_Story> lstData = new ArrayList<>();
        Cursor cursor = database.query(tb_StoryStructure.tableName,
                allColumns,
                null,
                null, null, null,
                tb_StoryStructure.colPKStory + " DESC");

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            tb_Story data = convertToRecord(cursor);
            lstData.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return lstData;
    }
    public List<String> getGenreList()
    {
         String[] genre = {tb_StoryStructure.colGenre};
        List<String> lstData = new ArrayList<>();
        Cursor cursor = database.query(tb_StoryStructure.tableName,
                genre,
                null,
                null, tb_StoryStructure.colGenre, null,
                null);

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data;
            data = cursor.getString(0);
            lstData.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return lstData;
    }

    private tb_Story convertToRecord(Cursor cursor)
    {
        tb_Story data=new tb_Story();

        data.PKStory = cursor.getInt(0);
        data.StoryName=cursor.getString(1);
        data.Story=cursor.getString(2);
        data.Author=cursor.getString(3);
        data.CreateDate=cursor.getString(4);
        data.Version=cursor.getInt(5);
        data.MarkedPlace=cursor.getInt(6);
        data.Genre=cursor.getString(7);
        data.Rate=cursor.getInt(8);
        data.Like=cursor.getInt(9);

        return data;
    }


    public interface AliList{
        public List<tb_Story> aliList();
    }
}
