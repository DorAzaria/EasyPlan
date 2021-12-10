package com.example.easyplan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.easyplan.Data.FirebaseData;
import com.example.easyplan.Data.Trainee;
import com.example.easyplan.R;
import com.example.easyplan.adapters.TraineeListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class TraineeListActivity extends AppCompatActivity {
    private String trainerId;
    private ArrayList<String> traineesId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trainerId = extras.getString("myId");
        }
        traineesId = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.trainee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Trainee> trainees = new ArrayList<>();
        TraineeListAdapter adapter = new TraineeListAdapter(trainees);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        FirebaseDatabase dataBase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = dataBase.getReference("Users/"+trainerId+"/my_trainees");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Boolean> Id = (HashMap<String, Boolean>) snapshot.getValue();
                if(Id != null){
                    for(String runner : Id.keySet() ){
                        Log.d("Id", runner);
                        traineesId.add(runner);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




            myRef = dataBase.getReference("Users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    trainees.clear();

                    for(int i=0; i<traineesId.size();i++){
                        String name = snapshot.child(traineesId.get(i)).child("name").getValue(String.class);
                        String address = snapshot.child(traineesId.get(i)).child("address").getValue(String.class);
                        String height = snapshot.child(traineesId.get(i)).child("height").getValue(String.class);
                        String weight = snapshot.child(traineesId.get(i)).child("weight").getValue(String.class);
                        String gender = snapshot.child(traineesId.get(i)).child("gender").getValue(String.class);
                        String notifications = snapshot.child(traineesId.get(i)).child("notifications").getValue(String.class);
                        String plan_status = snapshot.child(traineesId.get(i)).child("plan_status").getValue(String.class);
                        int age = snapshot.child(traineesId.get(i)).child("age").getValue(Integer.class);
                        Trainee trainee = new Trainee(name,address,height,weight,gender,age,notifications,plan_status);
                        trainees.add(trainee);
                    }


                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






    }

}