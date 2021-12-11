package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyplan.R;

import java.util.ArrayList;

//////**********************************************////////////
////// This activity manages the target selection for trainees service.
//////**********************************************////////////
public class SelectTargetActivity extends AppCompatActivity {
    private LinearLayout register_by_target_fitness_linear, register_by_target_cardio_linear, register_by_target_muscles_linear, register_by_target_menu_nutrition_linear;
    int fitness_press = 0, cardio_press = 0, muscles_press = 0, nutrition_press = 0;
    private ArrayList <String> targets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_target);
        register_by_target_fitness_linear = (LinearLayout) findViewById(R.id.register_by_target_fitness_linear);
        register_by_target_cardio_linear = (LinearLayout) findViewById(R.id.register_by_target_cardio_linear);
        register_by_target_muscles_linear = (LinearLayout) findViewById(R.id.register_by_target_muscles_linear);
        register_by_target_menu_nutrition_linear = (LinearLayout) findViewById(R.id.register_by_target_menu_nutrition_linear);
        targets = new ArrayList<>();
    }

//////**********************************************////////////
    public void goToTrainersList(View view) {

        if(targets.isEmpty()) {
            errorDialog("Please choose at least one Target!");
        }
        else {
            Intent move = new Intent(SelectTargetActivity.this, TrainerListActivity.class);
            move.putStringArrayListExtra("query types", targets);
            startActivity(move);
        }

    }


//////**********************************************////////////
    public void pressFitness(View view) {
        fitness_press++;

        if(fitness_press % 2 == 0) {
            register_by_target_fitness_linear.setBackground(getDrawable(R.drawable.edit_text_background));
            if (targets.contains("Fitness")) targets.remove("Fitness");


        }
        else {
            register_by_target_fitness_linear.setBackground(getDrawable(R.drawable.frame_background));
            targets.add("Fitness");
        }
    }


//////**********************************************////////////
    public void pressMuscle(View view) {
        muscles_press++;

        if(muscles_press % 2 == 0) {
            register_by_target_muscles_linear.setBackground(getDrawable(R.drawable.edit_text_background));
            if (targets.contains("Muscle")) targets.remove("Muscle");

        }
        else {
            register_by_target_muscles_linear.setBackground(getDrawable(R.drawable.frame_background));
            targets.add("Muscle");
        }
    }


//////**********************************************////////////
    public void pressMenuNutrition(View view) {
        nutrition_press++;

        if(nutrition_press % 2 == 0) {
            register_by_target_menu_nutrition_linear.setBackground(getDrawable(R.drawable.edit_text_background));
            if (targets.contains("Menu Nutrition")) targets.remove("Menu Nutrition");
        }
        else {
            register_by_target_menu_nutrition_linear.setBackground(getDrawable(R.drawable.frame_background));
            targets.add("Menu Nutrition");
        }
    }


//////**********************************************////////////
    public void pressCardio(View view) {
        cardio_press++;

        if(cardio_press % 2 == 0) {
            register_by_target_cardio_linear.setBackground(getDrawable(R.drawable.edit_text_background));
            if (targets.contains("Cardio")) targets.remove("Cardio");

        }
        else {
            register_by_target_cardio_linear.setBackground(getDrawable(R.drawable.frame_background));
            targets.add("Cardio");
        }
    }

//////**********************************************////////////
////// Dialog pattern, gets a string and prints it. usually to print errors.
//////**********************************************////////////
    private void errorDialog(String error) {
        final Dialog dialog = new Dialog(SelectTargetActivity.this);
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