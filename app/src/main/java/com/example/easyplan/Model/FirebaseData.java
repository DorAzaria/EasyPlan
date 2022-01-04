package com.example.easyplan.Model;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.easyplan.Controller.MakePlanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;






//////**********************************************////////////
//////
//////**********************************************////////////

public class FirebaseData  {

    // The entry point of the Firebase Authentication
    private FirebaseAuth mAuth;

    // The entry point for accessing a Firebase Database
    private FirebaseDatabase database;

    // Reading or Writing to the data base location.
    private DatabaseReference reference;

    // Variable with which the Trainer and trainee can communicate through the database
    private String notification_message;

    private StorageReference storageReference;

    private FirebaseMessaging fireBaseMessaging;

    private Context context;
    private Activity activity;


    //////**********************************************////////////
////// Initialize the variables : mAuth & database
//////**********************************************////////////
    public FirebaseData() {
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseDatabase getDatabase() {
        return this.database;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    //////**********************************************////////////
////// Get the user ID
//////**********************************************////////////
    public String getID() {
        return mAuth.getUid();
    }

    public DatabaseReference getUserReference() {
        return database.getReference("Users/" + getID());
    }
    public DatabaseReference getUserReference(String type) {
        return database.getReference("Users/" + type);
    }

    public DatabaseReference getPlanReference(String id) {
        return database.getReference("Plans/"+id);
    }
//////**********************************************////////////
////// Save a trainer user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainer(Trainer trainer) {
        DatabaseReference reference = getUserReference();
        reference.setValue(trainer);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        reference.child("token").setValue(token);

                    }
                });

    }

    public StorageReference getStorageReference() {
        return  FirebaseStorage.getInstance().getReference("images/" + getID());
    }
    public StorageReference getStorageReference(String type) {
        return  FirebaseStorage.getInstance().getReference("images/" + type);
    }

    public void logout() {
        mAuth.signOut();
    }

//////**********************************************////////////
////// Save a trainee user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainee(Trainee trainee) {
        DatabaseReference reference = getUserReference();
        reference.setValue(trainee);

        // set the token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        reference.child("token").setValue(token);
                    }
                });
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
        reference = getUserReference();
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

        // the plan not yet exist
        reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + trainee_id);
        reference.setValue("false");

        // save the UID of the trainer in trainee plan status field
        reference = database.getReference("Users/" + trainee_id + "/plan_status");
        reference.setValue(trainer_id);

        reference = database.getReference("Users/" + trainer_id + "/notifications");
        reference.setValue("You've got a new plan request");

        reference = database.getReference("Users/" + trainer_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String trainer_token = snapshot.child("token").getValue().toString();
                DatabaseReference trainee_reference = database.getReference("Users/"+trainee_id);
                trainee_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String trainee_name = snapshot.child("name").getValue(String.class).toString();
                        FcmNotificationsSender send_notification = new FcmNotificationsSender(trainer_token , "Easy Plan", "You Have got a new plan request from " + trainee_name,context,activity);
                        send_notification.SendNotifications();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createPlan(Plan plan) {
        getPlanReference(plan.getTrainee_id()).setValue(plan);
        getUserReference((plan.getTrainer_id() + "/my_trainees/" + plan.getTrainee_id())).setValue("true");
    }

    public void sendNotification (String from , String to, String message) {
        getUserReference(to).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String to_token = snapshot.child("token").getValue(String.class);
                getUserReference(from).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String from_name = snapshot.child("name").getValue(String.class);
                        FcmNotificationsSender send_notification = new FcmNotificationsSender(to_token , "Easy Plan", message +" "+ from_name,context,activity);
                        send_notification.SendNotifications();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
