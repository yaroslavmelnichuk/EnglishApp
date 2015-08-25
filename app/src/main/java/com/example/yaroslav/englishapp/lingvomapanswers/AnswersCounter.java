package com.example.yaroslav.englishapp.lingvomapanswers;

/**
 * Created by yaroslav on 25.08.15.
 */
public class AnswersCounter {
    public final int CONT_OF_QUESTIONS = 40;
    public int PASSED_QUESTIONS = 0;
    public int CURRENT_QUESTION = 0;
    public int NOT_PASSED_QUESTIONS = 0;

    public boolean isPassedTest(){
        boolean flag = false;
        if(CURRENT_QUESTION >= 10)
            flag = getResult();
        return flag;
    }
    private boolean getResult(){
        return (NOT_PASSED_QUESTIONS >= 3);
    }

}
