package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyplan.R;

public class RegisterActivity extends AppCompatActivity {

    private Button register_btn;
    private EditText register_username, register_password, register_re_password, register_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = (Button) findViewById(R.id.register_btn);
        register_username = (EditText) findViewById(R.id.register_username);
        register_password = (EditText) findViewById(R.id.register_password);
        register_re_password = (EditText) findViewById(R.id.register_re_password);
        register_email = (EditText) findViewById(R.id.register_email);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, RegisterByTypeActivity.class));
            }
        });
    }

    private void checkEmpty() {

    }

    private void checkInputs() {

    }
}