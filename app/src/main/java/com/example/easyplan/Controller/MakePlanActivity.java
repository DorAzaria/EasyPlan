package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.Model.FcmNotificationsSender;
import com.example.easyplan.Model.FirebaseData;
import com.example.easyplan.Model.Plan;
import com.example.easyplan.Model.Trainee;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MakePlanActivity extends AppCompatActivity {
    private CircleImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight,make_plan_train_number;
    private EditText make_plan_train_time1, make_plan_train_exer1, make_plan_train_time2, make_plan_train_exer2, make_plan_train_time3, make_plan_train_exer3;
    private EditText make_plan_day_1, make_plan_day_2, make_plan_day_3, make_plan_day_4;
    private EditText make_plan_day_5, make_plan_day_6, make_plan_cheat_day;
    private ImageView make_plan_menu;
    private Button make_plan_submit_btn;
    private String trainee_id;
    private FirebaseData model;
    private ProgressDialog progressDialog;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        Intent move = getIntent();
        trainee_id = "";
        if(move.hasExtra("trainee id from firebase")) {
            trainee_id = move.getStringExtra("trainee id from firebase");
        }
        initFields();
        setTraineeInfo(); // show trainee header info (at the top)
        checkForPlan(); // if the plan exists so hide the "Start Plan" button
        // make_plan(): is 'OnClick' method of plan's creation.
    }


    private void initFields() {
        trainee_homepage_picture = (CircleImageView) findViewById(R.id.trainee_homepage_picture);
        trainee_homepage_name = (TextView) findViewById(R.id.trainee_homepage_name);
        trainee_homepage_address = (TextView) findViewById(R.id.trainee_homepage_address);
        trainee_homepage_gender = (TextView) findViewById(R.id.trainee_homepage_gender);
        trainee_homepage_age = (TextView) findViewById(R.id.trainee_homepage_age);
        trainee_homepage_height = (TextView) findViewById(R.id.trainee_homepage_height);
        trainee_homepage_weight = (TextView) findViewById(R.id.trainee_homepage_weight);

        make_plan_train_number = (TextView) findViewById(R.id.make_plan_train_number);
        make_plan_train_time1 = (EditText) findViewById(R.id.make_plan_train_time1); // train 1
        make_plan_train_exer1 = (EditText) findViewById(R.id.make_plan_train_exer1);
        make_plan_train_time2 = (EditText) findViewById(R.id.make_plan_train_time2); // train 2
        make_plan_train_exer2 = (EditText) findViewById(R.id.make_plan_train_exer2);
        make_plan_train_time3 = (EditText) findViewById(R.id.make_plan_train_time3); // train 3
        make_plan_train_exer3 = (EditText) findViewById(R.id.make_plan_train_exer3);
        make_plan_day_1 = (EditText) findViewById(R.id.make_plan_day_1);
        make_plan_day_2 = (EditText) findViewById(R.id.make_plan_day_2);
        make_plan_day_3 = (EditText) findViewById(R.id.make_plan_day_3);
        make_plan_day_4 = (EditText) findViewById(R.id.make_plan_day_4);
        make_plan_day_5 = (EditText) findViewById(R.id.make_plan_day_5);
        make_plan_day_6 = (EditText) findViewById(R.id.make_plan_day_6);
        make_plan_cheat_day = (EditText) findViewById(R.id.make_plan_cheat_day);
        make_plan_submit_btn = (Button) findViewById(R.id.make_plan_submit_btn);
        make_plan_menu = (ImageView) (findViewById(R.id.make_plan_menu));
        model = new FirebaseData();
    }

    //////********************if the plan exists so hide the "Start Plan" button*************************////////////
    private void checkForPlan() {
        model.getPlanReference(trainee_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class) != null) {
                    make_plan_submit_btn.setVisibility(View.GONE);
                    show_plan();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//////********************show trainee header info (at the top)*************************////////////
    private void setTraineeInfo() {
        showImage();
        model.getUserReference(trainee_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Trainee trainee = snapshot.getValue(Trainee.class);
                trainee_homepage_name.setText(trainee.getName());
                trainee_homepage_address.setText(trainee.getAddress());
                trainee_homepage_gender.setText(trainee.getGender());
                trainee_homepage_age.setText(String.valueOf(trainee.getAge()));
                trainee_homepage_height.setText(trainee.getHeight());
                trainee_homepage_weight.setText(trainee.getWeight());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//////***********************load image of the trainee and show on the header**********************////////////
    private void showImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            model.getStorageReference(trainee_id).getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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





//////***********************is 'OnClick' method of plan's creation + send notification**********************////////////
    public void make_plan(View view) {

        String check = checkInputs();
        if(check.isEmpty()) {
            String trainer_id = model.getID();
            String train_time1 = make_plan_train_time1.getText().toString();
            String train_exer1 = make_plan_train_exer1.getText().toString();
            String train_time2 = make_plan_train_time2.getText().toString();
            String train_exer2 = make_plan_train_exer2.getText().toString();
            String train_time3 = make_plan_train_time3.getText().toString();
            String train_exer3 = make_plan_train_exer3.getText().toString();
            String day1 = make_plan_day_1.getText().toString();
            String day2 = make_plan_day_2.getText().toString();
            String day3 = make_plan_day_3.getText().toString();
            String day4 = make_plan_day_4.getText().toString();
            String day5 = make_plan_day_5.getText().toString();
            String day6 = make_plan_day_6.getText().toString();
            String cheat = make_plan_cheat_day.getText().toString();


            HashMap<String, HashMap<String, String>> trains = new HashMap<>();
            trains.put("1", new HashMap<>());
            trains.get("1").put("time", train_time1);
            trains.get("1").put("exercise", train_exer1);
            trains.put("2", new HashMap<>());
            trains.get("2").put("time", train_time2);
            trains.get("2").put("exercise", train_exer2);
            trains.put("3", new HashMap<>());
            trains.get("3").put("time", train_time3);
            trains.get("3").put("exercise", train_exer3);

            ArrayList<String> menu = new ArrayList<>();
            menu.add(day1);
            menu.add(day2);
            menu.add(day3);
            menu.add(day4);
            menu.add(day5);
            menu.add(day6);
            menu.add(cheat);

            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
            String strDate = df.format(Calendar.getInstance().getTime());


            model.createPlan(new Plan(trainer_id, trainee_id, trains, menu, strDate));
            model.setActivity(this);
            model.setContext(getApplicationContext());
            model.sendNotification(trainer_id , trainee_id , "You Have got a new plan from");

            Intent move = new Intent(MakePlanActivity.this, TrainerHomepageActivity.class);
            move.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(move);
            MakePlanActivity.this.finish();
        }

        else {
            final Dialog dialog = new Dialog(MakePlanActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_error);

            TextView errors = dialog.findViewById(R.id.dialog_error_text);
            Button ok_btn = dialog.findViewById(R.id.dialog_ok);

            errors.setText(check);
            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        }
    }

//////***********************if the plan is made then just show the current data of the plan*********************////////////
    private void show_plan() {
        model.getPlanReference(trainee_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                make_plan_train_time1.setText(snapshot.child("trains").child("1").child("time").getValue(String.class));
                make_plan_train_exer1.setText(snapshot.child("trains").child("1").child("exercise").getValue(String.class));
                make_plan_train_time2.setText(snapshot.child("trains").child("2").child("time").getValue(String.class));
                make_plan_train_exer2.setText(snapshot.child("trains").child("2").child("exercise").getValue(String.class));
                make_plan_train_time3.setText(snapshot.child("trains").child("3").child("time").getValue(String.class));
                make_plan_train_exer3.setText(snapshot.child("trains").child("3").child("exercise").getValue(String.class));
                make_plan_day_1.setText(snapshot.child("menu").child("0").getValue(String.class));
                make_plan_day_2.setText(snapshot.child("menu").child("1").getValue(String.class));
                make_plan_day_3.setText(snapshot.child("menu").child("2").getValue(String.class));
                make_plan_day_4.setText(snapshot.child("menu").child("3").getValue(String.class));
                make_plan_day_5.setText(snapshot.child("menu").child("4").getValue(String.class));
                make_plan_day_6.setText(snapshot.child("menu").child("5").getValue(String.class));
                make_plan_cheat_day.setText(snapshot.child("menu").child("6").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






//////***********************check relevant inputs*********************////////////
    private String checkInputs() {
        String errors = "";

        String train_time1 = make_plan_train_time1.getText().toString();
        String train_exer1 = make_plan_train_exer1.getText().toString();

        String train_time2 = make_plan_train_time2.getText().toString();
        String train_exer2 = make_plan_train_exer2.getText().toString();

        String train_time3 = make_plan_train_time3.getText().toString();
        String train_exer3 = make_plan_train_exer3.getText().toString();

        String day1 = make_plan_day_1.getText().toString();
        String day2 = make_plan_day_2.getText().toString();
        String day3 = make_plan_day_3.getText().toString();
        String day4 = make_plan_day_4.getText().toString();
        String day5 = make_plan_day_5.getText().toString();
        String day6 = make_plan_day_6.getText().toString();
        String cheat = make_plan_cheat_day.getText().toString();

        if(TextUtils.isEmpty(train_time1)) {
            make_plan_train_time1.setError("Please insert time.");
            errors += "Insert 1st train time \n";
        }

        if(TextUtils.isEmpty(train_exer1)) {
            make_plan_train_exer1.setError("Please insert exercise.");
            errors += "Insert 1st train exercise \n";
        }

        if(TextUtils.isEmpty(train_time2)) {
            make_plan_train_time2.setError("Please insert time.");
            errors += "Insert 2nd train time \n";
        }

        if(TextUtils.isEmpty(train_exer2)) {
            make_plan_train_exer2.setError("Please insert exercise.");
            errors += "Insert 2nd train exercise \n";
        }

        if(TextUtils.isEmpty(train_time3)) {
            make_plan_train_time3.setError("Please insert time.");
            errors += "Insert 3rd train time \n";
        }

        if(TextUtils.isEmpty(train_exer3)) {
            make_plan_train_exer3.setError("Please insert exercise.");
            errors += "Insert 3rd train exercise \n";
        }

        if(TextUtils.isEmpty(day1)) {
            make_plan_day_1.setError("Please insert a nutrition.");
            errors += "Insert day 1 nutrition \n";
        }

        if(TextUtils.isEmpty(day2)) {
            make_plan_day_2.setError("Please insert a nutrition.");
            errors += "Insert day 2 nutrition \n";
        }

        if(TextUtils.isEmpty(day3)) {
            make_plan_day_3.setError("Please insert a nutrition.");
            errors += "Insert day 3 nutrition \n";
        }

        if(TextUtils.isEmpty(day4)) {
            make_plan_day_4.setError("Please insert a nutrition.");
            errors += "Insert day 4 nutrition \n";
        }

        if(TextUtils.isEmpty(day5)) {
            make_plan_day_5.setError("Please insert a nutrition.");
            errors += "Insert day 5 nutrition \n";
        }

        if(TextUtils.isEmpty(day6)) {
            make_plan_day_6.setError("Please insert a nutrition.");
            errors += "Insert day 6 nutrition \n";
        }

        if(TextUtils.isEmpty(cheat)) {
            make_plan_day_1.setError("Please insert a nutrition.");
            errors += "Insert cheat day nutrition \n";
        }

        return errors;
    }







//////***********************show the image header in big version***********************////////////
    public void showImageDialog(View view) {

        final Dialog dialog = new Dialog(MakePlanActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_image);

        CircleImageView picture = dialog.findViewById(R.id.dialog_profile_picture);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            File local_file = File.createTempFile("tempfile", ".jpg" );
            model.getStorageReference(trainee_id).getFile(local_file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
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






//////***********************go back to the trainee list view***********************////////////
    public void trainee_list_menu (View v) {
        Intent move = new Intent(MakePlanActivity.this , TraineeListActivity.class);
        startActivity(move);
    }







}