package com.example.easyplan.activities;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Pattern;

//////**********************************************////////////
////// This activity manages the Authentication register system.
////// 1. Inputs email, password and re-password
////// 2. Checks if the user already exists in the system - prints dialog if there is.
////// 3. Checks if the passwords match - prints dialog if it's not matched.
////// 4. Checks for input errors and prints dialog if there's any.
//////**********************************************////////////

public class RegisterActivity extends AppCompatActivity {

    private EditText register_password, register_re_password, register_email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        register_password = (EditText) findViewById(R.id.register_password);
        register_re_password = (EditText) findViewById(R.id.register_re_password);
        register_email = (EditText) findViewById(R.id.register_email);
    }


//////**********************************************////////////
////// register method: manages everything (same details as the class details).
//////**********************************************////////////
    public void register(View view) {
        String errors_details = checkInputs();

        // if it's empty then there's no errors, else, use dialog to show the errors.
        if(errors_details.isEmpty()) {

            mAuth.fetchSignInMethodsForEmail(register_email.getText().toString())
                    .addOnCompleteListener (new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if(isNewUser){
                                // Sign in success, update UI with the signed-in user's information
                                Intent move = new Intent(RegisterActivity.this, RegisterByTypeActivity.class);
                                move.putExtra("email",register_email.getText().toString().trim());
                                move.putExtra("password",register_password.getText().toString().trim());
                                startActivity(move);
                            }
                            else {
                                errorDialog("This user is already in our system.");
                            }
                        }
                    });

        }
        else {
            errorDialog(errors_details);
        }

    }


//////**********************************************////////////
////// checkInputs collects all errors as String and returns the errors.
////// Checks empty cases, password matching, valid email and password.
//////**********************************************////////////
    public String checkInputs() {
        String errors = "";
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        String re_password = register_re_password.getText().toString();

        if(TextUtils.isEmpty(email)) {
            register_email.setError("Please insert your email.");
            errors += "Insert your email \n";
        }

        if(TextUtils.isEmpty(password)) {
            register_password.setError("Please insert your password.");
            errors += "Insert your password \n";
        }

        if(TextUtils.isEmpty(re_password)) {
            register_re_password.setError("Please insert your re-password.");
            errors += "Insert your re-password \n";
        }

        if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(re_password) && !password.equals(re_password)) {
            register_password.setError("Your passwords doesn't match!");
            register_re_password.setError("Your passwords doesn't match!");
            errors += "Your passwords doesn't match! \n";
        }

        if(!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            register_email.setError("Please enter valid Email address! ");
            errors += "Enter valid Email address! \n";
        }

        Pattern PASSWORD_PATTERN = Pattern.compile("[a-z0-9]{8,24}");
        if(!TextUtils.isEmpty(password) && !PASSWORD_PATTERN.matcher(password).matches()) {
            register_password.setError("Strong password needed!");
            errors += "Strong password needed: at least 8 letters a-z, 0-9 \n";
        }

        return errors;
    }


//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(RegisterActivity.this);
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