package com.example.yaroslav.englishapp.model;

import android.content.Context;
import android.content.Intent;

import com.example.yaroslav.englishapp.activity.LingvomapTestActivity;

/**
 * Created by yaroslav on 11.08.15.
 */
public class Lesson {
   public String name;
    public String videoId;
    public  Intent testIntent;
    final int TEST_ID;
    Context context;
    public Lesson(Context context, String name, String video, int test){
        this.name = name;
        this.videoId = video;
        this.TEST_ID = test;
        this.context = context;
        testIntent = new Intent(context, LingvomapTestActivity.class);
        testIntent.putExtra("TEST",TEST_ID);
    }
    public void  startTest(){
        context.startActivity(testIntent);
    }
}
