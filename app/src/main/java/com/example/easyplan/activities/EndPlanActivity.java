package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.easyplan.R;

public class EndPlanActivity extends AppCompatActivity {

    private Button end_plan_btn;
    private RatingBar end_plan_toolbar_ratebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_plan);

        end_plan_btn = (Button) findViewById(R.id.end_plan_btn);
        end_plan_toolbar_ratebar = (RatingBar) findViewById(R.id.end_plan_toolbar_ratebar);

        end_plan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EndPlanActivity.this, TrainerListActivity.class));
            }
        });
    }
}