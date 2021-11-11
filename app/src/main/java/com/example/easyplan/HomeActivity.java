package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private Button btn, bp, bm;
    private TextView textView;
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn = (Button) findViewById(R.id.home_btn);
        bp = (Button) findViewById(R.id.plus);
        bm = (Button) findViewById(R.id.minus);
        textView = (TextView) findViewById(R.id.counter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(moveToMain);
            }
        });

        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText(Integer.toString(counter));
            }
        });

        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                textView.setText(Integer.toString(counter));
            }
        });
    }
}