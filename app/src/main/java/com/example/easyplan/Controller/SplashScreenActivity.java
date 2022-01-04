package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//////**********************************************////////////
////// Splash Screen - if user is logged in - goes right to its homepage
////// else, go to login page.
//////**********************************************////////////
public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String move_type;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ConstraintLayout constraintLayout = findViewById(R.id.splash_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                System.out.println(move_type);
                checkUserType();
            }

        }, SPLASH_DISPLAY_LENGTH);

    }









//////**********************************************////////////
////// Checks the user type: Trainer, Trainee or Unsigned.
//////**********************************************////////////
    private void checkUserType() {
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
                    move_type = snapshot.child("type").getValue(String.class);
                    if (move_type.equals("Trainee")) {
                        SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this,TraineeHomepageActivity.class));
                    }
                    else if (move_type.equals("Trainer")) {
                        SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this,TrainerHomepageActivity.class));
                    }
                    SplashScreenActivity.this.finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        else {
            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
            SplashScreenActivity.this.finish();
        }
    }


}