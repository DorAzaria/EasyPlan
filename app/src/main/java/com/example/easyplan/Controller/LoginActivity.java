package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easyplan.Model.FirebaseData;
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


//////**********************************************////////////
////// This activity manages the login system.
////// It checks if the user exists and prints errors using dialog class.
////// It also offers "Forgot Password" and "Register" pages.
//////**********************************************////////////
public class LoginActivity extends AppCompatActivity  {

    private EditText login_email, login_password;
    private FirebaseData model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        model = new FirebaseData();
        login_email = findViewById(R.id.login_email);
        login_password =  findViewById(R.id.login_password);
    }


//////**********************************************////////////
////// This activity manages the login system.
////// It checks if the user exists and prints errors using dialog class.
//////**********************************************////////////
    public void login (View v) {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();
        String errors_details = checkInputs();

        if(errors_details.isEmpty()) {
            signIn(email, password);
        }
        else {
            errorDialog(errors_details);
        }
    }


//////**********************************************////////////
////// checkInputs collects all errors as String and returns the errors.
////// Checks empty cases.
//////**********************************************////////////
    public String checkInputs() {
        String errors = "";
        String email_input = login_email.getText().toString().trim();
        String password_input = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email_input)) {
            login_email.setError("Email is required!");
            errors += "Email is required! \n";
        }

        if(!TextUtils.isEmpty(email_input) && !Patterns.EMAIL_ADDRESS.matcher(email_input).matches()) {
            login_email.setError("Valid email is required!");
            errors += "Valid email is required! \n";
        }

        if(TextUtils.isEmpty(password_input)) {
            login_password.setError("Password is required!");
            errors += "Password is required! \n";
        }
        return errors;
    }


//////**********************************************////////////
////// signIn method checks if the user exists or not and pass to the system
//////**********************************************////////////
    public void signIn(String email, String password) {
        model.getmAuth().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    moveToHomepage();
                }
                else {
                    errorDialog("Failed to login!"); // e.c. wrong password or email
                }
            }
        });
    }


//////**********************************************////////////
////// moveToHomepage method takes the user to its homepage - depends by its type (Trainer or Trainee).
//////**********************************************////////////
    private void moveToHomepage() {

        model.getUserReference(model.getID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username_type = snapshot.child("type").getValue(String.class);
                if(username_type.equals("Trainee")) {
                    startActivity(new Intent(LoginActivity.this, TraineeHomepageActivity.class));
                }
                else {
                    startActivity(new Intent(LoginActivity.this, TrainerHomepageActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorDialog("Ops! something went wrong.");
            }
        });
    }


//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(LoginActivity.this);
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


//////**********************************************////////////
    public void forgot_password(View v) {
        startActivity(new Intent(LoginActivity.this , ForgotPasswordActivity.class));
    }


//////**********************************************////////////
    public void register(View v) {
        startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
    }

}