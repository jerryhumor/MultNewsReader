package com.hdu.jerryhumor.multnewsreader.keep.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hdu.jerryhumor.multnewsreader.keep.bean.KeepItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hanjianhao on 2017/12/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, Database.DB_NAME, factory, Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Database.TableFavour.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public interface ICreateBean<T>{
        T createBean(Cursor cursor);
    }

    /**
     * 从数据库中查找
     * @param db                        sqlite数据库
     * @param tableName                 表名
     * @param selection                 条件语句
     * @param selectionArgs             条件参数
     * @param orderBy                   排列方式
     * @param createBean                接口 生成bean的方式
     * @param <T>                       bean的类型
     * @return
     */
    private <T>List<T> queryFrom(SQLiteDatabase db, String tableName, String selection, String[] selectionArgs, String orderBy, ICreateBean<T> createBean){
        List<T> beanList = new ArrayList<>();
        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy);
        if (cursor.getCount() != 0 && cursor.moveToFirst()){
            do {
                beanList.add(createBean.createBean(cursor));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return beanList;
    }

    /**
     * 获取用户收藏列表
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    public List<KeepItem> getKeepListBy(String selection, String[] selectionArgs, String orderBy){
        SQLiteDatabase db = getReadableDatabase();
        return queryFrom(db, Database.TableFavour.TABLE_NAME, selection, selectionArgs, orderBy, new ICreateBean<KeepItem>() {
            @Override
            public KeepItem createBean(Cursor cursor) {
                int id = cursor.getInt(Database.TableFavour.INDEX_ID);
                String title = cursor.getString(Database.TableFavour.INDEX_TITLE);
                int source = cursor.getInt(Database.TableFavour.INDEX_SOURCE);
                int newsId = cursor.getInt(Database.TableFavour.INDEX_NEWS_ID);
                long createTime = cursor.getLong(Database.TableFavour.INDEX_CREATE_TIME);
                return new KeepItem(id, title, source, newsId, createTime);
            }
        });
    }

    /**
     * 插入用户收藏数据 创建时间自动生成
     * @param title
     * @param source
     * @param newsId
     * @return
     */
    public long insertKeepItem(final String title, final int source, final int newsId){
        ContentValues values = new ContentValues();
        values.put(Database.TableFavour.TITLE, title);
        values.put(Database.TableFavour.SOURCE, source);
        values.put(Database.TableFavour.NEWS_ID, newsId);
        values.put(Database.TableFavour.CREATE_TIME, new Date().getTime() / 1000);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(Database.TableFavour.TABLE_NAME, null, values);
    }

    public long deleteKeepItem(final int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(Database.TableFavour.TABLE_NAME, "id=?", new String[]{id+""});
    }

}
