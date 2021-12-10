package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.example.easyplan.Data.FirebaseData;
import com.example.easyplan.Data.Trainer;
import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    FirebaseDatabase database;
    DatabaseReference reference;
    String move_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ConstraintLayout constraintLayout = findViewById(R.id.splash_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        move_type = "";
        // user is already logged in
        if (user != null) {
            String uid = user.getUid();
            // check the type of the user and send him to specific home page by his type
            reference = database.getReference("Users/" + uid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String type = snapshot.child("type").getValue(String.class);
                    if (type.equals("Trainer")) {
                        move_type = "Trainer";
                    } else move_type = "Trainee";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

             new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent;

                if (move_type.equals("Trainee")) {
                    mainIntent = new Intent(SplashScreenActivity.this,TraineeHomepageActivity.class);
                }
                else if (move_type.equals("Trainer")) mainIntent = new Intent(SplashScreenActivity.this,TrainerHomepageActivity.class);

                else  mainIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}