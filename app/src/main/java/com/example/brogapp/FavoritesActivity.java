package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = "FavoritesActivity";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirestoreRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        /*
    //Fill out recyclerView for debugging
        ArrayList<BrewItem> listOfFaves = new ArrayList<>();
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Manhatten", "Super lækkert bryg som jeg vil drikke hver dag","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"New York", "Smager også ok, men den er bedst om mandagen","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Torronto", "Den er skidego' til hygge med veninderne","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Skagen", "Når man bare skal have kaffe, og det skal gå stærkt","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"San Francisco", "Minder om den vi fik på caféen i USA","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Malmø", "Shit den her er fandme også lækker","4.6"));

         */


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        //Query for database
        Query query = fStore.collection("users").document(userID).collection("favorites");

        //Recycler options (github dependency)
        FirestoreRecyclerOptions<BrewItem> options = new FirestoreRecyclerOptions.Builder<BrewItem>()
                .setQuery(query,BrewItem.class).build();

        //Adapter
        mAdapter = new FirestoreRecyclerAdapter<BrewItem,BrewViewHolderFirestore>(options) {
            @NonNull
            @Override
            public BrewViewHolderFirestore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faves_adapter, parent, false);

                return new BrewViewHolderFirestore(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull BrewViewHolderFirestore holder, int position, @NonNull BrewItem model) {
                holder.mImage.setImageResource(model.getImageResource());
                holder.brewName.setText(model.getBrewName());
                holder.brewDescription.setText(model.getBrewDescription());
                holder.brewScore.setText(model.getBrewScore());
            }
        };

        mRecyclerView = findViewById(R.id.favesHolderRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        //mAdapter = new BrewListAdapter(listOfFaves);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        //Set up listener, for determine if other icon is pressed
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(),ScanActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_brew:
                        startActivity(new Intent(getApplicationContext(),BrewMainActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_wash:
                        startActivity(new Intent(getApplicationContext(),CleanActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfilePage.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;
                }

                return false;
            }
        });


    }

    private class BrewViewHolderFirestore extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView brewName, brewDescription, brewScore;
        public BrewViewHolderFirestore(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.itemPicIV);
            brewName = itemView.findViewById(R.id.brewNameTV);
            brewDescription = itemView.findViewById(R.id.brewDescriptionTV);
            brewScore = itemView.findViewById(R.id.scoreTV);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
}