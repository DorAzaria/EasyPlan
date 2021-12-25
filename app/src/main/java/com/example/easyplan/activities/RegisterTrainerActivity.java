package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.data.FirebaseData;
import com.example.easyplan.data.Trainer;
import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterTrainerActivity extends AppCompatActivity {

    private Button register_trainer_btn;
    private ImageView register_trainer_image, register_trainer_upload;
    private EditText register_trainer_full_name, register_trainer_address, register_trainer_education;
    private EditText register_trainer_personal_page, register_trainer_age, register_trainer_cost, register_trainer_days;
    private RadioButton register_trainer_male_radio, register_trainer_female_radio;
    private CheckBox register_trainer_cardio, register_trainer_fitness, register_trainer_muscle, register_trainer_menu;
    private FirebaseAuth mAuth;
    private String email, password, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainer);

        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");

        mAuth = FirebaseAuth.getInstance();
        initFields();
    }


//////**********************************************////////////
    public void initFields() {
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

//////**********************************************////////////
///// Once presses this button, the process begins:
///// Checks for errors - prints with dialogs if any,
///// else, create the user (Auth) and register (save personal data).
///// then move to the homepage.
//////**********************************************////////////
    public void register_trainer(View view) {
        String error_information = checkInputs();
        if(error_information.isEmpty()) {
            createUser();
        }
        else {
            errorDialog(error_information);
        }
    }


//////**********************************************////////////
    private void createUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            registerUser();
                            Intent move = new Intent(RegisterTrainerActivity.this, TrainerHomepageActivity.class);
                            startActivity(move);
                        } else {
                            errorDialog("Authentication failed.");
                        }
                    }
                });
    }


//////**********************************************////////////
    private void registerUser() {
        String name = register_trainer_full_name.getText().toString();
        String address = register_trainer_address.getText().toString();
        int age = Integer.parseInt(register_trainer_age.getText().toString());
        int cost = Integer.parseInt(register_trainer_cost.getText().toString());
        String education = register_trainer_education.getText().toString();
        String personal_page = register_trainer_personal_page.getText().toString();
        ArrayList<String> targets = new ArrayList<>();
        if (register_trainer_cardio.isChecked()) {
            targets.add("Cardio");
        }
        if (register_trainer_fitness.isChecked()) {
            targets.add("Fitness");
        }
        if (register_trainer_muscle.isChecked()) {
            targets.add("Muscle");
        }
        if (register_trainer_menu.isChecked()) {
            targets.add("Menu Nutrition");
        }

        FirebaseData firebaseData = new FirebaseData();
        Trainer trainer = new Trainer(name, address, gender, "Trainer", education, personal_page, "",  age, cost, 3, 0, targets, new HashMap<String, String>());
        firebaseData.createTrainer(trainer);
    }


//////**********************************************////////////
////// checkInputs collects all errors as String and returns the errors.
////// Checks empty cases and more...
//////**********************************************////////////
    public String checkInputs() {
        String errors = "";

        String name = register_trainer_full_name.getText().toString();
        String address = register_trainer_address.getText().toString();
        String age =  register_trainer_age.getText().toString();
        String cost = register_trainer_cost.getText().toString();
        String education = register_trainer_education.getText().toString();
        String personal_page = register_trainer_personal_page.getText().toString();

        if(TextUtils.isEmpty(name)) {
            register_trainer_full_name.setError("Please insert your name.");
            errors += "Insert your name \n";
        }

        if(name.length() > 18) {
            register_trainer_full_name.setError("Your name is too long (max 18 letters)");
            errors += "Your name is too long (max 18 letters) \n";
        }

        if(TextUtils.isEmpty(address)) {
            register_trainer_address.setError("Please insert your address.");
            errors += "Insert your address \n";
        }

        if(address.length() > 25) {
            register_trainer_address.setError("Your address is too long (max 25 letters)");
            errors += "Your address is too long (max 25 letters) \n";
        }

        if(TextUtils.isEmpty(age)) {
            register_trainer_age.setError("Please insert your age.");
            errors += "Insert your age \n";
        }

        if(TextUtils.isEmpty(cost)) {
            register_trainer_age.setError("Please insert your cost.");
            errors += "Insert your cost \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) > 120) {
            register_trainer_age.setError("Age over 120 isn't allowed.");
            errors += "Age over 120 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(cost) && Integer.parseInt(cost) > 9000) {
            register_trainer_age.setError("Cost over 9000 isn't allowed.");
            errors += "Cost over 9000 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(education)) {
            register_trainer_education.setError("Please insert your education.");
            errors += "Insert your education \n";
        }

        if(TextUtils.isEmpty(personal_page)) {
            register_trainer_personal_page.setError("Please insert your personal page.");
            errors += "Insert your personal page \n";
        }

        int check_multichecks = 0;
        if (register_trainer_cardio.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_fitness.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_muscle.isChecked()) {
            check_multichecks++;
        }
        if (register_trainer_menu.isChecked()) {
            check_multichecks++;
        }

        if(check_multichecks == 0) {
            errors += "You must check at least one \n";
        }

        if(gender.isEmpty()) {
            errors += "You must choose a gender \n";
        }

        return errors;
    }


//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(RegisterTrainerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_error);

        TextView errors = dialog.findViewById(R.id.dialog_error_text);
        Button ok_btn = dialog.findViewById(R.id.dialog_ok);

        errors.setText(error);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.register_trainer_male_radio:
                if (checked)
                    gender = "Male";
                    break;
            case R.id.register_trainer_female_radio:
                if (checked)
                    gender = "Female";
                    break;
        }
    }
}