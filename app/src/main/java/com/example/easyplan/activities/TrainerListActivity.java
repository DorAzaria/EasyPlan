package com.example.easyplan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.easyplan.R;
import com.example.easyplan.adapters.TrainerListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrainerListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_list);

        RecyclerView recyclerView = findViewById(R.id.trainer_list_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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