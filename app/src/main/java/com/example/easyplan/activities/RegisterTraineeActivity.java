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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.Data.Trainee;
import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterTraineeActivity extends AppCompatActivity {

    private Button register_trainee_btn;
    private ImageView register_trainee_image, register_trainee_upload;
    private EditText register_trainee_full_name, register_trainee_address, register_trainee_age;
    private EditText register_trainee_height,register_trainee_weight;
    private RadioButton register_trainee_male_radio, register_trainee_female_radio;
    private FirebaseAuth mAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_trainee);
        //get email and password from register by type activity
        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");

        mAuth = FirebaseAuth.getInstance();
        register_trainee_btn = (Button) findViewById(R.id.register_trainee_btn);
        register_trainee_image = (ImageView) findViewById(R.id.register_trainee_image);
        register_trainee_upload = (ImageView) findViewById(R.id.register_trainee_upload);
        register_trainee_full_name = (EditText) findViewById(R.id.register_trainee_full_name2);
        register_trainee_address = (EditText) findViewById(R.id.register_trainee_address);
        register_trainee_age = (EditText) findViewById(R.id.register_trainee_age);
        register_trainee_height = (EditText) findViewById(R.id.register_trainee_height);
        register_trainee_weight = (EditText) findViewById(R.id.register_trainee_weight);
        register_trainee_male_radio = (RadioButton) findViewById(R.id.register_trainee_male_radio);
        register_trainee_female_radio = (RadioButton) findViewById(R.id.register_trainee_female_radio);


    }



    public void register_trainee(View view) {
        String check = checkInputs();
        if(check.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                String name = register_trainee_full_name.getText().toString();
                                String address = register_trainee_address.getText().toString();
                                String age = register_trainee_age.getText().toString();
                                String height = register_trainee_height.getText().toString();
                                String weight = register_trainee_weight.getText().toString();
                                String gender;
                                if (register_trainee_female_radio.isChecked()) {
                                    gender = "female";
                                } else gender = "male";

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
                                Trainee trainee = new Trainee(name, address, height, weight, gender, Integer.parseInt(age), "", "");
                                reference.setValue(trainee);
                                Intent move = new Intent(RegisterTraineeActivity.this, TraineeHomepageActivity.class);
                                startActivity(move);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterTraineeActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else {
            final Dialog dialog = new Dialog(RegisterTraineeActivity.this);
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

    public String checkInputs() {
        String errors = "";

        String name = register_trainee_full_name.getText().toString();
        String address = register_trainee_address.getText().toString();
        String age =  register_trainee_age.getText().toString();
        String height = register_trainee_height.getText().toString();
        String weight = register_trainee_weight.getText().toString();


        if(TextUtils.isEmpty(name)) {
            register_trainee_full_name.setError("Please insert your name.");
            errors += "Insert your name \n";
        }

        if(name.length() > 18) {
            register_trainee_full_name.setError("Your name is too long (max 18 letters)");
            errors += "Your name is too long (max 18 letters) \n";
        }

        if(TextUtils.isEmpty(address)) {
            register_trainee_address.setError("Please insert your address.");
            errors += "Insert your address \n";
        }

        if(address.length() > 25) {
            register_trainee_address.setError("Your address is too long (max 25 letters)");
            errors += "Your address is too long (max 25 letters) \n";
        }

        if(TextUtils.isEmpty(age)) {
            register_trainee_age.setError("Please insert your age.");
            errors += "Insert your age \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) > 120) {
            register_trainee_age.setError("Age over 120 isn't allowed.");
            errors += "Age over 120 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(age) && Integer.parseInt(age) < 16) {
            register_trainee_age.setError("Age under 16 isn't allowed.");
            errors += "Age under 16 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(height)) {
            register_trainee_height.setError("Please insert your height.");
            errors += "Insert your height \n";
        }

        if(!TextUtils.isEmpty(height) && Integer.parseInt(height) > 250) {
            register_trainee_height.setError("Height over 250 isn't allowed.");
            errors += "Height over 250 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(height) && Integer.parseInt(height) < 50) {
            register_trainee_height.setError("Height under 50 isn't allowed.");
            errors += "Height under 50 isn't allowed. \n";
        }

        if(TextUtils.isEmpty(weight)) {
            register_trainee_weight.setError("Please insert your weight.");
            errors += "Insert your weight \n";
        }

        if(!TextUtils.isEmpty(weight) && Integer.parseInt(weight) > 250) {
            register_trainee_weight.setError("Weight over 250 isn't allowed.");
            errors += "Weight over 250 isn't allowed. \n";
        }

        if(!TextUtils.isEmpty(weight) && Integer.parseInt(weight) < 30) {
            register_trainee_weight.setError("Weight under 30 isn't allowed.");
            errors += "Weight under 30 isn't allowed. \n";
        }

        if(!register_trainee_female_radio.isChecked() && !register_trainee_male_radio.isChecked()) {
            errors += "Insert your gender \n";
        }

        return errors;
    }
}