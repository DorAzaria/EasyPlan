package com.example.easyplan.data;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//////**********************************************////////////
//////
//////**********************************************////////////

public class FirebaseData {

    // The entry point of the Firebase Authentication
    private FirebaseAuth mAuth;

    // The entry point for accessing a Firebase Database
    private FirebaseDatabase database;

    // Reading or Writing to the data base location.
    private DatabaseReference reference;

    // Variable with which the Trainer and trainee can communicate through the database
    private String notification_message;

    private StorageReference storageReference;



//////**********************************************////////////
////// Initialize the variables : mAuth & database
//////**********************************************////////////
    public FirebaseData() {
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

//////**********************************************////////////
////// Get the user ID
//////**********************************************////////////
    public String getID() {
        return mAuth.getUid();
    }


//////**********************************************////////////
////// Save a trainer user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainer(Trainer trainer) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
        reference.setValue(trainer);
    }


//////**********************************************////////////
////// Save a trainee user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainee(Trainee trainee) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
        reference.setValue(trainee);
    }


    public void uploadUri(Uri image) {

         FirebaseStorage storage = FirebaseStorage.getInstance();
         StorageReference storageRef = storage.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
         storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(image);

    }
//////**********************************************////////////
////// returns the notification based on the appropriate ID
//////**********************************************////////////
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


//////**********************************************////////////
////// check if there's new notifications
//////**********************************************////////////
    public boolean checkForNewNotifications(String notification) {
        return notification != null && !notification.isEmpty();
    }


//////**********************************************////////////
////// Deletes the notification based on the appropriate ID
//////**********************************************////////////
    public void clearNotification() {
        reference = database.getReference("Users/" + mAuth.getUid() + "/notifications");
        reference.setValue("");
    }


//////**********************************************////////////
////// Deletes the notification based on the appropriate ID
//////**********************************************////////////
    public void sendPlanRequest(String trainee_id, String trainer_id) {

        reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + trainee_id);
        reference.setValue("false");

        reference = database.getReference("Users/" + trainee_id + "/plan_status");
        reference.setValue(trainer_id);

        reference = database.getReference("Users/" + trainer_id + "/notifications");

        DatabaseReference gets_trainer_name = database.getReference("Users/"+trainee_id);

        gets_trainer_name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String trainee_name = snapshot.child("name").getValue(String.class);
                reference.setValue("You've got a new plan request from " + trainee_name);
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
