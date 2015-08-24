package com.example.yaroslav.englishapp.controler;

import android.content.Context;
import com.example.yaroslav.englishapp.model.Lesson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yaroslav on 11.08.15.
 */
public class LessonController {
    Context context;
    public LessonController(Context context){
        this.context = context;
    }
   public Lesson loadFromJson(int lessonCode){
        Lesson lesson = null;
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray block_arr = obj.getJSONArray("videos");
                JSONObject videos = block_arr.getJSONObject(lessonCode - 1);

                String name = videos.getString("name");
                String id = videos.getString("id");
             lesson = new Lesson(context, name, id,lessonCode);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("JSON ERRORR");
        }
        return lesson;
    }
    private String loadJSONFromAsset() {

        String json = null;
        try {

            InputStream is = context.getAssets().open("videos.json");
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
}
