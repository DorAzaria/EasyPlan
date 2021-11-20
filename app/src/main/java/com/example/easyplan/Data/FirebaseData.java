package com.example.easyplan.Data;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FirebaseData {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public FirebaseData() {

    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public String getCurrentID(){
        return firebaseAuth.getCurrentUser().getUid();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setUsers(DatabaseReference referenceUsers) {
        this.databaseReference = referenceUsers;
    }


}
