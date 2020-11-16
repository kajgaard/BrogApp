package com.example.brogapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.brogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryAddToF extends AppCompatActivity {

    private ImageView iconPic;
    private EditText brewName;
    private EditText description;
    private RatingBar stars;
    private int selectedIcon = 0;
    private String IdOfSelectedHistory;
    public Map<String,Object> HistoryDocumentFromFirebase = new HashMap<>();

    ArrayList<String> brewValues;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public void saveToFave(View view) {
        Map<String, Object> newbrew = new HashMap<>();

        newbrew.put("brewName", brewName.getText().toString());
        newbrew.put("brewDescription", description.getText().toString());
        newbrew.put("brewScore", Float.toString(stars.getRating()));
        newbrew.put("imageRessource", Integer.toString(selectedIcon));
        newbrew.put("coffeeAmount", (String) HistoryDocumentFromFirebase.get("coffeeAmount"));
        newbrew.put("grindSize", (String) HistoryDocumentFromFirebase.get("grindSize"));
        newbrew.put("waterRatio", (String) HistoryDocumentFromFirebase.get("waterRatio"));
        newbrew.put("brewTemp", (String) HistoryDocumentFromFirebase.get("brewTemp"));
        newbrew.put("bloomWater",(String) HistoryDocumentFromFirebase.get("bloomWater"));
        newbrew.put("bloomTime", (String) HistoryDocumentFromFirebase.get("bloomTime"));
        newbrew.put("brewTime", (String) HistoryDocumentFromFirebase.get("brewTime"));

        fStore.collection("users").document(userID).collection("favorites").document().set(newbrew);

        fStore.collection("users")
                .document(userID)
                .collection("history")
                .document(IdOfSelectedHistory)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });



    }

    public void pickIconButtonPushed(View view) {
        HistorySelectIconFragment hsif = new HistorySelectIconFragment();
        hsif.show(getSupportFragmentManager(), "test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_add_to_favorites);

        IdOfSelectedHistory = (String) getIntent().getSerializableExtra("IdOfSelectedHistory");
        Toast.makeText(this, IdOfSelectedHistory, Toast.LENGTH_SHORT).show();

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int) (width*.8),(int)(height*.6));

        iconPic = findViewById(R.id.pickIconIV);
        brewName = findViewById(R.id.addToFaveNameOfBrewET);
        description = findViewById(R.id.addToFaveDiscripOfBrewET);
        stars = findViewById(R.id.ratingBar);

        setIconImage(0);
        getHistoryDataFromFirebase();
    }

    public void getHistoryDataFromFirebase(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        // From googles guide: https://firebase.google.com/docs/firestore/query-data/get-data#java_2
        DocumentReference dr = fStore
                .collection("users")
                .document(userID)
                .collection("history")
                .document(IdOfSelectedHistory);

        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "Document data: " + document.getData());
                        HistoryDocumentFromFirebase = document.getData();
                        Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("TAG", "No doc found");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    public void setIconImage(int selectedIcon) {

        this.selectedIcon = selectedIcon;

        switch (selectedIcon) {
            case 0:
                iconPic.setImageResource(R.drawable.coffee_pic);
                break;
            case 1:
                iconPic.setImageResource(R.drawable.coffeetwo_pic);
                break;
            case 2:
                iconPic.setImageResource(R.drawable.coffeethree_pic);
                break;
            case 3:
                iconPic.setImageResource(R.drawable.coffeefour_pic);
                break;
            case 4:
                iconPic.setImageResource(R.drawable.coffeefive_pic);
                break;
            case 5:
                iconPic.setImageResource(R.drawable.coffeesix_pic);
                break;
            default:
                iconPic.setImageResource(R.drawable.coffee_pic);
        }

    }

    private void saveBrewToFavorites(ArrayList<String> brewInfoPackage) {


//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//        userID = fAuth.getCurrentUser().getUid();


    }
}