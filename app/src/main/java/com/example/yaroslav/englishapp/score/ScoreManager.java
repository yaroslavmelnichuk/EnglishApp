package com.example.yaroslav.englishapp.score;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by yaroslav on 15.07.15.
 */
public  class ScoreManager {
    private static DB db;
    Context context;
    public ScoreManager(Context cntx){
        context = cntx;
        db = new DB(context);
    }
public  ArrayList<String> getAllTimes(){
    db.open();
    ArrayList<String> data = new ArrayList<>();
    Cursor cursor = db.getAllData();
    while (cursor.moveToNext()){
        String time = cursor.getString(cursor.getColumnIndex(db.COLUMN_TIME));
        data.add(time);
    }
    db.close();
    return data;
}
    public void addScore(Score value){
        db.open();
        db.addHScore(value.time);
        db.close();
    }
}
