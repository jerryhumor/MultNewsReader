package com.hdu.jerryhumor.multnewsreader.keep.database;

/**
 * Created by hanjianhao on 2017/12/17.
 */

/**
 * 数据库 存放表 版本号 数据库名
 */
public class Database {

    public static int VERSION = 1;
    public static String DB_NAME = "user_favour";

    /**
     * 用户收藏表
     */
    public static class TableFavour{

        public static String TABLE_NAME = "favour";

        public static String ID = "id";                             //id 自增
        public static String TITLE = "title";                       //新闻标题
        public static String SOURCE = "source";                     //新闻源
        public static String NEWS_ID = "news_id";                   //新闻id 访问新闻时用到
        public static String CREATE_TIME = "create_time";           //创建时间

        public static int INDEX_ID = 0;
        public static int INDEX_TITLE = 1;
        public static int INDEX_SOURCE = 2;
        public static int INDEX_NEWS_ID = 3;
        public static int INDEX_CREATE_TIME = 4;

        public static final String CREATE_TABLE =
                "create table " + TABLE_NAME +
                        "(" +
                        ID + " integer primary key autoincrement," +
                        TITLE + " varchar not null," +
                        SOURCE + " integer not null," +
                        NEWS_ID + " integer unique not null," +
                        CREATE_TIME + " integer not null" +
                        ");";
    }

}
