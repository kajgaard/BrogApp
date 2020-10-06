package com.example.brogapp;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EnterBloomTimeActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int bloomTimeInteger;
    int minimumBloomTime = 20;
    int maximumBloomTime = 120;
    TextView bloomTimeValueTextView;
    String convertedTime;


    public void bloomTimeUpButtonPush(View view) {
        Log.i("BloomTime", "Up button pushed");
        if (bloomTimeInteger < maximumBloomTime) {
            bloomTimeInteger = bloomTimeInteger + 1;
            bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));
        }
    }

    public void bloomTimeDownButtonPush(View view) {
        Log.i("BloomTime", "Up button pushed");
        if (bloomTimeInteger > minimumBloomTime) {
            bloomTimeInteger = bloomTimeInteger - 1;
            bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));
        }
    }

    public void bloomTimeNextButtonPushed(View view) {
        Log.i("Temp", "Next button pushed");
        brewValues.set(5, Integer.toString(bloomTimeInteger));
        // Intent intent = new Intent(EnterBloomTimeActivity.this, EnterBrewTimeActivity.class);
        // intent.putExtra("brewValues", brewValues);
        // startActivity(intent);
    }

    public void bloomTimePreviousButtonPushed(View view) {
        Log.i("Temp", "Previous button pushed");
        brewValues.set(5, Integer.toString(bloomTimeInteger));
        Intent intent = new Intent(EnterBloomTimeActivity.this, EnterBloomWaterActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
    }

    public String convertTime(int totalTime) {
        if (totalTime == 60) {
            return "60 sek";
        }
        int seconds = totalTime % 60;
        int minutes = totalTime / 60;
        if (minutes > 0) {
            return "" + minutes + " min\n" + seconds + " sek";
        } else {
            return "" + seconds + " sek";
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bloom_time);

        seekBar = findViewById(R.id.bloomTimeSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(5);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        bloomTimeInteger = Integer.parseInt(brewValues.get(5));
        bloomTimeValueTextView = findViewById(R.id.bloomTimeValueTextView);
        bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));

        Toast.makeText(this, brewValues.toString(), Toast.LENGTH_SHORT).show();

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_brew);

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
}