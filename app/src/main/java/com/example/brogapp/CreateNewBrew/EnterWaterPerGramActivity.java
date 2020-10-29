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

public class EnterWaterPerGramActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;
    int minimumGram = 20;
    int maximumGram = 100;
    int totalBrew;
    int testTotalBrew;
    TextView waterValueTextView;
    TextView waterTotalTextView;


    public void waterNextButtonPushed(View view) {
        Log.i("Water per gram", "Next button pushed");
        brewValues.set(1,Integer.toString(gramsInteger));
        Intent intent = new Intent(EnterWaterPerGramActivity.this, EnterGrindActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void waterUpButtonPush(View view) {
        Log.i("Water per gram", "Up button pushed");
        testTotalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / (gramsInteger + 1));

        if (gramsInteger < maximumGram && testTotalBrew >= 100) {
            gramsInteger = gramsInteger + 1;
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
            waterValueTextView.setText(gramsInteger + " g");
            waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);
        }
    }

    public void waterDownButtonPush(View view) {
        Log.i("Water per gram", "Down button pushed");
        testTotalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / (gramsInteger - 1));

        if (gramsInteger > minimumGram && testTotalBrew <= 600) {
            gramsInteger = gramsInteger - 1;
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
            waterValueTextView.setText(gramsInteger + " g");
            waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);
        }
    }

    public void waterPreviousButtonPushed(View view) {
        Log.i("Water per gram", "Previous button pushed");
        brewValues.set(1, Integer.toString(gramsInteger));
        Intent intent = new Intent(EnterWaterPerGramActivity.this, EnterGramsActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_water_per_gram);

        seekBar = findViewById(R.id.waterSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(1);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        waterValueTextView = findViewById(R.id.waterValueTextView);
        waterTotalTextView = findViewById(R.id.waterTotalTextView);
        gramsInteger = Integer.parseInt(brewValues.get(1));
        waterValueTextView.setText(gramsInteger + " g");

        totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
        if (totalBrew > 600) {
            gramsInteger = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / 600) + 1;
            waterValueTextView.setText(gramsInteger + " g");
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
        }
        waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);

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