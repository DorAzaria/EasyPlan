package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.R;

public class TrainerHomepageActivity extends AppCompatActivity {

    private ImageView trainer_homepage_picture, trainer_list_menu;
    private TextView trainer_homepage_name, trainer_homepage_rate;
    private TextView trainer_homepage_cost, trainer_homepage_days,trainer_homepage_duration;
    private ImageView trainer_homepage_fitness, trainer_homepage_muscle, trainer_homepage_cardio, trainer_homepage_menu;
    private Button trainer_homepage_btn;
    private String trainerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             trainerId = extras.getString("myId");
        }
        trainer_homepage_name = (TextView) findViewById(R.id.trainer_homepage_name);
        trainer_homepage_picture = (ImageView) findViewById(R.id.trainer_homepage_picture);
        trainer_list_menu = (ImageView) findViewById(R.id.trainer_list_menu);
        trainer_homepage_btn = (Button) findViewById(R.id.trainer_homepage_btn);
        trainer_homepage_fitness = (ImageView) findViewById(R.id.trainer_homepage_fitness);
        trainer_homepage_muscle = (ImageView) findViewById(R.id.trainer_homepage_muscle);
        trainer_homepage_cardio = (ImageView) findViewById(R.id.trainer_homepage_cardio);
        trainer_homepage_menu = (ImageView) findViewById(R.id.trainer_homepage_menu);
        trainer_homepage_rate = (TextView) findViewById(R.id.trainer_homepage_rate);
        trainer_homepage_cost = (TextView) findViewById(R.id.trainer_homepage_cost);
        trainer_homepage_days = (TextView) findViewById(R.id.trainer_homepage_days);
        trainer_homepage_duration = (TextView) findViewById(R.id.trainer_homepage_duration);

        Intent intent = getIntent();

        if(intent.hasExtra("name")) {
            String name = intent.getStringExtra("name");
            int image_id = Integer.parseInt(intent.getStringExtra("image"));
            trainer_homepage_name.setText(name);
            trainer_homepage_picture.setImageResource(image_id);
        }
         else {
            trainer_homepage_name.setText("Trainer Name");
            trainer_homepage_picture.setImageResource(R.drawable.trainer_logo);
        }

        trainer_homepage_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(TrainerHomepageActivity.this, TraineeHomepageActivity.class));
             }
         });

        trainer_list_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainerHomepageActivity.this, TraineeListActivity.class);
                i.putExtra("myId",trainerId);
                startActivity(i);
            }
        });


    }
}