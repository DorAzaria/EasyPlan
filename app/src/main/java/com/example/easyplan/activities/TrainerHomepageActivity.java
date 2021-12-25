package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Trainer;
import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

//////**********************************************////////////
////// This activity manages the trainer homepage
//////**********************************************////////////
public class TrainerHomepageActivity extends AppCompatActivity {

    private ImageView trainer_homepage_picture, trainer_list_menu;
    private TextView trainer_homepage_name, trainer_homepage_rate;
    private TextView trainer_homepage_cost, trainer_homepage_days,trainer_homepage_duration;
    private ImageView trainer_homepage_fitness, trainer_homepage_muscle, trainer_homepage_cardio, trainer_homepage_menu;
    private Button trainer_start_plan;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String trainer_id;
    private ImageView trainee_homepage_notification;
    private FirebaseData firebaseData;
    private boolean trainer_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);

        initFields();

        Intent move = getIntent();
        trainer_id = "";
        trainer_flag = false;

        // trainee want to see my homepage - he choose me
        if(move.hasExtra("trainer id from firebase")) {
            trainer_id = move.getStringExtra("trainer id from firebase");
            trainer_list_menu.setVisibility(View.GONE);
            trainer_flag = true;

        }
        else {
            trainer_start_plan.setVisibility(View.GONE);
            trainer_id = firebaseData.getID();
            String notification = firebaseData.getNotification();
            if(firebaseData.checkForNewNotifications(notification)) {
                trainee_homepage_notification.setVisibility(View.VISIBLE);
            }
        }
        getProfileData();
    }


    private void initFields() {
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
        trainee_homepage_notification = (ImageView) findViewById(R.id.trainee_homepage_notification);
        trainee_homepage_notification.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseData = new FirebaseData();
    }


    private void getProfileData() {
        reference = database.getReference("Users/" + trainer_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainer trainer = snapshot.getValue(Trainer.class);
                trainer_homepage_name.setText(trainer.getName());
                trainer_homepage_cost.setText(String.valueOf(trainer.getCost()));
                List<String> targets = trainer.getTargets();
                Map<String, String> my_trainees = trainer.getMy_trainees();
                if(targets != null) {
                    if (!targets.contains("Fitness"))
                        trainer_homepage_fitness.setVisibility(View.GONE);
                    if (!targets.contains("Muscle"))
                        trainer_homepage_muscle.setVisibility(View.GONE);
                    if (!targets.contains("Cardio"))
                        trainer_homepage_cardio.setVisibility(View.GONE);
                    if (!targets.contains("Menu Nutrition"))
                        trainer_homepage_menu.setVisibility(View.GONE);
                }
                if(my_trainees != null && trainer_flag) {
                    for (String runner : my_trainees.values()) {
                        if (runner.equals("false")) {
                            trainee_homepage_notification.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
                // because trainer see my profile - remove the menu
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void trainee_list(View view) {
        String notification = firebaseData.getNotification();
        if(firebaseData.checkForNewNotifications(notification)) {
            makeNotificationsDialog(notification);
        }
        Intent move = new Intent(TrainerHomepageActivity.this, TraineeListActivity.class);
        startActivity(move);
    }


    public void logout(View v) {
        mAuth.signOut();
        Intent move = new Intent(TrainerHomepageActivity.this , LoginActivity.class);
        startActivity(move);
    }


    public void start_plan(View view) {
        newPlanDialog();
    }

    private void makeNotificationsDialog(String my_notification) {
        final Dialog dialog = new Dialog(TrainerHomepageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_error);

        TextView errors = dialog.findViewById(R.id.dialog_error_text);
        Button ok_btn = dialog.findViewById(R.id.dialog_ok);
        TextView title = dialog.findViewById(R.id.dialog_title);

        errors.setText(my_notification);
        title.setText("Notifications");

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseData.clearNotification();

                dialog.dismiss();

                startActivity(new Intent(TrainerHomepageActivity.this, TraineeListActivity.class));
            }
        });


        dialog.show();
    }


    private void newPlanDialog() {

        final Dialog dialog = new Dialog(TrainerHomepageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_error);

        TextView errors = dialog.findViewById(R.id.dialog_error_text);
        TextView title = dialog.findViewById(R.id.dialog_title);
        Button ok_btn = dialog.findViewById(R.id.dialog_ok);

        title.setText("New plan is on the way!");
        errors.setText("Your trainer just received your request.\nIt could take a while, please be patient.\nWe'll let you know when your plan is ready.");

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String trainee_id = firebaseData.getID();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                firebaseData.sendPlanRequest(trainee_id, trainer_id);
                startActivity(new Intent(TrainerHomepageActivity.this, TraineeHomepageActivity.class));
            }
        });


        dialog.show();
    }

}