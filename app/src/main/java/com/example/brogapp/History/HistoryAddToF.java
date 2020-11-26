package com.example.brogapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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
    public Map<String, Object> HistoryDocumentFromFirebase = new HashMap<>();

    ArrayList<String> brewValues;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public void saveToFave(View view) {// When user presses "GEM" in order to save History to Favorites


        final String name = brewName.getText().toString();
        final String mDescription = description.getText().toString();

        if(TextUtils.isEmpty(name)){
            brewName.setError("Feltet skal udfyldes");
        }else{

            Map<String, Object> newbrew = new HashMap<>();      // HashMap to store all brew details

                // Populate the HashMap with values entered by the user + brew data from the brew
                newbrew.put("brewName", brewName.getText().toString());
                newbrew.put("brewDescription", description.getText().toString());
                newbrew.put("brewScore", Float.toString(stars.getRating()));
                newbrew.put("imageRessource", selectedIcon);
                newbrew.put("coffeeAmount", (String) HistoryDocumentFromFirebase.get("coffeeAmount"));
                newbrew.put("grindSize", (String) HistoryDocumentFromFirebase.get("grindSize"));
                newbrew.put("waterRatio", (String) HistoryDocumentFromFirebase.get("waterRatio"));
                newbrew.put("brewTemp", (String) HistoryDocumentFromFirebase.get("brewTemp"));
                newbrew.put("bloomWater", (String) HistoryDocumentFromFirebase.get("bloomWater"));
                newbrew.put("bloomTime", (String) HistoryDocumentFromFirebase.get("bloomTime"));
                newbrew.put("brewTime", (String) HistoryDocumentFromFirebase.get("brewTime"));

            if(TextUtils.isEmpty(mDescription)) {
                newbrew.put("brewName", brewName.getText().toString());
                newbrew.put("brewDescription", "Du har ikke skrevet en beskrivelse...");
                newbrew.put("brewScore", Float.toString(stars.getRating()));
                newbrew.put("imageRessource", selectedIcon);
                newbrew.put("coffeeAmount", (String) HistoryDocumentFromFirebase.get("coffeeAmount"));
                newbrew.put("grindSize", (String) HistoryDocumentFromFirebase.get("grindSize"));
                newbrew.put("waterRatio", (String) HistoryDocumentFromFirebase.get("waterRatio"));
                newbrew.put("brewTemp", (String) HistoryDocumentFromFirebase.get("brewTemp"));
                newbrew.put("bloomWater", (String) HistoryDocumentFromFirebase.get("bloomWater"));
                newbrew.put("bloomTime", (String) HistoryDocumentFromFirebase.get("bloomTime"));
                newbrew.put("brewTime", (String) HistoryDocumentFromFirebase.get("brewTime"));
            }

            // Store the populated brew in FireStore Favorites
            fStore.collection("users").document(userID).collection("favorites").document().set(newbrew);

            // Delete the brew from History, as it is now available in favorites
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

            Toast.makeText(this,"Dit bryg er gemt i favoritter!",Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    // Display fragment when user wants to pick an Icon
    public void pickIconButtonPushed(View view) {
        HistorySelectIconFragment hsif = new HistorySelectIconFragment();
        hsif.show(getSupportFragmentManager(), "test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_add_to_favorites);

        // Receive ID of the History Brew, that the user selected
        IdOfSelectedHistory = (String) getIntent().getSerializableExtra("IdOfSelectedHistory");
        Toast.makeText(this, IdOfSelectedHistory, Toast.LENGTH_SHORT).show();

        // Early attempt to make the activity "pop out". Looked bad.
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        getWindow().setLayout((int) (width*.8),(int)(height*.6));

        iconPic = findViewById(R.id.pickIconIV);
        brewName = findViewById(R.id.addToFaveNameOfBrewET);
        description = findViewById(R.id.addToFaveDiscripOfBrewET);
        stars = findViewById(R.id.ratingBar);

        setIconImage(0);        // Pick basic icon when the brew is displayed to the user for the first time.
        getHistoryDataFromFirebase();
    }

    // Method to establish contact with Firestore and retreive all brew data from the selected brew
    public void getHistoryDataFromFirebase() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        // From googles guide: https://firebase.google.com/docs/firestore/query-data/get-data#java_2
        // Get a handle on the specific brew we want to move to Favorites
        DocumentReference dr = fStore
                .collection("users")
                .document(userID)
                .collection("history")
                .document(IdOfSelectedHistory);

        // Get the data. This is an asynchronous task. Listener added to check when done.
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "Document data: " + document.getData());
                        HistoryDocumentFromFirebase = document.getData(); // Copy data so we can access it outside this class
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("TAG", "No doc found");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    // This is called from the select icon fragment, so the selected icon is displayed in the activity
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
}