package com.edong.mohelp.config;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.edong.mohelp.R;

public class NoticeActivity extends AppCompatActivity {


    private Button nextStepBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        nextStepBt = findViewById(R.id.next_button);
        nextStepBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this,ConfigActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
