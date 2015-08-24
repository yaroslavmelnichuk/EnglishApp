package com.example.yaroslav.englishapp.score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by yaroslav on 06.06.15.
 */public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    public static final String DB_TABLE_SCORES = "highscore";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TIME = "time";
    private static final String DB_CREATE_SCORES =
            "create table " + DB_TABLE_SCORES + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_AUTHOR + " text, " +
                    COLUMN_TIME + " text" +
                    ");";
    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }
    public void delRec(long id) {
        mDB.delete(DB_TABLE_SCORES, COLUMN_ID + " = " + id, null);
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE_SCORES, null, null, null, null, null, null);
    }
    public ArrayList<String> getItemsList(String table, String column){
        ArrayList<String> tmp = new ArrayList<>();
        Cursor cursor = mDB.query(table,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String value = cursor.getString(cursor.getColumnIndex(column));
            tmp.add(value);
        }
        return tmp;
    }
    public void addHScore(String author, String time) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_TIME, time);
        mDB.insert(DB_TABLE_SCORES, null, cv);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }
        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_SCORES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}