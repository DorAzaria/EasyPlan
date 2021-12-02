package com.example.easyplan.Data;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.easyplan.activities.RegisterTraineeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Vector;

public class FirebaseData {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private Trainer trainer;
    private Trainee trainee;

    public FirebaseData() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public String getCurrentID(){
        return mAuth.getCurrentUser().getUid();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setUsers(DatabaseReference referenceUsers) {
        this.databaseReference = referenceUsers;
    }


}
