package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.Data.FirebaseData;
import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TraineeHomepageActivity extends AppCompatActivity {
    private ImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight;
    private TextView trainee_homepage_train_number, trainee_homepage_train_time, trainee_homepage_train_exer;
    private TextView trainee_homepage_day_1, trainee_homepage_day_2, trainee_homepage_day_3, trainee_homepage_day_4;
    private TextView trainee_homepage_day_5, trainee_homepage_day_6, trainee_homepage_cheat_day;
    private Button trainee_homepage_btn, trainee_homepage_plan_btn;
    private ConstraintLayout trainee_homepage_menu, trainee_homepage_trains;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_homepage);
        mAuth = FirebaseAuth.getInstance();
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
        trainee_homepage_btn.setVisibility(View.GONE);
        trainee_homepage_menu.setVisibility(View.GONE);
        trainee_homepage_trains.setVisibility(View.GONE);
        trainee_homepage_plan_btn.setVisibility(View.GONE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainee_homepage_name.setText(snapshot.child("name").getValue(String.class));
                trainee_homepage_age.setText(snapshot.child("age").getValue(Integer.class).toString());
                trainee_homepage_address.setText(snapshot.child("address").getValue(String.class));
                trainee_homepage_gender.setText(snapshot.child("gender").getValue(String.class));
                trainee_homepage_height.setText(snapshot.child("height").getValue(String.class));
                trainee_homepage_weight.setText(snapshot.child("weight").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Plans/" + mAuth.getUid())) {
                    trainee_homepage_btn.setVisibility(View.VISIBLE);
                    trainee_homepage_menu.setVisibility(View.VISIBLE);
                    trainee_homepage_trains.setVisibility(View.VISIBLE);
                }
                else {
                    trainee_homepage_plan_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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