package com.example.yaroslav.englishapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yaroslav.englishapp.R;
import com.example.yaroslav.englishapp.score.ScoreManager;

import java.util.ArrayList;
import java.util.Collections;

public class HightScoreActivity extends Activity {
    ListView lvTimes;
    ScoreManager scoreManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight_score);
        scoreManager = new ScoreManager(this);

        lvTimes = (ListView)findViewById(R.id.lvTimes);

        ArrayList<String> times = scoreManager.getAllTimes();
        Collections.sort(times);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_items_hs, times);
        lvTimes.setAdapter(adapter);
    }

}
