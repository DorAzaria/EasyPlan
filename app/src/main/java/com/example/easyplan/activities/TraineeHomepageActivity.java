package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.R;
import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Plan;
import com.example.easyplan.data.Trainee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//////**********************************************////////////
////// This activity manages the trainee homepage
//////**********************************************////////////
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
    private FrameLayout trainee_homepage_notification;
    private String notification;
    private FirebaseData firebaseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_homepage);
        firebaseData = new FirebaseData();
        mAuth = FirebaseAuth.getInstance();
        notification = "";
        initFields();
        showProfileHeader();
        checkForNotifications();
        checkForPlan();
    }


//////**********************************************////////////
    private void initFields() {
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

        trainee_homepage_notification = (FrameLayout) findViewById(R.id.trainee_homepage_notification);
        trainee_homepage_notification.setVisibility(View.GONE);
    }


//////**********************************************////////////
    private void showProfileHeader() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users/" + mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainee trainee = snapshot.getValue(Trainee.class);
                trainee_homepage_name.setText(trainee.getName());
                trainee_homepage_age.setText(String.valueOf(trainee.getAge()));
                trainee_homepage_address.setText(trainee.getAddress());
                trainee_homepage_gender.setText(trainee.getGender());
                trainee_homepage_height.setText(trainee.getHeight());
                trainee_homepage_weight.setText(trainee.getWeight());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//////**********************************************////////////
    private void checkForNotifications() {
        notification = firebaseData.getNotification();
        if(firebaseData.checkForNewNotifications(notification)) {
            trainee_homepage_notification.setVisibility(View.VISIBLE);
        }
    }


//////**********************************************////////////
    private void checkForPlan() {
        reference = database.getReference("Plans/");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(mAuth.getUid())) {
                    showPlan();
                }
                else {
                    traineeWithoutPlan();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//////**********************************************////////////
    private void showPlan() {
        trainee_homepage_btn.setVisibility(View.VISIBLE);
        trainee_homepage_menu.setVisibility(View.VISIBLE);
        trainee_homepage_trains.setVisibility(View.VISIBLE);

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


//////**********************************************////////////
    private void traineeWithoutPlan() {
        // If trainee not waiting for the plan, so need to ask for new one (go to Targets)
        trainee_homepage_plan_btn.setVisibility(View.VISIBLE);

        // Checks if trainee is waiting for plan, and if he is waiting, then let them know.
        waitingScenario();
    }


//////**********************************************////////////
    private void waitingScenario() {
        // The plan isn't ready yet, but we want to get the trainer name
        DatabaseReference getStatusReference = database.getReference("Users/"+mAuth.getUid());

        getStatusReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plan_status = snapshot.child("plan_status").getValue(String.class);
                if(plan_status != null && !plan_status.isEmpty()) {

                    // status is the id of the trainer.
                    DatabaseReference getTrainerNameReference = database.getReference("Users/"+plan_status);
                    getTrainerNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String trainer_name = snapshot.child("name").getValue(String.class);
                            trainee_homepage_plan_btn.setText(trainer_name + " is working on your plan");
                            trainee_homepage_plan_btn.setClickable(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//////**********************************************////////////
    public void clickNotification(View view) {
        if(notification != null && !notification.isEmpty()) {

            final Dialog dialog = new Dialog(TraineeHomepageActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_error);

            TextView errors = dialog.findViewById(R.id.dialog_error_text);
            Button ok_btn = dialog.findViewById(R.id.dialog_ok);
            TextView title = dialog.findViewById(R.id.dialog_title);

            errors.setText(notification);
            title.setText("Notifications");

            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseData.clearNotification();
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }


//////**********************************************////////////
    public void startPlanButton(View view) {
        startActivity(new Intent(TraineeHomepageActivity.this, SelectTargetActivity.class));
    }


//////**********************************************////////////
    public void logout(View v) {
        mAuth.signOut();
        Intent move = new Intent(TraineeHomepageActivity.this , LoginActivity.class);
        startActivity(move);
    }

}