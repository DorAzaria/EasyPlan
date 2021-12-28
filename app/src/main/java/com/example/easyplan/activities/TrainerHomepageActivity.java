package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Trainer;
import com.example.easyplan.R;
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
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

//////**********************************************////////////
////// This activity manages the trainer homepage
//////**********************************************////////////
public class TrainerHomepageActivity extends AppCompatActivity {
    private CircleImageView trainer_homepage_picture;
    private ImageView trainer_list_menu;
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

    private ProgressDialog progressDialog;
    private StorageReference storageReference;

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

        }
        else {
            trainer_start_plan.setVisibility(View.GONE);
            trainer_id = firebaseData.getID();
            trainer_flag = true;
            String notification = firebaseData.getNotification();
            if(firebaseData.checkForNewNotifications(notification)) {
                trainee_homepage_notification.setVisibility(View.VISIBLE);
            }
        }
        getProfileData();

    }




    private void initFields() {
        trainer_homepage_name = (TextView) findViewById(R.id.trainer_homepage_name);
        trainer_homepage_picture = (CircleImageView) findViewById(R.id.trainer_homepage_picture);
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
        trainer_homepage_rate = (TextView) findViewById(R.id.trainer_homepage_rate);
        trainee_homepage_notification.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseData = new FirebaseData();
    }


    private void getProfileData() {
        showImage();

        reference = database.getReference("Users/" + trainer_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainer trainer = snapshot.getValue(Trainer.class);
                trainer_homepage_name.setText(trainer.getName());
                trainer_homepage_rate.setText(new DecimalFormat("#.#").format(trainer.getRate()));
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

    private void showImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();


        storageReference = FirebaseStorage.getInstance().getReference("images/"+trainer_id);
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            storageReference.getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Bitmap bitmap = BitmapFactory.decodeFile(local_file.getAbsolutePath());
                    trainer_homepage_picture.setImageBitmap(bitmap);
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
        move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(move);
        TrainerHomepageActivity.this.finish();
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
                String trainee_id = firebaseData.getID();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseData fd = new FirebaseData();
                fd.setActivity(TrainerHomepageActivity.this);
                fd.setContext(v.getContext());
                fd.sendPlanRequest(trainee_id, trainer_id);
                startActivity(new Intent(TrainerHomepageActivity.this, TraineeHomepageActivity.class));
                dialog.dismiss();

            }
        });


        dialog.show();
    }

    public void showImageDialog(View view) {

        final Dialog dialog = new Dialog(TrainerHomepageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_image);

        CircleImageView picture = dialog.findViewById(R.id.dialog_profile_picture);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();


        storageReference = FirebaseStorage.getInstance().getReference("images/"+trainer_id);
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

    private void endPlan(View view) {

    }

}