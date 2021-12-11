package com.example.easyplan.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgot_password_email;
    private Button forgot_password_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_password_btn = (Button) findViewById(R.id.forgot_password_btn);
        forgot_password_email = (EditText) findViewById(R.id.forgot_password_email);

        forgot_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = checkInputs();

                if(check.isEmpty()) {

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = forgot_password_email.getText().toString();
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        errorDialog("Email Sent.");
                                    }
                                    else {
                                        errorDialog("Ops, something went wrong! \n   This email isn't exists in our system :(");
                                    }
                                }
                            });
                }
                else {
                    errorDialog(check);
                }
            }
        });
    }


    private String checkInputs() {
        String errors = "";
        String email = forgot_password_email.getText().toString();

        if(TextUtils.isEmpty(email)) {
            forgot_password_email.setError("Please insert your email.");
            errors += "Insert your email \n";
        }


        if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgot_password_email.setError("Please enter valid Email address!");
            errors += "Please enter valid Email address! \n";
        }

        return errors;
    }

//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(ForgotPasswordActivity.this);
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
}