package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = "FavoritesActivity";
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

    //Fill out recyclerView
        ArrayList<BrewItem> listOfFaves = new ArrayList<>();
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Manhatten", "Super lækkert bryg som jeg vil drikke hver dag","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"New York", "Smager også ok, men den er bedst om mandagen","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Torronto", "Den er skidego' til hygge med veninderne","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Skagen", "Når man bare skal have kaffe, og det skal gå stærkt","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"San Francisco", "Minder om den vi fik på caféen i USA","4.6"));
        listOfFaves.add(new BrewItem(R.drawable.coffee_pic,"Malmø", "Shit den her er fandme også lækker","4.6"));

        mRecyclerView = findViewById(R.id.favesHolderRV);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BrewListAdapter(listOfFaves);

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

}