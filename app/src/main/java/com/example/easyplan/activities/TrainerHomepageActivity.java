package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.Data.Trainer;
import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrainerHomepageActivity extends AppCompatActivity {

    private ImageView trainer_homepage_picture, trainer_list_menu;
    private TextView trainer_homepage_name, trainer_homepage_rate;
    private TextView trainer_homepage_cost, trainer_homepage_days,trainer_homepage_duration;
    private ImageView trainer_homepage_fitness, trainer_homepage_muscle, trainer_homepage_cardio, trainer_homepage_menu;
    private Button trainer_start_plan;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);
        trainer_homepage_name = (TextView) findViewById(R.id.trainer_homepage_name);
        trainer_homepage_picture = (ImageView) findViewById(R.id.trainer_homepage_picture);
        trainer_list_menu = (ImageView) findViewById(R.id.trainer_list_menu);
        trainer_start_plan = (Button) findViewById(R.id.trainer_start_plan);
        trainer_homepage_fitness = (ImageView) findViewById(R.id.trainer_homepage_fitness);
        trainer_homepage_muscle = (ImageView) findViewById(R.id.trainer_homepage_muscle);
        trainer_homepage_cardio = (ImageView) findViewById(R.id.trainer_homepage_cardio);
        trainer_homepage_menu = (ImageView) findViewById(R.id.trainer_homepage_menu);
        trainer_homepage_rate = (TextView) findViewById(R.id.trainer_homepage_rate);
        trainer_homepage_cost = (TextView) findViewById(R.id.trainer_homepage_cost);
        trainer_homepage_days = (TextView) findViewById(R.id.trainer_homepage_days);
        trainer_homepage_duration = (TextView) findViewById(R.id.trainer_homepage_duration);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Intent move = getIntent();
        String trainer_id = "";

        // trainer want to see my homepage - he choose me
        if (move.hasExtra("trainer id from firebase")) {
            trainer_id = move.getStringExtra("trainer id from firebase");
            trainer_list_menu.setVisibility(View.GONE);

        }
        else {
            trainer_id = mAuth.getUid();
            trainer_start_plan.setVisibility(View.GONE);
        }
        reference = database.getReference("Users/" + trainer_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainer trainer = snapshot.getValue(Trainer.class);
                trainer_homepage_name.setText(trainer.getName());
                trainer_homepage_cost.setText(String.valueOf(trainer.getCost()));
                if (!trainer.getTargets().contains("Fitness")) trainer_homepage_fitness.setVisibility(View.GONE);
                if (!trainer.getTargets().contains("Muscle")) trainer_homepage_muscle.setVisibility(View.GONE);
                if (!trainer.getTargets().contains("Cardio")) trainer_homepage_cardio.setVisibility(View.GONE);
                if (!trainer.getTargets().contains("Menu Nutrition")) trainer_homepage_menu.setVisibility(View.GONE);
                // because trainer see my profile - remove the menu
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        trainer_start_plan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(TrainerHomepageActivity.this, TraineeHomepageActivity.class));
             }
         });


        trainer_list_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainerHomepageActivity.this, TraineeListActivity.class));
            }
        });

    }

    public void logout (View v) {
        mAuth.signOut();
        Intent move = new Intent(TrainerHomepageActivity.this , LoginActivity.class);
        startActivity(move);
    }
}