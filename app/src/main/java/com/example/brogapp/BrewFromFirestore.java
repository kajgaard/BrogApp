package com.example.brogapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class BrewFromFirestore {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String brewId, userID;
    ArrayList<String> values = new ArrayList<>();
    Boolean isFavorite;
    String userCollection;



    public ArrayList<String> extractUserValues(String brewID, Boolean isFavorite){
        ArrayList<String> mList = this.values;
        if(isFavorite){
            userCollection = "favorites";
        }else{
            userCollection = "history";
        }

        DocumentReference ref = fStore.collection("users").document(userID).collection(userCollection).document(brewID);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("snapshot", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("snapshot", "No such document");
                    }
                } else {
                    Log.d("snapshot", "get failed with ", task.getException());
                }
            }
        });




        return mList;
    }

    public void connectTo(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
    }

    //Query for database
    Query query = fStore.collection("users").document(userID).collection("favorites");


}
