package com.example.brogapp.CreateNewBrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brogapp.BrewMainActivity;
import com.example.brogapp.CleanActivity;
import com.example.brogapp.HomePage;
import com.example.brogapp.ProfilePage;
import com.example.brogapp.R;
import com.example.brogapp.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BrewStartedActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Date date;
    String dateName;
    String text;
    TextView message;

    public void okPushed(View view){
        Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveBrew(){

        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm",Locale.GERMAN);
        dateName = formatter.format(date);
        Log.i("date",formatter.format(date));
        Toast.makeText(this,formatter.format(date),Toast.LENGTH_SHORT).show();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Map<String, Object> newbrew = new HashMap<>();
        newbrew.put("brewName", dateName);
        newbrew.put("brewDescription", "Nyt bryg. Der er ikke tilføjet beskrivelse.");
        newbrew.put("brewScore", "0.0");
        newbrew.put("imageRessource", 0);
        newbrew.put("coffeeAmount", brewValues.get(0));
        newbrew.put("grindSize", brewValues.get(2));
        newbrew.put("waterRatio", brewValues.get(1));
        newbrew.put("brewTemp", brewValues.get(3));
        newbrew.put("bloomWater", brewValues.get(4));
        newbrew.put("bloomTime", brewValues.get(5));
        newbrew.put("brewTime", brewValues.get(6));
        newbrew.put("timeStamp",System.currentTimeMillis());

        fStore.collection("users").document(userID).collection("history").document().set(newbrew);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_started);

        message = findViewById(R.id.finalIntroTextView3);


        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");
        text = (String) getIntent().getSerializableExtra("text");
        message.setText(text);

        Toast.makeText(this,brewValues.toString(),Toast.LENGTH_SHORT).show();

        saveBrew();

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_wash);

        //Set up listener, for determine if other icon is pressed
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_brew:
                        startActivity(new Intent(getApplicationContext(), BrewMainActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_wash:
                        startActivity(new Intent(getApplicationContext(), CleanActivity.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                        overridePendingTransition(0,0); //Dont know what this does
                        return true;
                }
                return false;
            }
        });
    }
}