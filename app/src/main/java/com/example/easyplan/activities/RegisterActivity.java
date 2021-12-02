package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyplan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {

    private Button register_btn;
    private EditText register_username, register_password, register_re_password, register_email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        register_btn = (Button) findViewById(R.id.register_btn);
        register_username = (EditText) findViewById(R.id.register_username);
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


    private boolean checkEmpty() {
        boolean flag = true;
        String error = "Errors at: ";
        if(register_email == null || register_email.getText().toString().equals("")) {
            flag &= false;
            error += " email, ";
        }

        if(register_password == null || register_password.getText().toString().equals("")) {
            flag &= false;
            error += " password, ";
        }

        if(register_re_password == null || register_re_password.getText().toString().equals("")) {
            flag &= false;
            error += " re-password .";
        }

        if(!flag) {
            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
        }

        return flag;
    }

    private void checkInputs() {
//        Patterns.EMAIL_ADDRESS.matcher(emailToText).matches(
    }

    public void register(View view) {
        register_btn = (Button) view;

        if(checkEmpty()) {


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

    }
}