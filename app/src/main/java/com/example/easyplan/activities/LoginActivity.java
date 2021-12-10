package com.example.easyplan.activities;

import static com.example.easyplan.R.id.login_to_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity  {

    private TextView login_to_register, login_to_forgot;
    private EditText login_username, login_password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        login_username = findViewById(R.id.login_email);
        login_password =  findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);

        login_to_register = findViewById(R.id.login_to_register);
        login_to_forgot =  findViewById(R.id.login_to_forgot);

    }

    public void login (View v) {
        String email = login_username.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if (checkValidation(login_username , login_password)) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Users/" + mAuth.getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String username_type = snapshot.child("type").getValue(String.class);
                                if(username_type.equals("Trainee")) {
                                    Intent i = new Intent(LoginActivity.this, TraineeHomepageActivity.class);
                                    i.putExtra("myId", mAuth.getUid());
                                    startActivity(i);
                                }
                                else startActivity(new Intent(LoginActivity.this,TrainerHomepageActivity.class));



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Failed to login!",Toast.LENGTH_LONG).show();;
                    }
                }
            });
        }

    }

    private boolean checkValidation (EditText email , EditText password) {
            String email_input = email.getText().toString();
            String password_input = password.getText().toString();

            if(password_input.isEmpty()) {
                login_password.setError("Password is required!");
                login_password.requestFocus();
                return false;
            }
            if (email_input.isEmpty()) {
                login_username.setError("Email is required!");
                login_username.requestFocus();
                return false;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email_input).matches()) {
                email.setError("Please enter a valid email!");
                email.requestFocus();
                return false;
            }
            return true;
    }

    public void forgot_password(View v) {
        startActivity(new Intent(LoginActivity.this , ForgotPasswordActivity.class));
    }
    public void register(View v) {
        startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
    }


}