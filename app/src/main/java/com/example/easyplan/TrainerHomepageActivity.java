package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TrainerHomepageActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int image_id = Integer.parseInt(intent.getStringExtra("image"));

        textView = (TextView) findViewById(R.id.profile_trainer_name);
        imageView = (ImageView) findViewById(R.id.profile_image_card);

        textView.setText(name);
        imageView.setImageResource(image_id);
    }
}