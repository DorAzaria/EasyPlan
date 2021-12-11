package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.easyplan.R;

//////**********************************************////////////
////// This activity is a part of the registration process by picking user type.
////// There's two types: Trainer or Trainees.
//////**********************************************////////////
public class RegisterByTypeActivity extends AppCompatActivity {

    private String email_address, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_type);
        email_address = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
    }


//////**********************************************////////////
    public void chooseTrainee(View view) {
        Intent move = new Intent(RegisterByTypeActivity.this, RegisterTraineeActivity.class);
        move.putExtra("email", email_address);
        move.putExtra("password",password);
        startActivity(move);
    }


//////**********************************************////////////
    public void chooseTrainer(View view) {
        Intent move = new Intent(RegisterByTypeActivity.this, RegisterTrainerActivity.class);
        move.putExtra("email", email_address);
        move.putExtra("password",password);
        startActivity(move);
    }
}