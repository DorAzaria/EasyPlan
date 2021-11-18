package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {
    private TextView login_to_register, login_to_forgot;
    private EditText login_username, login_password;
    private Button login_btn;
    private SignInButton sign_in_button_google;
    private LoginButton sign_in_button_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /// CONNECT FRONT TO BACK
        login_to_register = (TextView) findViewById(R.id.login_to_register);
        login_to_forgot = (TextView) findViewById(R.id.login_to_forgot);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_btn = (Button) findViewById(R.id.login_btn);
        sign_in_button_google = (SignInButton) findViewById(R.id.sign_in_button_google);
        sign_in_button_facebook = (LoginButton) findViewById(R.id.sign_in_button_facebook);
        ///

        login_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login_to_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }
}