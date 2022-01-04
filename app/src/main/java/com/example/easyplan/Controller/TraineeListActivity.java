package com.example.easyplan.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.easyplan.Model.FirebaseData;
import com.example.easyplan.Model.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.Controller.adapters.TraineeListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//////**********************************************////////////
////// This activity manages the trainees list.
////// It loads all the trainees uses RecyclerView to present the data.
//////**********************************************////////////
public class TraineeListActivity extends AppCompatActivity {
    private FirebaseData model;
    private TraineeListAdapter adapter;
    private String trainerId;
    private ArrayList<Trainee> trainees_adapter;
    private ArrayList<String> trainees_id_adapter;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_list);

        model = new FirebaseData();
        trainerId = model.getID();

        trainees_adapter = new ArrayList<>();
        trainees_id_adapter = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.trainee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TraineeListAdapter(trainees_adapter, trainees_id_adapter, trainerId);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        ArrayList<String> trainees_uid = getAllTraineesUID();
        showAllTrainees(trainees_uid);
    }









//////**********************************************////////////
////// Returns all trainees ID signed to the specific trainer in ArrayList
//////**********************************************////////////
    private ArrayList<String> getAllTraineesUID() {
        ArrayList<String> trainees_temp_id = new ArrayList<>();

        model.getUserReference(trainerId+"/my_trainees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot runner : snapshot.getChildren()) {
                    trainees_temp_id.add(runner.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return trainees_temp_id;
    }









//////**********************************************////////////
////// Gets all trainees id and fill the Trainees Objects with its ID for the adapter.
//////**********************************************////////////
    private void showAllTrainees(ArrayList<String> trainees_id) {
        model.getUserReference("").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot runner : snapshot.getChildren()) {
                    String id = runner.getKey();
                    if(trainees_id.contains(id)) {
                        Trainee trainee = runner.getValue(Trainee.class);
                        trainees_adapter.add(trainee);
                        trainees_id_adapter.add(id);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}