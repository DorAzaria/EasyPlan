package com.example.easyplan.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.easyplan.Controller.FirebaseData;
import com.example.easyplan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EndPlanActivity extends AppCompatActivity {

    private Button end_plan_btn; // btn
    private String trainee_id, trainer_id;
    private RatingBar end_plan_toolbar_ratebar;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseData model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new FirebaseData();
        database = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_end_plan);
        end_plan_btn = (Button) findViewById(R.id.end_plan_btn);
        end_plan_toolbar_ratebar = (RatingBar) findViewById(R.id.end_plan_toolbar_ratebar);
        Intent move = getIntent();
        trainee_id = move.getStringExtra("trainee_id");
        trainer_id = move.getStringExtra("trainer_id");

        model.getPlanReference(model.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    startActivity(new Intent(EndPlanActivity.this, TraineeHomepageActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void endPlan(View view) {
        model.getUserReference(model.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainer_id = snapshot.child("plan_status").getValue(String.class);
                model.getUserReference(trainer_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        float rating = end_plan_toolbar_ratebar.getRating();
                        int counter = 1;
                        double total = rating;
                        double rate = rating;
                        if (snapshot.child("counter").getValue() != null) {
                            counter = (snapshot.child("counter").getValue(Integer.class) + 1);
                            total = (snapshot.child("total").getValue(Integer.class) + rating);
                            rate = total / counter;
                        }
                        database.getReference("Users/" + trainer_id).child("counter").setValue(counter);
                        database.getReference("Users/" + trainer_id).child("total").setValue(total);
                        database.getReference("Users/" + trainer_id).child("rate").setValue(rate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                model.getUserReference(trainer_id+"/my_trainees").child(trainee_id).setValue(null);
                model.getPlanReference(trainee_id).setValue(null);
                model.getUserReference(trainee_id).child("plan_status").setValue("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent move = new Intent(EndPlanActivity.this , TraineeHomepageActivity.class);
        startActivity(move);
        finish();
    }
}