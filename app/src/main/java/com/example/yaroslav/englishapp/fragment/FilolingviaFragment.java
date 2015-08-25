package com.example.yaroslav.englishapp.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.yaroslav.englishapp.MyTimer;
import com.example.yaroslav.englishapp.R;
import com.example.yaroslav.englishapp.activity.HightScoreActivity;
import com.example.yaroslav.englishapp.controller.LessonController;
import com.example.yaroslav.englishapp.score.Score;
import com.example.yaroslav.englishapp.score.ScoreManager;

/**
 * Created by yaroslav on 13.08.15.
 */
public class FilolingviaFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    Button btnNext;
    Context context;
    ImageView ivNoun1;
    ImageView ivNoun2;
    ImageView ivObject1;
    ImageView ivObject2;
    ImageView ivVerb;
    TableLayout tableLayout;
    TextView tvTime;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    ScoreManager scoreManager;
    boolean isStartPressed = false;
    MyTimer myTimer;
    int LESSON_TYPE = -1;
    com.example.yaroslav.englishapp.controller.LessonController lessonController;

    public FilolingviaFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filolingvia_fragment, container, false);
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String lessonName = getResources().getStringArray(R.array.Filolingvia_lessons)[i];
        context = getActivity().getApplicationContext();
        LESSON_TYPE = i;
        initViews(rootView);
        myTimer = new MyTimer(tvTime);

        btnNext = (Button) rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        scoreManager = new ScoreManager(context);

        getActivity().setTitle(lessonName);
        lessonController = new LessonController(LESSON_TYPE,tableLayout,context);
        lessonController.attachViews(ivNoun1, ivNoun2, ivObject1, ivObject2, ivVerb);
        lessonController.initTable();

        builder = new AlertDialog.Builder(getActivity());
        scoreManager = new ScoreManager(context);

        return rootView;
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.btnNext:
            if(!isStartPressed) {
                btnNext.setText("Next");
                isStartPressed = true;
                lessonController.startTest();
                myTimer.startTimer();
                break;
            }
            if(lessonController.isLastItem()){
           showEndDialog();
           myTimer.resetTimer();
             isStartPressed = false;
             btnNext.setText("Start");
            }
            else
            lessonController.toNext();
            break;
    }
    }
    public void initViews(View root){
        ivNoun1 = (ImageView) root.findViewById(R.id.ivNounLeft);
        ivNoun1.setOnClickListener(this);

        ivNoun2 = (ImageView) root.findViewById(R.id.ivNounRight);
        ivNoun2.setOnClickListener(this);

        ivObject1 = (ImageView) root.findViewById(R.id.ivSubjectLeft);
        ivObject1.setOnClickListener(this);

        ivObject2 = (ImageView) root.findViewById(R.id.ivSubjectRight);
        ivObject2.setOnClickListener(this);

        ivVerb = (ImageView) root.findViewById(R.id.ivVerb);
        ivVerb.setOnClickListener(this);

        tvTime = (TextView) root.findViewById(R.id.tvTimer);

        tableLayout = (TableLayout) root.findViewById(R.id.table);

        View  header = LayoutInflater.from(context).inflate(R.layout.table_header,null, false);
        // add it to Layout
        tableLayout.addView(header);
    }

void showEndDialog(){
    final String timeText = tvTime.getText().toString();
    myTimer.stopTimer();
    builder.setMessage(timeText);
    builder.setTitle("Your Time!");
    builder.setPositiveButton("High Scores", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User clicked OK button
            scoreManager.addScore(new Score(timeText));
              Intent intent = new Intent(context, HightScoreActivity.class);
            startActivity(intent);

        }
    });
    builder.setNegativeButton("Repeat", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
            scoreManager.addScore(new Score(timeText));
            lessonController.restartTest();
        }
    });

    dialog = builder.create();
    dialog.show();
    lessonController.restartTest();
}

}