package database.structure;

public class tb_StoryStructure {

    public static final String tableName = "tb_Story";


	public static final String colId = "id";
    public static final String colStoryName = "storyName";
    public static final String colStoryText = "storyText";
    public static final String colAuthor = "author";
    public static final String colCreateDate = "createDate";
    public static final String colVersion = "version";
    public static final String colMarkedPlace = "markedPlace";
    public static String colGenre = "genre";
    public static final String colRate = "rate";
    public static final String colLike = "like";

    // "create table  tb_Story (
    public static final String createTableQuery = "create table " + tableName + "(" +
            colId + " integer primary key ," +
            colStoryName + " text," +
            colStoryText + " text," +
            colAuthor + " text," +
            colCreateDate + " text," +
            colVersion + " integer," +
            colMarkedPlace + " integer," +
            colGenre + " text," +
            colRate + " integer," +
            colLike + " integer" +


            ")";
}
