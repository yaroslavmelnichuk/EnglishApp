package com.example.yaroslav.englishapp.model;

import java.util.ArrayList;

/**
 * Created by yaroslav on 08.08.15.
 */
public class Question {
    public String text;
    public String key;// Ключ вопроса
    public ArrayList<String> items;
    public int keyIndex;

    public Question(String txt, String key, ArrayList<String> items){
        this.items = items;
        this.text = txt;
        this.key = key;
    }

}
