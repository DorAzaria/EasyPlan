package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.easyplan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EndPlanActivity extends AppCompatActivity {

    private Button end_plan_btn;
    private String trainee_id, trainer_id;
    private RatingBar end_plan_toolbar_ratebar;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_end_plan);
        end_plan_btn = (Button) findViewById(R.id.end_plan_btn);
        end_plan_toolbar_ratebar = (RatingBar) findViewById(R.id.end_plan_toolbar_ratebar);
        Intent move = getIntent();
        trainee_id = move.getStringExtra("trainee_id");
        trainer_id = move.getStringExtra("trainer_id");

    }

    public void endPlan(View view) {
        reference = database.getReference("Users/" + trainer_id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float rating = end_plan_toolbar_ratebar.getRating();
                int counter = 1;
                double total = rating;
                double rate = rating;
                if(snapshot.child("counter").getValue() !=null){
                    counter = (snapshot.child("counter").getValue(Integer.class) + 1);
                    total = (snapshot.child("total").getValue(Integer.class) + rating);
                    rate = total / counter;
                }

                reference = database.getReference("Users/" + trainer_id);
                reference.child("counter").setValue(counter);
                reference.child("total").setValue(total);
                reference.child("rate").setValue(rate);
                reference = database.getReference("Plans/" + trainee_id);
                reference.getRef().removeValue();
                reference = database.getReference("Users/" + trainee_id);
                reference.child("plan_status").setValue("");
                reference = database.getReference("Users/" + trainer_id+"/my_trainees");
                reference.child(trainee_id).getRef().removeValue();
                Intent move = new Intent(EndPlanActivity.this , TraineeHomepageActivity.class);
                move.putExtra("Flag","true");
                move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(move);
                EndPlanActivity.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}