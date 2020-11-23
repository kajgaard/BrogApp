package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    BrewFaveAdapter mFaveAdapter;
    RecyclerView mFaveRecyclerView;
    RecyclerView.LayoutManager mFaveLayoutManager;

    BrewForslagAdapter mForslagAdapter;
    RecyclerView mForslagRecyclerView;
    RecyclerView.LayoutManager mForslagLayoutManager;

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
        Query queryFavorites = fStore.collection("users").document(userID).collection("favorites");

        //Paging so in case we have a lot of data in database, it loads in pages
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(15)
                .setPageSize(5)
                .build();

        //Recycler options (github dependency) //se youtube.com/watch?v=LatlcDZhpd4
        FirestorePagingOptions<BrewItem> options = new FirestorePagingOptions.Builder<BrewItem>()
                .setLifecycleOwner(this) //No longer need onStart() and onStop()
                .setQuery(queryFavorites, config, new SnapshotParser<BrewItem>() {
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

        mFaveAdapter = new BrewFaveAdapter(options, this);
        mFaveRecyclerView = findViewById(R.id.favesRV);
        mFaveRecyclerView.setHasFixedSize(true);
        mFaveLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mFaveRecyclerView.setLayoutManager(mFaveLayoutManager);
        mFaveRecyclerView.setAdapter(mFaveAdapter);



        Query queryForslag = fStore.collection("brews");

        //Paging so in case we have a lot of data in database, it loads in pages
        PagedList.Config config2 = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(15)
                .setPageSize(5)
                .build();

        //Recycler options (github dependency) //se youtube.com/watch?v=LatlcDZhpd4
        FirestorePagingOptions<BrewItem> options2 = new FirestorePagingOptions.Builder<BrewItem>()
                .setLifecycleOwner(this) //No longer need onStart() and onStop()
                .setQuery(queryForslag, config2, new SnapshotParser<BrewItem>() {
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

        mForslagAdapter = new BrewForslagAdapter(options2, this);
        mForslagRecyclerView = findViewById(R.id.flereForslagRV);
        mForslagRecyclerView.setHasFixedSize(true);
        mForslagLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mForslagRecyclerView.setLayoutManager(mForslagLayoutManager);
        mForslagRecyclerView.setAdapter(mForslagAdapter);


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