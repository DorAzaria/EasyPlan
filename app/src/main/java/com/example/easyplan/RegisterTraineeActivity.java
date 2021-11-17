package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterTraineeActivity extends AppCompatActivity {

    private Button register_trainee_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainee);

        register_trainee_btn = (Button) findViewById(R.id.register_trainee_btn);

        register_trainee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainee_choose_target = new Intent(RegisterTraineeActivity.this, RegisterByTargetActivity.class);
                startActivity(trainee_choose_target);
            }
        });
    }
}