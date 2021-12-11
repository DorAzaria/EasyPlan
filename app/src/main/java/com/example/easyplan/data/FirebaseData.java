package com.example.easyplan.data;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseData {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String notification_message;


    public FirebaseData() {
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
    }


    public String getID() {
        return mAuth.getUid();
    }


    public void createTrainer(Trainer trainer) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
        reference.setValue(trainer);
    }


    public void createTrainee(Trainee trainee) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
        reference.setValue(trainee);
    }


    public String getNotification() {
        reference = database.getReference("Users/" + mAuth.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notification_message = snapshot.child("notifications").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return notification_message;
    }


    public boolean checkForNewNotifications(String notification) {
        return notification != null && !notification.isEmpty();
    }


    public void clearNotification() {
        reference = database.getReference("Users/" + mAuth.getUid() + "/notifications");
        reference.setValue("");
    }


    public void sendPlanRequest(String trainee_id, String trainer_id) {

        reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + trainee_id);
        reference.setValue("false");

        reference = database.getReference("Users/" + trainee_id + "/plan_status");
        reference.setValue(trainer_id);

        reference = database.getReference("Users/" + trainer_id + "/notifications");
        DatabaseReference ref_trainer = database.getReference("Users/"+mAuth.getUid());
        ref_trainer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.setValue("You've got a new plan request from " + snapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


//    public void createPlan(Plan plan, String trainer_id, String trainee_id) {
//        reference = database.getReference("Plans/" + trainee_id);
//        reference.setValue(plan);
//        reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + trainee_id);
//        reference.setValue("true");
//    }

}
