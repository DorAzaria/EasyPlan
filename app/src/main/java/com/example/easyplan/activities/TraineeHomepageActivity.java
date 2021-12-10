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
    private TextView time_of_train_1 , time_of_train_2 , time_of_train_3;
    private TextView exercise_1 , exercise_2 , exercise_3;
    private TextView trainee_homepage_day_1, trainee_homepage_day_2, trainee_homepage_day_3, trainee_homepage_day_4;
    private TextView trainee_homepage_day_5, trainee_homepage_day_6, trainee_homepage_cheat_day;
    private Button trainee_homepage_btn, trainee_homepage_plan_btn;
    private ConstraintLayout trainee_homepage_menu, trainee_homepage_trains;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public TraineeHomepageActivity() {
    }

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
        time_of_train_1 = (TextView) findViewById(R.id.time_of_train_1);
        time_of_train_2 = (TextView) findViewById(R.id.time_of_train_2);
        time_of_train_3 = (TextView) findViewById(R.id.time_of_train_3);
        exercise_1 = (TextView) findViewById(R.id.exercise_1);
        exercise_2 = (TextView) findViewById(R.id.exercise_2);
        exercise_3 = (TextView) findViewById(R.id.exercise_3);
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

         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Users/" + mAuth.getUid());

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



        reference = database.getReference("Plans/");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(mAuth.getUid())) {
                    trainee_homepage_btn.setVisibility(View.VISIBLE);
                    trainee_homepage_menu.setVisibility(View.VISIBLE);
                    trainee_homepage_trains.setVisibility(View.VISIBLE);
                    show_plan();
                }
                else {
                    System.out.println("ASDASD");
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

    private void show_plan ( ) {
            reference = database.getReference("Plans/" + mAuth.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    time_of_train_1.setText(snapshot.child("trains").child("1").child("time").getValue(String.class));
                    exercise_1.setText(snapshot.child("trains").child("1").child("exercise").getValue(String.class));
                    time_of_train_2.setText(snapshot.child("trains").child("2").child("time").getValue(String.class));
                    exercise_2.setText(snapshot.child("trains").child("2").child("exercise").getValue(String.class));
                    time_of_train_3.setText(snapshot.child("trains").child("3").child("time").getValue(String.class));
                    exercise_3.setText(snapshot.child("trains").child("3").child("exercise").getValue(String.class));
                    trainee_homepage_day_1.setText(snapshot.child("menu").child("0").getValue(String.class));
                    trainee_homepage_day_2.setText(snapshot.child("menu").child("1").getValue(String.class));
                    trainee_homepage_day_3.setText(snapshot.child("menu").child("2").getValue(String.class));
                    trainee_homepage_day_4.setText(snapshot.child("menu").child("3").getValue(String.class));
                    trainee_homepage_day_5.setText(snapshot.child("menu").child("4").getValue(String.class));
                    trainee_homepage_day_6.setText(snapshot.child("menu").child("5").getValue(String.class));
                    trainee_homepage_cheat_day.setText(snapshot.child("menu").child("6").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

}