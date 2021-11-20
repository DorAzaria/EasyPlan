package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.easyplan.R;

public class RegisterTrainerActivity extends AppCompatActivity {

    private Button register_trainer_btn;
    private ImageView register_trainer_image, register_trainer_upload;
    private EditText register_trainer_full_name, register_trainer_address, register_trainer_education;
    private EditText register_trainer_personal_page, register_trainer_age, register_trainer_cost, register_trainer_days;
    private RadioButton register_trainer_male_radio, register_trainer_female_radio;
    private CheckBox register_trainer_cardio, register_trainer_fitness, register_trainer_muscle, register_trainer_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainer);

        register_trainer_btn = (Button) findViewById(R.id.register_trainer_btn);
        register_trainer_image = (ImageView) findViewById(R.id.register_trainer_image);
        register_trainer_upload = (ImageView) findViewById(R.id.register_trainer_upload);
        register_trainer_full_name = (EditText) findViewById(R.id.register_trainer_full_name);
        register_trainer_address = (EditText) findViewById(R.id.register_trainer_address);
        register_trainer_education = (EditText) findViewById(R.id.register_trainer_education);
        register_trainer_personal_page = (EditText) findViewById(R.id.register_trainer_personal_page);
        register_trainer_age = (EditText) findViewById(R.id.register_trainer_age);
        register_trainer_cost = (EditText) findViewById(R.id.register_trainer_cost);
        register_trainer_days = (EditText) findViewById(R.id.register_trainer_days);
        register_trainer_male_radio = (RadioButton) findViewById(R.id.register_trainer_male_radio);
        register_trainer_female_radio = (RadioButton) findViewById(R.id.register_trainer_female_radio);
        register_trainer_cardio = (CheckBox) findViewById(R.id.register_trainer_cardio);
        register_trainer_fitness = (CheckBox) findViewById(R.id.register_trainer_fitness);
        register_trainer_muscle = (CheckBox) findViewById(R.id.register_trainer_muscle);
        register_trainer_menu = (CheckBox) findViewById(R.id.register_trainer_menu);

        register_trainer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterTrainerActivity.this, TrainerHomepageActivity.class));
            }
        });
    }
}