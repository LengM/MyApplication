package com.yng.ming.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bright Lee on 2017/9/19 0019
 */

public class IndexDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "index.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "index_table";

    private static final String ID = "id";
    private static final String MESSAGE = "message";

    public IndexDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID + " text," + MESSAGE + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 插入
     */
    public long insert(String id, String message) {
        /**
         * getWritableDatabase()方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，getWritableDatabase()打开数据库就会出错。
         * getReadableDatabase()方法先以读写方式打开数据库，倘若使用如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库.
         */
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(MESSAGE, message);
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * 查询全部
     */
    public Cursor selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * 模糊查询
     */
    public Cursor searchFuzzy(String searchText) {
        String[] columns = new String[]{ID, MESSAGE};
        String sql = MESSAGE + " LIKE \'%" + searchText + "%\'";
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, columns, sql, null, null, null, null);

    }

    //清空数据
    public void clear() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;
        db.execSQL(sql);
//        resetSequence();
    }

    //重置自增数，私有函数清空数据时调用
    /* private void resetSequence() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "DELETE FROM sqlite_sequence WHERE name = " + "\"" + TABLE_NAME + "\"";
        db.execSQL(sql);
    }*/

}
