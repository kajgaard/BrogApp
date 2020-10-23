package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brogapp.Favorites.FavoritesActivity;
import com.example.brogapp.History.HistoryActivity;
import com.example.brogapp.LogOnActivities.LogInActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {

    TextView favorites, logout, header, history;
    ImageView favoritesIV, historyIV, favoritesArrow, historyArrow;
    String name, userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public ProfilePage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        logout = findViewById(R.id.logOutTV);
        logout.setOnClickListener(this);

        favorites = findViewById(R.id.favesTV);
        favorites.setOnClickListener(this);
        favoritesIV = findViewById(R.id.heartIconIV);
        favoritesIV.setOnClickListener(this);
        favoritesArrow = findViewById(R.id.arrow1IV);
        favoritesArrow.setOnClickListener(this);

        history = findViewById(R.id.historyTV);
        historyIV = findViewById(R.id.historyIV);
        historyArrow = findViewById(R.id.arrow2IV);
        history.setOnClickListener(this);
        historyIV.setOnClickListener(this);
        historyArrow.setOnClickListener(this);


        header = findViewById(R.id.welcomeTV);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null) {

                    name = value.getString("name");
                    //header.setText("Velkommen " + name + "!");
                    header.setText("Velkommen " + name.substring(0, name.indexOf(" ")).trim() + "!");
                    if (error != null) {
                        Log.d("STRING", "fejl " + error.getMessage());
                    }
                }
            }
        });


        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        //Set up listener, for determine if other icon is pressed
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0); //Dont know what this does
                        return true;

                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        overridePendingTransition(0, 0); //Dont know what this does
                        return true;

                    case R.id.nav_brew:
                        startActivity(new Intent(getApplicationContext(), BrewMainActivity.class));
                        overridePendingTransition(0, 0); //Dont know what this does
                        return true;

                    case R.id.nav_wash:
                        startActivity(new Intent(getApplicationContext(), CleanActivity.class));
                        overridePendingTransition(0, 0); //Dont know what this does
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                        overridePendingTransition(0, 0); //Dont know what this does
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == favorites || v == favoritesIV || v == favoritesArrow) {
            startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
        } else if (v == history || v == historyIV || v == historyArrow) {
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        } else if (v == logout) {

            FirebaseAuth.getInstance().signOut(); //logs user out
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();

        }
    }
}