package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterByTargetActivity extends AppCompatActivity {

    private Button register_by_target_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_target);

        register_by_target_next = (Button) findViewById(R.id.register_by_target_next);

        register_by_target_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterByTargetActivity.this, TrainerListActivity.class ));
            }
        });
    }
}