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
static ArrayList<Score> getAllScores(){
    db.open();
    ArrayList<Score> data = new ArrayList<>();
    Cursor cursor = db.getAllData();
    while (cursor.moveToNext()){
        String author = cursor.getString(cursor.getColumnIndex(db.COLUMN_AUTHOR));
        String time = cursor.getString(cursor.getColumnIndex(db.COLUMN_TIME));
        data.add(new Score(author, time));
    }
    db.close();
    return data;
}
    void addScore(Score value){
        db.open();
        db.addHScore(value.author, value.time);
        db.close();
    }
}
