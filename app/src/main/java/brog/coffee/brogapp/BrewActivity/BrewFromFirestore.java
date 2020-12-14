package brog.coffee.brogapp.BrewActivity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;



public class BrewFromFirestore { //Vi nåede desværre aldrig at få implementeret denne klasse.
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String brewId, userID, bloomtime, bloomwater, brewdescript, brewname,
            brewscore, brewtemp,brewtime,coffeeamount, grindsize,
            imageressource, waterratio;
    ArrayList<String> values = new ArrayList<>();
    Boolean isFavorite;
    String userCollection;

    public BrewFromFirestore(){
        connectTo();
    }

    public ArrayList<String> brewThis(DocumentSnapshot value){

        bloomtime = value.getString("bloomTime");
        bloomwater = value.getString("bloomWater");
        brewdescript = value.getString("brewDescription");
        brewname = value.getString("brewName");
        brewscore = value.getString("brewScore");
        brewtemp = value.getString("brewTemp");
        brewtime = value.getString("brewTime");
        coffeeamount = value.getString("coffeeAmount");
        grindsize = value.getString("grindSize");
        imageressource = value.getString("imageRessource");
        waterratio = value.getString("waterRatio");

        values.set(0, coffeeamount);
        values.set(1, waterratio);
        values.set(2, grindsize);
        values.set(3, brewtemp);
        values.set(4, bloomwater);
        values.set(5, bloomtime);
        values.set(6, brewtime);

        Log.d("HALLO", "brewThis: " + values);
        return values;
    }


    public ArrayList<String> extractUserValues(String brewID, Boolean isFavorite){
        ArrayList<String> mList = this.values;
        if(isFavorite){
            userCollection = "favorites";
        }else{
            userCollection = "history";
        }

        DocumentReference ref = fStore.collection("users").document(userID).collection(userCollection).document(brewID);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                bloomtime = value.getString("bloomTime");
                bloomwater = value.getString("bloomWater");
                brewdescript = value.getString("brewDescription");
                brewname = value.getString("brewName");
                brewscore = value.getString("brewScore");
                brewtemp = value.getString("brewTemp");
                brewtime = value.getString("brewTime");
                coffeeamount = value.getString("coffeeAmount");
                grindsize = value.getString("grindSize");
                imageressource = value.getString("imageRessource");
                waterratio = value.getString("waterRatio");


            }
        });

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
