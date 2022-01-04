package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.easyplan.Model.Trainer;
import com.example.easyplan.R;
import com.example.easyplan.Controller.adapters.TrainerListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;









//////**********************************************////////////
////// This activity manages the trainers list.
////// It loads all the trainers with the specific targets and uses RecyclerView to present the data.
//////**********************************************////////////
public class TrainerListActivity extends AppCompatActivity {

    private TrainerListAdapter trainerListAdapter;
    private List<Trainer> trainers;
    private List<String> trainers_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_list);

        Intent move = getIntent();
        ArrayList<String> trainee_targets = move.getStringArrayListExtra("query types");

        trainers = new ArrayList<>();
        trainers_ids = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.trainer_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainerListAdapter = new TrainerListAdapter(trainers, trainers_ids);
        recyclerView.setAdapter(trainerListAdapter);

        getTrainers(trainee_targets);
    }










//////**********************************************////////////
    private void getTrainers(ArrayList<String> trainee_targets) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot runner : snapshot.getChildren()) {
                    String type = runner.child("type").getValue(String.class);
                    if (type.equals("Trainer")) {
                        Trainer trainer = runner.getValue(Trainer.class);
                        if (trainer.getTargets().containsAll(trainee_targets)) {
                            trainers.add(trainer);
                            trainers_ids.add(runner.getKey());
                        }
                    }
                }
                trainerListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}