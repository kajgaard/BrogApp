package com.example.brogapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.brogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HistoryAddToF extends AppCompatActivity {

    private ImageView iconPic;
    private EditText brewName;
    private EditText description;
    private RatingBar stars;
    private int selectedIcon = 0;
    private String IdOfSelectedHistory;


    ArrayList<String> brewValues;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public void saveToFave(View view){
        ArrayList<String> brewInfoPackage = new ArrayList<>();
        brewInfoPackage.add(brewName.getText().toString());
        brewInfoPackage.add(description.getText().toString());
        brewInfoPackage.add(Float.toString(stars.getRating()));
        brewInfoPackage.add(Integer.toString(selectedIcon));

        Toast.makeText(this,brewInfoPackage.toString(),Toast.LENGTH_SHORT).show();

        // From googles guide: https://firebase.google.com/docs/firestore/query-data/get-data#java_2
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference dr = fStore
                .collection("users")
                .document(userID)
                .collection("history")
                .document(IdOfSelectedHistory);

        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("TAG","Document data: "+document.getData());
                    } else {
                        Log.d("TAG","No doc found");
                    }
                } else {
                    Log.d("TAG","get failed with ",task.getException());
                }
            }
        });
//      End of google snippet

        //saveBrewToFavorites(brewInfoPackage);
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
        Toast.makeText(this,IdOfSelectedHistory,Toast.LENGTH_SHORT).show();

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

    private void saveBrewToFavorites(ArrayList<String> brewInfoPackage){


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Map<String, Object> newbrew = new HashMap<>();
        newbrew.put("brewName", brewInfoPackage.get(0));
        newbrew.put("brewDescription", brewInfoPackage.get(1));
        newbrew.put("brewScore", brewInfoPackage.get(2));
        newbrew.put("imageRessource", brewInfoPackage.get(3));
//        newbrew.put("coffeeAmount", brewValues.get(0));
//        newbrew.put("grindSize", brewValues.get(2));
//        newbrew.put("waterRatio", brewValues.get(1));
//        newbrew.put("brewTemp", brewValues.get(3));
//        newbrew.put("bloomWater", brewValues.get(4));
//        newbrew.put("bloomTime", brewValues.get(5));
//        newbrew.put("brewTime", brewValues.get(6));
//        newbrew.put("timeStamp",System.currentTimeMillis());

        fStore.collection("users").document(userID).collection("favorites").document().set(newbrew);
    }
}