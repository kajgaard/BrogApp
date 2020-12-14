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

public class EnterGramsActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;
    int minimumGrams = 10;
    int maximumGrams = 50;

    TextView gramsTextView;

    public void nextButtonPushed(View view) { // When user pushes next
        brewValues.set(0,Integer.toString(gramsInteger));
        Log.i("Button","Next button pushed");

        Intent intent = new Intent(EnterGramsActivity.this,EnterWaterPerGramActivity.class);
        intent.putExtra("brewValues",brewValues); // Arraylist with brewvalues sent next activity
        startActivity(intent);
        finish();
    }

    public void upButtonpush(View view){ // User pushes up
        if(gramsInteger<maximumGrams) {
            gramsInteger++;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    public void downButtonpush(View view){ // User pushes down
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
        seekBar.setEnabled(false); // User unable to manipulate seekbar

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        gramsTextView = findViewById(R.id.gramsTextView);
        gramsInteger=Integer.parseInt(brewValues.get(0));
        gramsTextView.setText(gramsInteger+" g");

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