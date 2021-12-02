package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.easyplan.Data.Trainee;
import com.example.easyplan.Data.Trainer;
import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

public class RegisterTrainerActivity extends AppCompatActivity {

    private Button register_trainer_btn;
    private ImageView register_trainer_image, register_trainer_upload;
    private EditText register_trainer_full_name, register_trainer_address, register_trainer_education;
    private EditText register_trainer_personal_page, register_trainer_age, register_trainer_cost, register_trainer_days;
    private RadioButton register_trainer_male_radio, register_trainer_female_radio;
    private CheckBox register_trainer_cardio, register_trainer_fitness, register_trainer_muscle, register_trainer_menu;
    private FirebaseAuth mAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainer);

        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");

        mAuth = FirebaseAuth.getInstance();
        register_trainer_btn = (Button) findViewById(R.id.register_trainer_btn);
        register_trainer_image = (ImageView) findViewById(R.id.register_trainer_image);
        register_trainer_upload = (ImageView) findViewById(R.id.register_trainer_upload);
        register_trainer_full_name = (EditText) findViewById(R.id.register_trainer_full_name);
        register_trainer_address = (EditText) findViewById(R.id.register_trainer_address);
        register_trainer_education = (EditText) findViewById(R.id.register_trainer_education);
        register_trainer_personal_page = (EditText) findViewById(R.id.register_trainer_personal_page);
        register_trainer_age = (EditText) findViewById(R.id.register_trainer_age);
        register_trainer_cost = (EditText) findViewById(R.id.register_trainer_cost);
        register_trainer_male_radio = (RadioButton) findViewById(R.id.register_trainer_male_radio);
        register_trainer_female_radio = (RadioButton) findViewById(R.id.register_trainer_female_radio);
        register_trainer_cardio = (CheckBox) findViewById(R.id.register_trainer_cardio);
        register_trainer_fitness = (CheckBox) findViewById(R.id.register_trainer_fitness);
        register_trainer_muscle = (CheckBox) findViewById(R.id.register_trainer_muscle);
        register_trainer_menu = (CheckBox) findViewById(R.id.register_trainer_menu);

    }

    public void register_trainer(View view) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String name = register_trainer_full_name.getText().toString();
                            String address = register_trainer_address.getText().toString();
                            int age =  Integer.parseInt(register_trainer_age.getText().toString());
                            int cost = Integer.parseInt(register_trainer_cost.getText().toString());
                            String education = register_trainer_education.getText().toString();
                            String personal_page = register_trainer_personal_page.getText().toString();
                            Vector<String> targets = new Vector<>();
                            if(register_trainer_muscle.isChecked()){
                                targets.add("Cardio");
                            }
                            if(register_trainer_muscle.isChecked()){
                                targets.add("Fitness");
                            }
                            if(register_trainer_muscle.isChecked()){
                                targets.add("Muscle");
                            }
                            if(register_trainer_muscle.isChecked()){
                                targets.add("Menu Nutrition");
                            }
                            String gender;
                            if (register_trainer_female_radio.isChecked()) {
                                gender = "female";
                            } else gender = "male";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
                            Trainer trainer =  new Trainer(name,address,gender,education,personal_page,age,cost,targets);
                            reference.setValue(trainer);
                            Intent move = new Intent(RegisterTrainerActivity.this , TrainerHomepageActivity.class);
                            startActivity(move);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterTrainerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}