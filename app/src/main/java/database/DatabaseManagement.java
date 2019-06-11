package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import database.structure.tb_StoryStructure;

public class DatabaseManagement extends SQLiteOpenHelper {

    public static final String databaseName="MyStory.db";
    public static final int databaseVersion=3;

    public DatabaseManagement(Context  context)
    {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb_StoryStructure.createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
