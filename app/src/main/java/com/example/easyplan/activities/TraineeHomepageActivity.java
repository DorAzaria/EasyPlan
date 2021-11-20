package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.R;

public class TraineeHomepageActivity extends AppCompatActivity {
    private ImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight;
    private TextView trainee_homepage_train_number, trainee_homepage_train_time, trainee_homepage_train_exer;
    private TextView trainee_homepage_day_1, trainee_homepage_day_2, trainee_homepage_day_3, trainee_homepage_day_4;
    private TextView trainee_homepage_day_5, trainee_homepage_day_6, trainee_homepage_cheat_day;
    private Button trainee_homepage_btn, trainee_homepage_plan_btn;
    private ConstraintLayout trainee_homepage_menu, trainee_homepage_trains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_homepage);

        trainee_homepage_picture = (ImageView) findViewById(R.id.trainee_homepage_picture);
        trainee_homepage_name = (TextView) findViewById(R.id.trainee_homepage_name);
        trainee_homepage_address = (TextView) findViewById(R.id.trainee_homepage_address);
        trainee_homepage_gender = (TextView) findViewById(R.id.trainee_homepage_gender);
        trainee_homepage_age = (TextView) findViewById(R.id.trainee_homepage_age);
        trainee_homepage_height = (TextView) findViewById(R.id.trainee_homepage_height);
        trainee_homepage_weight = (TextView) findViewById(R.id.trainee_homepage_weight);
        trainee_homepage_train_number = (TextView) findViewById(R.id.trainee_homepage_train_number);
        trainee_homepage_train_time = (TextView) findViewById(R.id.trainee_homepage_train_time);
        trainee_homepage_train_exer = (TextView) findViewById(R.id.trainee_homepage_train_exer);
        trainee_homepage_day_1 = (TextView) findViewById(R.id.trainee_homepage_day_1);
        trainee_homepage_day_2 = (TextView) findViewById(R.id.trainee_homepage_day_2);
        trainee_homepage_day_3 = (TextView) findViewById(R.id.trainee_homepage_day_3);
        trainee_homepage_day_4 = (TextView) findViewById(R.id.trainee_homepage_day_4);
        trainee_homepage_day_5 = (TextView) findViewById(R.id.trainee_homepage_day_5);
        trainee_homepage_day_6 = (TextView) findViewById(R.id.trainee_homepage_day_6);
        trainee_homepage_cheat_day = (TextView) findViewById(R.id.trainee_homepage_cheat_day);
        trainee_homepage_btn = (Button) findViewById(R.id.trainee_homepage_btn);
        trainee_homepage_plan_btn = (Button) findViewById(R.id.trainee_homepage_plan_btn);
        trainee_homepage_menu = (ConstraintLayout) findViewById(R.id.trainee_homepage_menu);
        trainee_homepage_trains = (ConstraintLayout) findViewById(R.id.trainee_homepage_trains);


        Intent intent = getIntent();

        if(intent.hasExtra("from_register")) {
            trainee_homepage_btn.setVisibility(View.GONE);
            trainee_homepage_menu.setVisibility(View.GONE);
            trainee_homepage_trains.setVisibility(View.GONE);
        }
        else {
            trainee_homepage_plan_btn.setVisibility(View.GONE);
        }

        trainee_homepage_plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TraineeHomepageActivity.this, RegisterByTargetActivity.class));
            }
        });

    }

    private void getTrainee() {

    }
}