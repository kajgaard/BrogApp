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

public class EnterBloomTimeActivity extends AppCompatActivity {

    private ArrayList<String> brewValues;
    private SeekBar seekBar;
    private int bloomTimeInteger;
    private int minimumBloomTime = 20;          // Set minimum for legal user selection
    private int maximumBloomTime = 120;         // Set maximum for legal user selection
    private TextView bloomTimeValueTextView;


    public void bloomTimeUpButtonPush(View view) { // When user pushes up button
        Log.i("BloomTime", "Up button pushed");
        if (bloomTimeInteger < maximumBloomTime) {
            bloomTimeInteger = bloomTimeInteger + 1;
            bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));
        }
    }

    public void bloomTimeDownButtonPush(View view) { // When user pushes down button
        Log.i("BloomTime", "Up button pushed");
        if (bloomTimeInteger > minimumBloomTime) {
            bloomTimeInteger = bloomTimeInteger - 1;
            bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));
        }
    }

    public void bloomTimeNextButtonPushed(View view) { // Pushing next
        Log.i("Temp", "Next button pushed");
        brewValues.set(5, Integer.toString(bloomTimeInteger)); // Set value in arraylist
        Intent intent = new Intent(EnterBloomTimeActivity.this, EnterBrewTimeActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer arraylist to next activity
        startActivity(intent);
        finish();
    }

    public void bloomTimePreviousButtonPushed(View view) { // Pushing previous
        Log.i("Temp", "Previous button pushed");
        brewValues.set(5, Integer.toString(bloomTimeInteger)); // Set value in arraylist
        Intent intent = new Intent(EnterBloomTimeActivity.this, EnterBloomWaterActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer arraylist to next activity
        startActivity(intent);
        finish();
    }

    public String convertTime(int totalTime) { // Function to convert seconds to readable text
        // E.g. 130 -> 2 minutes 10 seconds
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
        seekBar.setEnabled(false); // Seekbar cannot be manipulated by user
        seekBar.setProgress(5);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        bloomTimeInteger = Integer.parseInt(brewValues.get(5)); // Get string of seconds and convert to int
        bloomTimeValueTextView = findViewById(R.id.bloomTimeValueTextView);
        bloomTimeValueTextView.setText(convertTime(bloomTimeInteger));


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