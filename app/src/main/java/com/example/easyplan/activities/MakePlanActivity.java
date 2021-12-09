package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyplan.Data.Plan;
import com.example.easyplan.Data.Trainer;
import com.example.easyplan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Vector;

public class MakePlanActivity extends AppCompatActivity {
    private ImageView trainee_homepage_picture;
    private TextView trainee_homepage_name, trainee_homepage_address, trainee_homepage_gender;
    private TextView trainee_homepage_age, trainee_homepage_height, trainee_homepage_weight,make_plan_train_number;

    private EditText make_plan_train_time1, make_plan_train_exer1, make_plan_train_time2, make_plan_train_exer2,
            make_plan_train_time3, make_plan_train_exer3;

    private EditText make_plan_day_1, make_plan_day_2, make_plan_day_3, make_plan_day_4;
    private EditText make_plan_day_5, make_plan_day_6, make_plan_cheat_day;

    private Button make_plan_submit_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        mAuth = FirebaseAuth.getInstance();

        trainee_homepage_picture = (ImageView) findViewById(R.id.trainee_homepage_picture);
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

    }

    public void make_plan(View view) {
        String trainer_id = mAuth.getUid();
        String trainee_id; /// need to get this id by intent putExtra from TraineeListActivity

        String train_time1 = make_plan_train_time1.getText().toString();
        String train_exer1 = make_plan_train_exer1.getText().toString();

        String train_time2 = make_plan_train_time2.getText().toString();
        String train_exer2 = make_plan_train_exer2.getText().toString();

        String train_time3 = make_plan_train_time3.getText().toString();
        String train_exer3 = make_plan_train_exer3.getText().toString();

        HashMap<String, HashMap<String,String>> trains = new HashMap<>();

        trains.put("1", new HashMap<>());
        trains.get("1").put("time", train_time1);
        trains.get("1").put("exercise", train_exer1);

        trains.put("2", new HashMap<>());
        trains.get("2").put("time", train_time2);
        trains.get("2").put("exercise", train_exer2);

        trains.put("3", new HashMap<>());
        trains.get("3").put("time", train_time3);
        trains.get("3").put("exercise", train_exer3);

        Vector<String> menu = new Vector<>();

        String day1 = make_plan_day_1.getText().toString();
        String day2 = make_plan_day_2.getText().toString();
        String day3 = make_plan_day_3.getText().toString();
        String day4 = make_plan_day_4.getText().toString();
        String day5 = make_plan_day_5.getText().toString();
        String day6 = make_plan_day_6.getText().toString();
        String cheat = make_plan_cheat_day.getText().toString();

        menu.add(day1);
        menu.add(day2);
        menu.add(day3);
        menu.add(day4);
        menu.add(day5);
        menu.add(day6);
        menu.add(cheat);

        Plan plan = new Plan(trainer_id, "Ok7J0x3grFYwqZBzJzGsYqmU1LI3", trains, menu);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Plans/" + "Ok7J0x3grFYwqZBzJzGsYqmU1LI3");
        reference.setValue(plan);

        reference = database.getReference("Users/" + mAuth.getUid() + "/my_trainees"+"/Ok7J0x3grFYwqZBzJzGsYqmU1LI3");
        reference.setValue("true");

        startActivity(new Intent(MakePlanActivity.this, TraineeHomepageActivity.class));
    }
}