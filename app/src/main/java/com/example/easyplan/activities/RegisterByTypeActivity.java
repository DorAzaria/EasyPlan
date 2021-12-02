package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.easyplan.R;

public class RegisterByTypeActivity extends AppCompatActivity {

    private CardView register_by_type_trainee, register_by_type_trainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_type);

        String email_address = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        register_by_type_trainee = (CardView) findViewById(R.id.register_by_type_trainee);
        register_by_type_trainer = (CardView) findViewById(R.id.register_by_type_trainer);

        register_by_type_trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterByTypeActivity.this, RegisterTraineeActivity.class));
            }
        });

        register_by_type_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterByTypeActivity.this, RegisterTrainerActivity.class));
            }
        });
    }
}