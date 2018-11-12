package com.example.ducmanh.app1.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ducmanh.app1.model.News;

import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "newsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Saves
    private static final String TABLE_NAME = "save";
    private static final String TABLE_ID = "id";
    private static final String TABLE_TITLE = "title";
    private static final String TABLE_DESC = "desc";
    private static final String TABLE_IMG = "img";
    private static final String TABLE_PUBDATE = "pubdate";
    private static final String TABLE_LINK = "link";
    private static final String TABLE_TYPE = "type";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_TITLE + " NVARCHAR(100)," +
                TABLE_DESC + " NVARCHAR(100)," +
                TABLE_IMG + " NVARCHAR(100)," +
                TABLE_PUBDATE + " NVARCHAR(100)," +
                TABLE_LINK + " NVARCHAR(100)," +
                TABLE_TYPE + " NVARCHAR(100)" +
                ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void delete_byID(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, TABLE_ID, null);
    }

    public void addNews(News news, String type){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TABLE_TITLE, news.getTitle());
            values.put(TABLE_DESC, news.getDesc());
            values.put(TABLE_IMG, news.getImg());
            values.put(TABLE_PUBDATE, news.getPubDate());
            values.put(TABLE_LINK, news.getLink());
            values.put(TABLE_TYPE, type);

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<News> getAllNew() {
        ArrayList<News> news = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME, null);
        try {
            while (cursor.moveToNext()){
                news.add(new News(
                        cursor.getString(cursor.getColumnIndex(TABLE_ID)),
                        cursor.getString(cursor.getColumnIndex(TABLE_TITLE)),
                        cursor.getString(cursor.getColumnIndex(TABLE_DESC)),
                        cursor.getString(cursor.getColumnIndex(TABLE_IMG)),
                        cursor.getString(cursor.getColumnIndex(TABLE_PUBDATE)),
                        cursor.getString(cursor.getColumnIndex(TABLE_LINK)),
                        cursor.getString(cursor.getColumnIndex(TABLE_TYPE))
                ));
            }

        } catch (Exception e) {
           e.printStackTrace();
        }
        return news;
    }

}
