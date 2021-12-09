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

public class RegisterActivity extends AppCompatActivity {

    private Button register_btn;
    private EditText register_password, register_re_password, register_email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        register_btn = (Button) findViewById(R.id.register_btn);
        register_password = (EditText) findViewById(R.id.register_password);
        register_re_password = (EditText) findViewById(R.id.register_re_password);
        register_email = (EditText) findViewById(R.id.register_email);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        updateUI(currentUser);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }




    public void register(View view) {
        register_btn = (Button) view;
        String check = checkInputs();
        if(check.isEmpty()) {


                if (register_password.getText().toString().equals(register_re_password.getText().toString())) {

                    mAuth.fetchSignInMethodsForEmail(register_email.getText().toString())
                            .addOnCompleteListener (new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                                    if (isNewUser) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Intent move = new Intent(RegisterActivity.this, RegisterByTypeActivity.class);
                                        move.putExtra("email",register_email.getText().toString());
                                        move.putExtra("password",register_password.getText().toString());
                                        startActivity(move);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords doesn't matched", Toast.LENGTH_SHORT).show();
                }
        }
        else {
            final Dialog dialog = new Dialog(RegisterActivity.this);
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
}