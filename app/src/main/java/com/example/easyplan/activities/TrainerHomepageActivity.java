package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
    private Button trainer_homepage_btn;
    private String trainerId,  trainer_id, notification_message;
    private ImageView trainee_homepage_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             trainerId = extras.getString("myId");
        }
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
        notification_message = "";
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Intent move = getIntent();
        trainer_id = "";

        // trainer want to see my homepage - he choose me
        if (move.hasExtra("trainer id from firebase")) {
            trainer_id = move.getStringExtra("trainer id from firebase");
            trainer_list_menu.setVisibility(View.GONE);

//            trainer_start_plan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Dialog dialog = new Dialog(TrainerHomepageActivity.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(true);
//                    dialog.setContentView(R.layout.dialog_error);
//
//                    TextView errors = dialog.findViewById(R.id.dialog_error_text);
//                    TextView title = dialog.findViewById(R.id.dialog_title);
//                    Button ok_btn = dialog.findViewById(R.id.dialog_ok);
//
//                    title.setText("New plan is on the way!");
//                    errors.setText("Your trainer just received your request.\nIt could take a while, please be patient.\nWe'll let you know when your plan is ready.");
//
//                    ok_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            reference = database.getReference("Users/" + trainer_id + "/my_trainees/" + mAuth.getUid());
//                            reference.setValue("false");
//
//                            reference = database.getReference("Users/" + mAuth.getUid() + "/plan_status");
//                            reference.setValue(trainer_id);
//
//                            reference = database.getReference("Users/" + trainer_id + "/notifications");
//
//                            DatabaseReference ref_trainer = database.getReference("Users/"+mAuth.getUid());
//
//                            ref_trainer.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    reference.setValue("You've got a new plan request from " + snapshot.child("name").getValue(String.class));
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                }
//                            });
//
//                            reference = database.getReference("Users/" + mAuth.getUid() + "/notifications");
//
//                            ref_trainer = database.getReference("Users/"+trainer_id);
//
//                            ref_trainer.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    reference.setValue("Your plan request just sent to " + snapshot.child("name").getValue(String.class));
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                }
//                            });
//
//                            startActivity(new Intent(TrainerHomepageActivity.this, TraineeHomepageActivity.class));
//                        }
//                    });
//
//
//                    dialog.show();
//
//                }
//            });
        }
        else {
            trainer_id = mAuth.getUid();
            trainer_start_plan.setVisibility(View.GONE);
//
//            reference = database.getReference("Users/" + trainer_id);
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    notification_message = snapshot.child("notifications").getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//            if(notification_message != null && !notification_message.isEmpty()) {
//                trainee_homepage_notification.setVisibility(View.VISIBLE);
//            }

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


        trainer_list_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                reference = database.getReference("Users/" + trainer_id);
//                reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        notification_message = snapshot.child("notifications").getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                if(notification_message != null && !notification_message.isEmpty()) {
//
//                    final Dialog dialog = new Dialog(TrainerHomepageActivity.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(true);
//                    dialog.setContentView(R.layout.dialog_error);
//
//                    TextView errors = dialog.findViewById(R.id.dialog_error_text);
//                    Button ok_btn = dialog.findViewById(R.id.dialog_ok);
//                    TextView title = dialog.findViewById(R.id.dialog_title);
//
//                    errors.setText(notification_message);
//                    title.setText("Notifications");
//
//                    ok_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            DatabaseReference reference2 = database.getReference("Users/" + mAuth.getUid() + "/notifications");
//                            reference2.setValue("");
//
//                            dialog.dismiss();
//                            Intent i = new Intent(TrainerHomepageActivity.this, TraineeListActivity.class);
//                            i.putExtra("myId",trainerId);
//                            startActivity(i);
//                        }
//                    });
//
//
//                    dialog.show();
//
//                }


                Intent i = new Intent(TrainerHomepageActivity.this, TraineeListActivity.class);
                i.putExtra("myId",trainerId);
                startActivity(i);
            }
        });

    }

    public void logout (View v) {
        mAuth.signOut();
        Intent move = new Intent(TrainerHomepageActivity.this , LoginActivity.class);
        startActivity(move);
    }
}