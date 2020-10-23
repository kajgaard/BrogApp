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

public class EnterTempActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int tempInteger;
    int minimumTemp = 80;
    int maximumTemp = 100;
    TextView tempValueTextView;

    public void tempPreviousButtonPushed(View view) {
        Log.i("Temp", "Previous button pushed");
        brewValues.set(3, Integer.toString(tempInteger));
        Intent intent = new Intent(EnterTempActivity.this, EnterGrindActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void tempNextButtonPushed(View view) {
        Log.i("Temp", "Next button pushed");
        brewValues.set(3,Integer.toString(tempInteger));
        Intent intent = new Intent(EnterTempActivity.this, EnterBloomWaterActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void tempUpButtonPush(View view) {
        Log.i("Temp", "Up button pushed");
        if (tempInteger < maximumTemp) {
            tempInteger = tempInteger + 1;
            tempValueTextView.setText(tempInteger + " \u00B0C");
        }
    }

    public void tempDownButtonPush(View view) {
        Log.i("Temp", "Down button pushed");
        if (tempInteger > minimumTemp) {
            tempInteger = tempInteger - 1;
            tempValueTextView.setText(tempInteger + " \u00B0C");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_temp);

        seekBar = findViewById(R.id.tempSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(3);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        tempValueTextView = findViewById(R.id.tempValueTextView);
        tempValueTextView.setText(brewValues.get(3)+" \u00B0C");

        tempInteger = Integer.parseInt(brewValues.get(3));

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