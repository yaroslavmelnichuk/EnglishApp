package com.example.yaroslav.englishapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    Context context;
    AlertDialog dialog;
    AlertDialog.Builder builder;
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

        builder = new AlertDialog.Builder(this);

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
                if(controller.isEndOfTest()){
                    showEndDialog();
                }
                controller.toNextQuestion();
                break;
            case R.id.btnRed:
                if(controller.isEndOfTest()){
                    showEndDialog();
                }
                controller.checkAnswer(btnRed.getText().toString());
                break;
            case R.id.btnBlue:
                if(controller.isEndOfTest()){
                    showEndDialog();
                }
                controller.checkAnswer(btnBlue.getText().toString());
                break;
            case R.id.btnGreen:
                if(controller.isEndOfTest()){
                    showEndDialog();
                }
                controller.checkAnswer(btnGreen.getText().toString());
                break;
            case R.id.btnYellow:
                if(controller.isEndOfTest()){
                    showEndDialog();
                }
                controller.checkAnswer(btnYellow.getText().toString());
                break;
        }

    }
    public void showEndDialog(){
        String text;
        if(!controller.counter.isPassedTest()){
            text = "Успішно пройдено!";
        } else {
            text = "Пройдіть ще раз!";
        }
        builder.setMessage(text);
        builder.setTitle("Результат!");
        builder.setPositiveButton("Повторити", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Завершити", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                finish();
            }
        });

        dialog = builder.create();
        dialog.show();
    }
}
