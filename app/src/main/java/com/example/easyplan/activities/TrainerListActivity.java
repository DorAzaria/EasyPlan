package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.easyplan.Data.Trainer;
import com.example.easyplan.R;
import com.example.easyplan.adapters.TrainerListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrainerListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_list);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = findViewById(R.id.trainer_list_recyclerview);
        Intent move = getIntent();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        List<Trainer> trainers = new ArrayList<>();
        List<String> trainers_ids = new ArrayList<>();

        TrainerListAdapter tla = new TrainerListAdapter(trainers , trainers_ids);
        recyclerView.setAdapter(tla);

        ArrayList<String> type_request = move.getStringArrayListExtra("query types");
        reference = database.getReference("Users/");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot runner : snapshot.getChildren()) {
                    String type = runner.child("type").getValue(String.class);
                    if (type.equals("Trainer")) {
                        Trainer trainer = runner.getValue(Trainer.class);
                        if (trainer.getTargets().containsAll(type_request)) {
                                trainers.add(trainer);
                                trainers_ids.add(runner.getKey());
                        }
                    }
                }
                tla.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}