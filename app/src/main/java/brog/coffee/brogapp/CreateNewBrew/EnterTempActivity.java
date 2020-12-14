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

public class EnterTempActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int tempInteger;
    int minimumTemp = 80; // Min value for the user to input
    int maximumTemp = 100; // Max value for the user to input
    TextView tempValueTextView;

    public void tempPreviousButtonPushed(View view) { // User pushing previous
        Log.i("Temp", "Previous button pushed");
        brewValues.set(3, Integer.toString(tempInteger));
        Intent intent = new Intent(EnterTempActivity.this, EnterGrindActivity.class);
        intent.putExtra("brewValues", brewValues); // Values sent to previous activity
        startActivity(intent);
        finish();
    }

    public void tempNextButtonPushed(View view) { // User pushing next
        Log.i("Temp", "Next button pushed");
        brewValues.set(3,Integer.toString(tempInteger));
        Intent intent = new Intent(EnterTempActivity.this, EnterBloomWaterActivity.class);
        intent.putExtra("brewValues", brewValues); // Values sent to next activity
        startActivity(intent);
        finish();
    }

    public void tempUpButtonPush(View view) { // User pushing up
        Log.i("Temp", "Up button pushed");
        if (tempInteger < maximumTemp) {
            tempInteger = tempInteger + 1;
            tempValueTextView.setText(tempInteger + " \u00B0C"); // UTF code for degree symbol
        }
    }

    public void tempDownButtonPush(View view) { // User pushing down
        Log.i("Temp", "Down button pushed");
        if (tempInteger > minimumTemp) {
            tempInteger = tempInteger - 1;
            tempValueTextView.setText(tempInteger + " \u00B0C"); // UTF code for degree symbol
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