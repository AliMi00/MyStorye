package database.structure;

public class tb_StoryStructure {

    public static final String tableName = "tb_Story";


	public static final String colPKStory = "PK_Story";
    public static final String colStoryName = "StoryName";
    public static final String colStory = "Story";
    public static final String colAuthor = "Author";
    public static final String colCreateDate = "CreateDate";
    public static final String colVersion = "Version";
    public static final String colMarkedPlace = "MarkedPlace";
    public static String colGenre = "Genre";
    public static final String colRate = "Rate";
    public static final String colLike = "Like";

    // "create table  tb_Story (
    public static final String createTableQuery = "create table " + tableName + "(" +
            colPKStory + " integer primary key ," +
            colStoryName + " text," +
            colStory + " text," +
            colAuthor + " text," +
            colCreateDate + " text," +
            colVersion + " integer," +
            colMarkedPlace + " integer," +
            colGenre + " text," +
            colRate + " integer," +
            colLike + " integer" +


            ")";
}
