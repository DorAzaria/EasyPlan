package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        /// CONNECT FRONT TO BACK
        register_trainee_btn = (Button) findViewById(R.id.register_trainee_btn);

        register_trainee_image = (ImageView) findViewById(R.id.register_trainee_image);
        register_trainee_upload = (ImageView) findViewById(R.id.register_trainee_upload);

        register_trainee_full_name = (EditText) findViewById(R.id.register_trainee_full_name);
        register_trainee_address = (EditText) findViewById(R.id.register_trainee_address);
        register_trainee_age = (EditText) findViewById(R.id.register_trainee_age);
        register_trainee_height = (EditText) findViewById(R.id.register_trainee_height);
        register_trainee_weight = (EditText) findViewById(R.id.register_trainee_weight);

        register_trainee_male_radio = (RadioButton) findViewById(R.id.register_trainee_male_radio);
        register_trainee_female_radio = (RadioButton) findViewById(R.id.register_trainee_female_radio);
        ///////////////////////////////////////////////////

        register_trainee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trainee_to_homepage = new Intent(RegisterTraineeActivity.this, TraineeHomepageActivity.class);
                trainee_to_homepage.putExtra("from_register","true");
                startActivity(trainee_to_homepage);
            }
        });

    }


    public void register_trainee(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterTraineeActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}