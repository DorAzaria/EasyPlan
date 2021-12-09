package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        List<Trainer> trainers = new ArrayList<>();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.hasChild("Users/" + mAuth.getUid())) {

                }
                else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setAdapter(new TrainerListAdapter(generateData()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
    }

    private List<String> generateData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("Trainer " + String.valueOf(i));
        }
        return data;
    }
}