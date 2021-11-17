package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterByTypeActivity extends AppCompatActivity {

    private CardView cardView_by_type_trainee, cardView_by_type_trainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_type);

        cardView_by_type_trainee = (CardView) findViewById(R.id.cardView_by_type_trainee);
        cardView_by_type_trainer = (CardView) findViewById(R.id.cardView_by_type_trainer);

        cardView_by_type_trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterByTypeActivity.this, RegisterTraineeActivity.class));
            }
        });

        cardView_by_type_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterByTypeActivity.this, RegisterTrainerActivity.class));
            }
        });
    }
}