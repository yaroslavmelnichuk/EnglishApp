package com.example.yaroslav.englishapp.controler;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaroslav.englishapp.lingvomapanswers.AnswersCounter;
import com.example.yaroslav.englishapp.model.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yaroslav on 08.08.15.
 */
public class QuestionsController {
    private Context context;
    private ArrayList<Question> data;
    private String jsonSource;
    private Random random;
    public Question currentQuestion;
    private int prevIndex = 0;
    private int currentIndex = 0;
    private int countOfQuestions;
    public String KEY;
    public AnswersCounter counter;
    private Button btnBlue;
    private Button btnRed;
    private Button btnGreen;
    private Button btnYellow;

    private TextView questionText;
    private TextView questionStatus;


    public QuestionsController(Context context, String source){
        this.context = context;
        this.jsonSource = source;
        data = new ArrayList<>();
        this.jsonSource = source;
        loadFromJson();
        random = new Random();
        currentQuestion = data.get(prevIndex);
        KEY = currentQuestion.key;

        counter = new AnswersCounter();
    }

    public void attachViews(TextView tvText, TextView tvStatus, Button... buttons){

        this.questionText = tvText;
        this.questionStatus = tvStatus;

        this.btnRed = buttons[0];
        this.btnGreen = buttons[1];
        this.btnBlue = buttons[2];
        this.btnYellow = buttons[3];
    }

    public String loadJSONFromAsset() {

        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonSource);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void loadFromJson(){
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray block_arr = obj.getJSONArray("questions");
            for(int i = 0; i < block_arr.length(); i++){
                JSONObject question = block_arr.getJSONObject(i);

                String key = question.getString("key");
                String red = question.getString("red");
                String blue = question.getString("blue");
                String green = question.getString("green");
                String yellow = question.getString("yellow");

                ArrayList<String> tmp = new ArrayList<>();//всі відповіді
                tmp.add(red);
                tmp.add(green);
                tmp.add(blue);
                tmp.add(yellow);

                String text = question.getString("text");
                data.add(new Question(text, key, tmp));
            }
            countOfQuestions = data.size();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("JSON ERRORR");
            Toast.makeText(context, "Erroror", Toast.LENGTH_LONG).show();
        }
    }

    public void toNextQuestion(){
        counter.CURRENT_QUESTION++;
        currentIndex = random.nextInt(countOfQuestions);
        if(currentIndex == prevIndex) {
            currentIndex = random.nextInt(countOfQuestions);
        }
        currentQuestion = data.get(currentIndex);
        KEY = currentQuestion.key;
        prevIndex = currentIndex;
        updateViews();
    }
    private void updateViews(){

        questionStatus.setText("");
        questionText.setText(currentQuestion.text);
        btnRed.setText(currentQuestion.items.get(0));
        btnGreen.setText(currentQuestion.items.get(1));
        btnBlue.setText(currentQuestion.items.get(2));
        btnYellow.setText(currentQuestion.items.get(3));
    }
    public void  checkAnswer(String text){
        if(currentQuestion.key.equals(text)){
            questionStatus.setText("Well done!");
            counter.PASSED_QUESTIONS++;
        }
        else {
            questionStatus.setText("Wrong!");
            counter.NOT_PASSED_QUESTIONS++;
        }
    }

    public boolean isEndOfTest(){
       return counter.CURRENT_QUESTION >= 5;
    }
}
