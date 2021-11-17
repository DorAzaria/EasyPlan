package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TrainerHomepageActivity extends AppCompatActivity {

    private ImageView imageView, trainer_list_menu;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);
        textView = (TextView) findViewById(R.id.profile_trainer_name);
        imageView = (ImageView) findViewById(R.id.profile_image_card);
        trainer_list_menu = (ImageView) findViewById(R.id.trainer_list_menu);

        Intent intent = getIntent();

        if(intent.hasExtra("name")) {
            String name = intent.getStringExtra("name");
            int image_id = Integer.parseInt(intent.getStringExtra("image"));
            textView.setText(name);
            imageView.setImageResource(image_id);
        }
         else {
            textView.setText("Trainer Name");
            imageView.setImageResource(R.drawable.trainer_logo);
        }


        trainer_list_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainerHomepageActivity.this, TraineeListActivity.class));
            }
        });


    }
}