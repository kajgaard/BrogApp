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

public class EnterBloomWaterActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int bloomWaterInteger;
    int minimumBloomWater = 20; // Define minimum possible value for user
    int maximumBloomWater = 80; // Define max possible value for user
    TextView bloomWaterValueTextView;

    public void bloomWaterDownButtonPush(View view) { // User pushes down
        Log.i("BloomWater", "Up button pushed");
        if (bloomWaterInteger > minimumBloomWater) {
            bloomWaterInteger = bloomWaterInteger - 1;
            bloomWaterValueTextView.setText(bloomWaterInteger + " ml");
        }
    }

    public void bloomWaterUpButtonPush(View view) { // User pushes up
        Log.i("BloomWater", "Up button pushed");
        if (bloomWaterInteger < maximumBloomWater) {
            bloomWaterInteger = bloomWaterInteger + 1;
            bloomWaterValueTextView.setText(bloomWaterInteger + " ml");
        }
    }

    public void bloomWaterNextButtonPushed(View view) { // User navigates to next screen
        Log.i("Temp", "Next button pushed");
        brewValues.set(4, Integer.toString(bloomWaterInteger)); // Update brewValues with users selected value
        Intent intent = new Intent(EnterBloomWaterActivity.this, EnterBloomTimeActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer brewValues to next activity
        startActivity(intent);
        finish();
    }

    public void bloomWaterPreviousButtonPushed(View view) { // User navigates to previous screen
        Log.i("Temp", "Previous button pushed");
        brewValues.set(4, Integer.toString(bloomWaterInteger)); // Update brewValues with users selected value
        Intent intent = new Intent(EnterBloomWaterActivity.this, EnterTempActivity.class);
        intent.putExtra("brewValues", brewValues); // Transfer brewValues to previous activity
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bloom_water);

        seekBar = findViewById(R.id.bloomWaterSeekBar);
        seekBar.setEnabled(false); // User cannot manipulate seekbar
        seekBar.setProgress(4);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        bloomWaterValueTextView = findViewById(R.id.bloomWaterValueTextView);
        bloomWaterValueTextView.setText(brewValues.get(4)+" ml");

        bloomWaterInteger = Integer.parseInt(brewValues.get(4));


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
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_scan:
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_brew:
                        startActivity(new Intent(getApplicationContext(), BrewMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_wash:
                        startActivity(new Intent(getApplicationContext(), CleanActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}