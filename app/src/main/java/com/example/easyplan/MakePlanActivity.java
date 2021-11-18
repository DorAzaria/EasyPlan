package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MakePlanActivity extends AppCompatActivity {
    private ImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight,make_plan_train_number;
    private EditText make_plan_train_time, make_plan_train_exer;
    private EditText make_plan_day_1, make_plan_day_2, make_plan_day_3, make_plan_day_4;
    private EditText make_plan_day_5, make_plan_day_6, make_plan_cheat_day;
    private Button make_plan_submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);

        trainee_homepage_picture = (ImageView) findViewById(R.id.trainee_homepage_picture);

        trainee_homepage_name = (TextView) findViewById(R.id.trainee_homepage_name);
        trainee_homepage_address = (TextView) findViewById(R.id.trainee_homepage_address);
        trainee_homepage_gender = (TextView) findViewById(R.id.trainee_homepage_gender);
        trainee_homepage_age = (TextView) findViewById(R.id.trainee_homepage_age);
        trainee_homepage_height = (TextView) findViewById(R.id.trainee_homepage_height);
        trainee_homepage_weight = (TextView) findViewById(R.id.trainee_homepage_weight);
        make_plan_train_number = (TextView) findViewById(R.id.make_plan_train_number);
        make_plan_train_time = (EditText) findViewById(R.id.make_plan_train_time);
        make_plan_train_exer = (EditText) findViewById(R.id.make_plan_train_exer);
        make_plan_day_1 = (EditText) findViewById(R.id.make_plan_day_1);
        make_plan_day_2 = (EditText) findViewById(R.id.make_plan_day_2);
        make_plan_day_3 = (EditText) findViewById(R.id.make_plan_day_3);
        make_plan_day_4 = (EditText) findViewById(R.id.make_plan_day_4);
        make_plan_day_5 = (EditText) findViewById(R.id.make_plan_day_5);
        make_plan_day_6 = (EditText) findViewById(R.id.make_plan_day_6);
        make_plan_cheat_day = (EditText) findViewById(R.id.make_plan_cheat_day);
        make_plan_submit_btn = (Button) findViewById(R.id.make_plan_submit_btn);

        make_plan_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakePlanActivity.this, TraineeHomepageActivity.class));
            }
        });

    }
}