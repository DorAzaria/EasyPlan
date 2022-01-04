package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.R;
import com.example.easyplan.Model.FirebaseData;
import com.example.easyplan.Model.Trainee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

//////**********************************************////////////
////// This activity manages the trainee homepage
//////**********************************************////////////
public class TraineeHomepageActivity extends AppCompatActivity {
    private CircleImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight;
    private TextView time_of_train_1 , time_of_train_2 , time_of_train_3;
    private TextView exercise_1 , exercise_2 , exercise_3;
    private TextView trainee_homepage_day_1, trainee_homepage_day_2, trainee_homepage_day_3, trainee_homepage_day_4;
    private TextView trainee_homepage_day_5, trainee_homepage_day_6, trainee_homepage_cheat_day;
    private Button trainee_homepage_btn, trainee_homepage_plan_btn , trainee_homepage_phone_btn , trainee_homepage_end_plan_btn;
    private ConstraintLayout trainee_homepage_menu, trainee_homepage_trains;
    private FirebaseData model;
    private String Flag;

    private ProgressDialog progressDialog;
    private StorageReference storageReference;




//////**********************************************////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_homepage);
        model = new FirebaseData();

        Flag = "false";
        Intent move = getIntent();

        if(move.hasExtra("Flag")){
            Flag = move.getStringExtra("Flag");
        }

        initFields();

        showProfileHeader();

        if(Flag.equals("false")){
            checkForPlan();
        }else{
            traineeWithoutPlan();
        }

    }






//////**********************************************////////////
    private void initFields() {
        trainee_homepage_picture = (CircleImageView) findViewById(R.id.trainee_homepage_picture);

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
        trainee_homepage_phone_btn = (Button) findViewById(R.id.trainee_homepage_phone_btn);

        trainee_homepage_btn = (Button) findViewById(R.id.trainee_homepage_btn);
        trainee_homepage_plan_btn = (Button) findViewById(R.id.trainee_homepage_plan_btn);
        trainee_homepage_end_plan_btn = (Button) findViewById(R.id.trainee_homepage_end_plan_btn);
        trainee_homepage_menu = (ConstraintLayout) findViewById(R.id.trainee_homepage_menu);
        trainee_homepage_trains = (ConstraintLayout) findViewById(R.id.trainee_homepage_trains);
        trainee_homepage_end_plan_btn.setVisibility(View.GONE);
        trainee_homepage_btn.setVisibility(View.GONE);
        trainee_homepage_menu.setVisibility(View.GONE);
        trainee_homepage_trains.setVisibility(View.GONE);
        trainee_homepage_plan_btn.setVisibility(View.GONE);
        trainee_homepage_phone_btn.setVisibility(View.GONE);

    }





    

//////**********************************************////////////
    private void showProfileHeader() {

        showImage();

        model.getUserReference().addValueEventListener(new ValueEventListener() {
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
    private void showImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();


        storageReference = model.getStorageReference();
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            storageReference.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    trainee_homepage_picture.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    



//////**********************************************////////////
    private void checkForPlan() {
        DatabaseReference reference = model.getPlanReference(model.getID());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.getValue() != null) {
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
        trainee_homepage_phone_btn.setVisibility(View.VISIBLE);
        trainee_homepage_end_plan_btn.setVisibility(View.VISIBLE);

        DatabaseReference reference = model.getPlanReference(model.getID());
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


        model.getUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plan_status = snapshot.child("plan_status").getValue(String.class);
                if(plan_status != null && !plan_status.isEmpty()) {

                    // status is the id of the trainer.
                    DatabaseReference getTrainerNameReference = model.getDatabase().getReference("Users/"+plan_status);
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
    public void showImageDialog(View view) {

        final Dialog dialog = new Dialog(TraineeHomepageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_image);

        CircleImageView picture = dialog.findViewById(R.id.dialog_profile_picture);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();


        storageReference = model.getStorageReference();
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            storageReference.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    picture.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        Button ok_btn = dialog.findViewById(R.id.dialog_image_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    




//////**********************************************////////////
    public void startPlanButton(View view) {
        startActivity(new Intent(TraineeHomepageActivity.this, SelectTargetActivity.class));
    }





//////**********************************************////////////
    public void logout(View v) {
        model.logout();
        Intent move = new Intent(TraineeHomepageActivity.this , LoginActivity.class);
        move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(move);
        TraineeHomepageActivity.this.finish();
    }






//////**********************************************////////////
    public void sendEmail(View view) {


        model.getUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plan_status = snapshot.child("plan_status").getValue(String.class);
                String trainee_name = snapshot.child("name").getValue(String.class);

                if(plan_status != null && !plan_status.isEmpty()) {

                    // status is the id of the trainer.
                    DatabaseReference getTrainerNameReference = model.getDatabase().getReference("Users/"+plan_status);
                    getTrainerNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String email_trainer = snapshot.child("email").getValue(String.class);
                            String name = snapshot.child("name").getValue(String.class);

                            String[] TO = {email_trainer};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");


                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New message from " + trainee_name + " || EasyPlan");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi "+name+",\n");

                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(TraineeHomepageActivity.this,
                                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }
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
    public void callPhone(View view) {

        model.getUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String plan_status = snapshot.child("plan_status").getValue(String.class);
                if(plan_status != null && !plan_status.isEmpty()) {

                    // status is the id of the trainer.
                    DatabaseReference getTrainerNameReference = model.getDatabase().getReference("Users/"+plan_status);
                    getTrainerNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // getting phone number from edit text
                            // and changing it to String
                            String phone_trainer = snapshot.child("phone_number").getValue(String.class);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+phone_trainer));
                            view.getContext().startActivity(intent);
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






    public void endPlan(View view) {

        model.getUserReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String trainer_id = snapshot.child("plan_status").getValue(String.class);
               Intent move = new Intent(TraineeHomepageActivity.this , EndPlanActivity.class);
               move.putExtra("trainee_id", model.getID());
               move.putExtra("trainer_id", trainer_id);
               startActivity(move);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}