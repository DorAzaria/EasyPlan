package com.example.easyplan.data;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.messaging.RemoteMessage;
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


//////**********************************************////////////
////// Save a trainer user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainer(Trainer trainer) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
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


//////**********************************************////////////
////// Save a trainee user in Firebase using his personal id (mAuth.getUid)
//////**********************************************////////////
    public void createTrainee(Trainee trainee) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
        reference.setValue(trainee);

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
                        FcmNotificationsSender send_notification = new FcmNotificationsSender(trainer_token , "Easy Plan", "You Have a request for plan from " + trainee_name,context,activity);
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

//    public void createPlan(Plan plan, String trainer_id, String trainee_id) {
//        reference = database.getReference("Plans/" + trainee_id);
//        reference.setValue(plan);
//        reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + trainee_id);
//        reference.setValue("true");
//    }

}
