package com.example.brogapp.CreateNewBrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import java.util.ArrayList;

public class EnterGramsActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;
    int minimumGrams = 10;
    int maximumGrams = 50;

    TextView gramsTextView;

    public void nextButtonPushed(View view) {
        brewValues.set(0,Integer.toString(gramsInteger));
        Log.i("Button","Next button pushed");

        Intent intent = new Intent(EnterGramsActivity.this,EnterWaterPerGramActivity.class);
        intent.putExtra("brewValues",brewValues);
        startActivity(intent);
finish();
    }

    public void upButtonpush(View view){
        if(gramsInteger<maximumGrams) {
            gramsInteger++;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    public void downButtonpush(View view){
        if(gramsInteger>minimumGrams) {
            gramsInteger--;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_grams);

        seekBar = findViewById(R.id.gramsSeekBar);
        seekBar.setEnabled(false);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        gramsTextView = findViewById(R.id.gramsTextView);
        gramsInteger=Integer.parseInt(brewValues.get(0));
        gramsTextView.setText(gramsInteger+" g");

        Toast.makeText(this,brewValues.toString(),Toast.LENGTH_SHORT).show();



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