package com.example.yaroslav.englishapp.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.yaroslav.englishapp.Config;
import com.example.yaroslav.englishapp.MyTimer;
import com.example.yaroslav.englishapp.R;

import java.util.Random;

/**
 * Created by yaroslav on 14.08.15.
 */
public class LessonController {
    private final int [] NOUNS = {R.drawable.she, R.drawable.they, R.drawable.they2,
            R.drawable.he, R.drawable.noun_we, R.drawable.noun_you};
    private final int [] VERBS = {R.drawable.bake, R.drawable.verb_make, R.drawable.verb_put};
    private final int [] SUBJECTS = {R.drawable.pie, R.drawable.pizza, R.drawable.potato,
            R.drawable.cake, R.drawable.fish};

     int ROW_COUNT = 9;
     int CELL_COUNT = 3;
     int CURRENT_CELL = 1;
     int CURRENT_ROW = 1;
     int PREV_ROW = 1;
     int PREV_CELL = 1;
     int DYNAMIC_INDEX = 0;
     int INDEX = 0;
    boolean IS_LAST = false;
     Random random;
     int LESSON_TYPE;
    TableLayout tableLayout;
    Context context;
    MyTimer myTimer;

    ImageView ivNounLeft, ivNounRight, ivVerb, ivObjectLeft, ivObjectRight;

    public LessonController(int type, TableLayout tableLayout, Context context){
        LESSON_TYPE = type;
        this.tableLayout = tableLayout;
        this.context = context;
        random = new Random();
    }
    public void attachViews(ImageView... views){
        ivNounLeft = views[0];
        ivNounRight = views[1];

        ivObjectLeft = views[2];
        ivObjectRight = views[3];

        ivVerb = views[4];
    }
    public void changeImages(){
        ivNounLeft.setImageResource(NOUNS[random.nextInt(NOUNS.length)]);
        ivNounRight.setImageResource(NOUNS[random.nextInt(NOUNS.length)]);

        ivObjectLeft.setImageResource(SUBJECTS[random.nextInt(SUBJECTS.length)]);
        ivObjectRight.setImageResource(SUBJECTS[random.nextInt(SUBJECTS.length)]);

        ivVerb.setImageResource(VERBS[random.nextInt(VERBS.length)]);
    }

    public void startTest(){
     markCell(CURRENT_ROW, CURRENT_CELL);
        reIndexing();
    }

    void markCell(int r, int c){
        if(LESSON_TYPE == 4){
        TableRow row = (TableRow)tableLayout.getChildAt(r);
        row.getChildAt(c).setBackgroundResource(Config.LESSON_CURRENT_STEP_BORDERS[INDEX]);}
        else {
            TableRow row = (TableRow)tableLayout.getChildAt(r);
            row.getChildAt(c).setBackgroundResource(Config.LESSON_CURRENT_STEP_BORDERS[LESSON_TYPE]);
        }
    }
    void visit(int r, int c){
        TableRow row = (TableRow)tableLayout.getChildAt(r);
        row.getChildAt(c).setBackgroundColor(Color.parseColor("#dddddd"));
        //  Toast.makeText(getApplicationContext(),"R = " + r + " C = " + c,Toast.LENGTH_SHORT).show();
    }

    public void reIndexing(){
        CURRENT_CELL++;
    if(CURRENT_CELL == CELL_COUNT) {
        CURRENT_ROW++;
        CURRENT_CELL = 1;
        if(INDEX < 3){
            INDEX++;
        }else {INDEX = 0;}
        changeImages();
    }
    }

    public void toNext(){
        visit(PREV_ROW, PREV_CELL);
         markCell(CURRENT_ROW, CURRENT_CELL);
        PREV_CELL = CURRENT_CELL;
        PREV_ROW = CURRENT_ROW;
        reIndexing();
    }
    public void initTable(){
        if(LESSON_TYPE == 4){
            for(int i = 0; i < 8;i++){
                View itemView = LayoutInflater.from(context).inflate(R.layout.row,null, false);
                TableRow view = (TableRow)itemView;
                view.setBackgroundColor(context.getResources().getColor(Config.APP_COLORS[DYNAMIC_INDEX]));
                TextView tv = (TextView)view.getChildAt(0);
                tv.setText(Config.TRAINER_ABBR[DYNAMIC_INDEX]);
                // add it to Layout
                tableLayout.addView(itemView);
                if(DYNAMIC_INDEX < 3){
                    DYNAMIC_INDEX++;
                }else {DYNAMIC_INDEX = 0;}
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                View itemView = LayoutInflater.from(context).inflate(R.layout.row, null, false);
                TableRow view = (TableRow) itemView;
                view.setBackgroundColor(context.getResources().getColor(Config.APP_COLORS[LESSON_TYPE]));
                TextView tv = (TextView) view.getChildAt(0);
                tv.setText(Config.TRAINER_ABBR[LESSON_TYPE]);
                // add it to Layout
                tableLayout.addView(itemView);
            }
        }
    }
    public boolean isLastItem(){
        if(PREV_ROW == ROW_COUNT - 1 && PREV_CELL == CELL_COUNT - 1){
            IS_LAST = true;
        }
     return IS_LAST;
    }
   public  void restartTest(){
        CURRENT_CELL  = 1;
        CURRENT_ROW = 1;
        PREV_CELL = 1;
        PREV_ROW = 1;
        DYNAMIC_INDEX = 0;
        INDEX = 0;
        IS_LAST = false;

       if(LESSON_TYPE == 4){
           for(int i = 1; i < 9;i++){
               TableRow row = (TableRow)tableLayout.getChildAt(i);
               for(int j = 1; j < 3; j++){
                   row.getChildAt(j).setBackgroundColor(context.getResources().getColor(Config.APP_COLORS[DYNAMIC_INDEX]));
               }
               if(DYNAMIC_INDEX < 3){
                   DYNAMIC_INDEX++;
               }else {DYNAMIC_INDEX = 0;}
           }
       }
       else {
           for (int i = 0; i < 8; i++) {
               View itemView = LayoutInflater.from(context).inflate(R.layout.row, null, false);
               TableRow view = (TableRow) itemView;
               view.setBackgroundColor(context.getResources().getColor(Config.APP_COLORS[LESSON_TYPE]));
               TextView tv = (TextView) view.getChildAt(0);
               tv.setText(Config.TRAINER_ABBR[LESSON_TYPE]);
               // add it to Layout
               tableLayout.addView(itemView);
           }
       }
    }

}
