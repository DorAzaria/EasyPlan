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


    private void checkEmpty() {

    }

    private void checkInputs() {

    }

    public void register(View view) {
        register_btn = (Button) view;


        if(register_email != null || register_password != null || register_re_password != null) {

            if(register_password.getText().toString().equals(register_re_password.getText().toString())) {

                    mAuth.createUserWithEmailAndPassword(register_email.getText().toString(), register_password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        startActivity(new Intent(RegisterActivity.this, RegisterByTypeActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }
            else {
                Toast.makeText(RegisterActivity.this, "Passwords doesn't matched", Toast.LENGTH_SHORT).show();
            }
        }

    }
}