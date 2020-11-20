package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.brogapp.CreateNewBrew.EnterGramsActivity;
import com.example.brogapp.Favorites.BrewFaveAdapter;
import com.example.brogapp.Favorites.FavoritesAdapter;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class BrewMainActivity extends AppCompatActivity implements FavoritesAdapter.OnListItemClick {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    BrewFaveAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    RecyclerView faveRecyclerView;
    RecyclerView.Adapter faveAdapter;
    RecyclerView.LayoutManager faveLayoutManager;

    RecyclerView flereForslagRecyclerView;
    RecyclerView.Adapter flereForslagAdapter;
    RecyclerView.LayoutManager flereForslagLayoutManager;

    // When pushing the "nyt bryg" button
    public void newBrewButtonPushed(View view){
        ArrayList<String> brewValues = new ArrayList<>();
        brewValues.add("20");       // Grams of coffee
        brewValues.add("60");       // grams of coffee per liter of water
        brewValues.add("Medium");   // Coffee ground coarseness
        brewValues.add("92");       // Water temperature
        brewValues.add("40");       // Bloom water
        brewValues.add("30");       // Bloom Time
        brewValues.add("180");      // Brew time
        Toast.makeText(this, brewValues.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Button","New brew button pushed");

        Intent intent = new Intent(BrewMainActivity.this, EnterGramsActivity.class);
        intent.putExtra("brewValues",brewValues);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_main);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        //Query for database
        Query query = fStore.collection("users").document(userID).collection("favorites");

        //Paging so in case we have a lot of data in database, it loads in pages
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(15)
                .setPageSize(5)
                .build();

        //Recycler options (github dependency) //se youtube.com/watch?v=LatlcDZhpd4
        FirestorePagingOptions<BrewItem> options = new FirestorePagingOptions.Builder<BrewItem>()
                .setLifecycleOwner(this) //No longer need onStart() and onStop()
                .setQuery(query, config, new SnapshotParser<BrewItem>() {
                    @NonNull
                    @Override
                    public BrewItem parseSnapshot(@NonNull DocumentSnapshot snapshot) { //so we can get ID for all documents in collection
                        BrewItem brewItem = snapshot.toObject(BrewItem.class);
                        String brewId = snapshot.getId();
                        brewItem.setbrewID();
                        return brewItem;
                    }
                })
                .build();

        mAdapter = new BrewFaveAdapter(options, this);
        mRecyclerView = findViewById(R.id.favesRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        ArrayList<BrewItem> listOfFlereForslag = new ArrayList<>();
        listOfFlereForslag.add(new BrewItem(0,"Manhatten", "None","4.6","none"));
        listOfFlereForslag.add(new BrewItem(R.drawable.coffeetwo_pic,"yo yo yo", "None","4.6","none"));
        listOfFlereForslag.add(new BrewItem(R.drawable.coffeetwo_pic,"Torronto", "None","4.6","none"));
        listOfFlereForslag.add(new BrewItem(R.drawable.coffeetwo_pic,"Skagen", "None","4.6","none"));
        listOfFlereForslag.add(new BrewItem(R.drawable.coffeetwo_pic,"San Francisco", "None","4.6","none"));
        listOfFlereForslag.add(new BrewItem(R.drawable.coffeetwo_pic,"Malmø", "None","4.6","none"));

        flereForslagRecyclerView = findViewById(R.id.flereForslagRV);
        flereForslagRecyclerView.setHasFixedSize(true);

        flereForslagLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        flereForslagAdapter = new BrewForslagAdapter(listOfFlereForslag);

        flereForslagRecyclerView.setLayoutManager(flereForslagLayoutManager);
        flereForslagRecyclerView.setAdapter(flereForslagAdapter);

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_brew);

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

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("CLICK","item was clicked at pos. " + position + "\nID is " + snapshot.getId());
    }
}