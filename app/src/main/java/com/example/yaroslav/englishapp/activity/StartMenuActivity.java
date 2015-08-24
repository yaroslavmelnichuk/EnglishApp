package com.example.yaroslav.englishapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yaroslav.englishapp.R;

public class StartMenuActivity extends Activity implements View.OnClickListener {
    Button btnWebSite;
    Button btnLingvo;
    Button btnFilolingvia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        btnWebSite = (Button) findViewById(R.id.btnWeb);
        btnWebSite.setOnClickListener(this);

        btnLingvo = (Button) findViewById(R.id.btnLingvoMap);
        btnLingvo.setOnClickListener(this);

        btnFilolingvia = (Button) findViewById(R.id.btnFilolingvia);
        btnFilolingvia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnWeb:
                Uri address = Uri.parse("http://trainer.filolingvia.com/");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
                break;
            case R.id.btnLingvoMap:
                Intent intent = new Intent(this, TestActivity.class);
                intent.putExtra("TEST_TYPE",1);
                startActivity(intent);
                break;
            case R.id.btnFilolingvia:
                Intent intent2 = new Intent(this, TestActivity.class);
                intent2.putExtra("TEST_TYPE",2);
                startActivity(intent2);
                break;
        }
    }
}
