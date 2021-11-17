package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndPlanActivity extends AppCompatActivity {

    private Button end_plan_toolbar_rate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_plan);

        end_plan_toolbar_rate_btn = (Button) findViewById(R.id.end_plan_toolbar_rate_btn);

        end_plan_toolbar_rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EndPlanActivity.this, TrainerListActivity.class));
            }
        });
    }
}