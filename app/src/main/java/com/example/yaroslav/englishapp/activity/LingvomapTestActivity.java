package com.example.yaroslav.englishapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaroslav.englishapp.DeveloperKey;
import com.example.yaroslav.englishapp.R;
import com.example.yaroslav.englishapp.controler.QuestionsController;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class LingvomapTestActivity extends Activity implements View.OnClickListener {
    Button btnNext;
    Button btnRed;
    Button btnBlue;
    Button btnGreen;
    Button btnYellow;
    TextView tvText;
    boolean isFirstlyOpened = true;
    TextView tvStatus;

    int CURRENT_TEST;
    QuestionsController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingvomap_test);


        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        btnRed = (Button) findViewById(R.id.btnRed);
        btnRed.setOnClickListener(this);

        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(this);

        btnBlue = (Button) findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(this);

        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnYellow.setOnClickListener(this);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvText = (TextView) findViewById(R.id.tvQuestion);

        CURRENT_TEST = getIntent().getIntExtra("TEST",-1);
        if (CURRENT_TEST == -1) Toast.makeText(this, "Test choise error!", Toast.LENGTH_SHORT).show();
        switch (CURRENT_TEST){
            case 1:
                controller = new QuestionsController(this, "questions-verbs.json");
                break;
            case 2:
                controller = new QuestionsController(this, "questions-ENG.json");
        }
        controller.attachViews(tvText, tvStatus, btnRed, btnGreen, btnBlue, btnYellow);
        controller.toNextQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lingvomap_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_demo) {
            Intent intent = null;
                intent = YouTubeStandalonePlayer.
                        createVideoIntent(this, DeveloperKey.DEVELOPER_KEY,"D3SVDZKipwU",0,true,false);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                controller.toNextQuestion();
                break;
            case R.id.btnRed:
                controller.checkAnswer(btnRed.getText().toString());
                break;
            case R.id.btnBlue:
                controller.checkAnswer(btnBlue.getText().toString());
                break;
            case R.id.btnGreen:
                controller.checkAnswer(btnGreen.getText().toString());
                break;
            case R.id.btnYellow:
                controller.checkAnswer(btnYellow.getText().toString());
                break;
        }

    }
}
