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

public class EnterGrindActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    TextView grindValueTextView;

    public void grindNextButtonPushed(View view) {
        Log.i("Grind", "Next button pushed");
        brewValues.set(2,grindValueTextView.getText().toString());
        Intent intent = new Intent(EnterGrindActivity.this, EnterTempActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void GrindPreviousButtonPushed(View view) {
        Log.i("Grind", "Previous button pushed");
        brewValues.set(2, grindValueTextView.getText().toString());
        Intent intent = new Intent(EnterGrindActivity.this, EnterWaterPerGramActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }


    public void grindUpButtonPush(View view) {
        Log.i("Grind", "Up button pushed");
        if (!grindValueTextView.getText().equals("Grov")) {
            if (grindValueTextView.getText().equals("Fin")) {
                grindValueTextView.setText("Medium");
            } else {
                grindValueTextView.setText("Grov");
            }
        }
    }

    public void grindDownButtonPush(View view) {
        Log.i("Grind", "Down button pushed");
        if (!grindValueTextView.getText().equals("Fin")) {
            if (grindValueTextView.getText().equals("Grov")) {
                grindValueTextView.setText("Medium");
            } else {
                grindValueTextView.setText("Fin");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_grind);

        seekBar = findViewById(R.id.grindSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(2);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        grindValueTextView = findViewById(R.id.grindValueTextView);
        grindValueTextView.setText(brewValues.get(2));


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