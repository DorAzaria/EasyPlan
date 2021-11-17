package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterTrainerActivity extends AppCompatActivity {

    private Button register_trainer_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainer);

        register_trainer_btn = (Button) findViewById(R.id.register_trainer_btn);

        register_trainer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterTrainerActivity.this, TrainerHomepageActivity.class));
            }
        });
    }
}