package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.easyplan.R;

import java.util.ArrayList;
import java.util.Vector;

public class RegisterByTargetActivity extends AppCompatActivity {

    private Button register_by_target_next;
    private CardView register_by_target_fitness, register_by_target_cardio, register_by_target_muscles, register_by_target_menu_nutrition;
    private LinearLayout register_by_target_fitness_linear, register_by_target_cardio_linear, register_by_target_muscles_linear, register_by_target_menu_nutrition_linear;
    int fitness_press = 0, cardio_press = 0, muscles_press = 0, nutrition_press =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_target);

        register_by_target_next = (Button) findViewById(R.id.register_by_target_next);

        register_by_target_fitness = (CardView) findViewById(R.id.register_by_target_fitness);
        register_by_target_cardio = (CardView) findViewById(R.id.register_by_target_cardio);
        register_by_target_muscles = (CardView) findViewById(R.id.register_by_target_muscles);
        register_by_target_menu_nutrition = (CardView) findViewById(R.id.register_by_target_menu_nutrition);

        register_by_target_fitness_linear = (LinearLayout) findViewById(R.id.register_by_target_fitness_linear);
        register_by_target_cardio_linear = (LinearLayout) findViewById(R.id.register_by_target_cardio_linear);
        register_by_target_muscles_linear = (LinearLayout) findViewById(R.id.register_by_target_muscles_linear);
        register_by_target_menu_nutrition_linear = (LinearLayout) findViewById(R.id.register_by_target_menu_nutrition_linear);
        ArrayList <String> targets = new ArrayList<>();

        register_by_target_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(targets.isEmpty()) {
                    Toast.makeText(RegisterByTargetActivity.this,"Please choose at least one Target!",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent move = new Intent(RegisterByTargetActivity.this, TrainerListActivity.class);
                    move.putStringArrayListExtra("query types", targets);
                    startActivity(move);
                }



            }
        });

        register_by_target_fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitness_press++;

                if(fitness_press % 2 == 0) {
                    register_by_target_fitness_linear.setBackground(getDrawable(R.drawable.edit_text_background));
                    if (targets.contains("fitness")) targets.remove("fitness");


                }
                else {
                    register_by_target_fitness_linear.setBackground(getDrawable(R.drawable.frame_background));
                    targets.add("fitness");
                }

            }
        });

        register_by_target_cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardio_press++;

                if(cardio_press % 2 == 0) {
                    register_by_target_cardio_linear.setBackground(getDrawable(R.drawable.edit_text_background));
                    if (targets.contains("cardio")) targets.remove("cardio");

                }
                else {
                    register_by_target_cardio_linear.setBackground(getDrawable(R.drawable.frame_background));
                    targets.add("cardio");
                }
            }
        });

        register_by_target_muscles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muscles_press++;

                if(muscles_press % 2 == 0) {
                    register_by_target_muscles_linear.setBackground(getDrawable(R.drawable.edit_text_background));
                    if (targets.contains("muscles")) targets.remove("muscles");

                }
                else {
                    register_by_target_muscles_linear.setBackground(getDrawable(R.drawable.frame_background));
                    targets.add("muscles");
                }
            }
        });

        register_by_target_menu_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nutrition_press++;

                if(nutrition_press % 2 == 0) {
                    register_by_target_menu_nutrition_linear.setBackground(getDrawable(R.drawable.edit_text_background));
                    if (targets.contains("nutrition")) targets.remove("nutrition");
                }
                else {
                    register_by_target_menu_nutrition_linear.setBackground(getDrawable(R.drawable.frame_background));
                    targets.add("nutrition");
                }
            }
        });
    }
}