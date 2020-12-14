package brog.coffee.brogapp.CreateNewBrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.StartActivity.HomePage;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import brog.coffee.brogapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EnterBrewTimeActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int brewTimeInteger;
    int minimumBrewTime = 120; // Set minimum allowable user input
    int maximumBrewTime = 240; // Set maximum allowable user input
    TextView brewTimeValueTextView;

    public void brewTimeUpButtonPush(View view) { // User pushes up
        Log.i("BrewTime", "Up button pushed");
        if (brewTimeInteger < maximumBrewTime) {
            brewTimeInteger = brewTimeInteger + 1;
            brewTimeValueTextView.setText(convertTime(brewTimeInteger)); // The secs are converted to min/sec
        }
    }

    public void brewTimeDownButtonPush(View view) { // User pushes down
        Log.i("BrewTime", "Up button pushed");
        if (brewTimeInteger > minimumBrewTime) {
            brewTimeInteger = brewTimeInteger - 1;
            brewTimeValueTextView.setText(convertTime(brewTimeInteger));
        }
    }

    public void brewTimeNextButtonPushed(View view) { // User pushes next
        Log.i("Temp", "Next button pushed");
        brewValues.set(6, Integer.toString(brewTimeInteger)); // Set the arraylist with user's selected value
        Intent intent = new Intent(EnterBrewTimeActivity.this, EnterFinalPageActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer arraylist to next activity
        startActivity(intent);
        finish();
    }

    public void brewTimePreviousButtonPushed(View view) { // User pushes previous
        Log.i("Temp", "Previous button pushed");
        brewValues.set(6, Integer.toString(brewTimeInteger)); // Set the arraylist with user's selected value
        Intent intent = new Intent(EnterBrewTimeActivity.this, EnterBloomTimeActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer arraylist to next activity
        startActivity(intent);
        finish();
    }

    public String convertTime(int totalTime) { // Method that converts seconds to min+sec
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
        setContentView(R.layout.activity_enter_brew_time);

        seekBar = findViewById(R.id.brewTimeSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(6);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        brewTimeInteger = Integer.parseInt(brewValues.get(6));
        brewTimeValueTextView = findViewById(R.id.brewTimeValueTextView);
        brewTimeValueTextView.setText(convertTime(brewTimeInteger));

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
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_brew:
                        startActivity(new Intent(getApplicationContext(), BrewMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_wash:
                        startActivity(new Intent(getApplicationContext(), CleanActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
    }

    }