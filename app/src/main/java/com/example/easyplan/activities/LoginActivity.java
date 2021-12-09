package com.example.easyplan.activities;

import static com.example.easyplan.R.id.login_to_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

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

        String check = checkInputs();
        if(check.isEmpty()) {
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
                                    System.out.println(username_type);
                                    startActivity(new Intent(LoginActivity.this, TraineeHomepageActivity.class));
                                }
                                else startActivity(new Intent(LoginActivity.this,TrainerHomepageActivity.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.dialog_error);

                        TextView errors = dialog.findViewById(R.id.dialog_error_text);
                        Button ok_btn = dialog.findViewById(R.id.dialog_ok);

                        errors.setText("Failed to login!");
                        ok_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }
            });
        }
        else {
            final Dialog dialog = new Dialog(LoginActivity.this);
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
        String email_input = login_username.getText().toString().trim();
        String password_input = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email_input)) {
            login_username.setError("Email is required!");
            errors += "Email is required! \n";
        }

        if(!TextUtils.isEmpty(email_input) && !Patterns.EMAIL_ADDRESS.matcher(email_input).matches()) {
            login_username.setError("Valid email is required!");
            errors += "Valid email is required! \n";
        }

        if(TextUtils.isEmpty(password_input)) {
            login_password.setError("Password is required!");
            errors += "Password is required! \n";
        }
        return errors;
    }

    public void forgot_password(View v) {
        startActivity(new Intent(LoginActivity.this , ForgotPasswordActivity.class));
    }
    public void register(View v) {
        startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
    }


}