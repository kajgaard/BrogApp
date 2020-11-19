package com.example.brogapp.CreateNewBrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class EnterFinalPageActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    Button redHeartButton;
    Button greyHeartButton;
    boolean isFavorited = false;
    Toast toastAddToFavorites;
    Toast toastRemoveFromFavorites;
    TextView finalValuesTextView;
    String finalValues;

    public void finalStartButtonPushed(View view) {
        Toast.makeText(this, brewValues.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Final Page", "Bryg button pushed");
        Intent intent = new Intent(EnterFinalPageActivity.this, BrewStartedActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void finalPreviousButtonPushed(View view) {
        Log.i("Temp", "Previous button pushed");
        Intent intent = new Intent(EnterFinalPageActivity.this, EnterBrewTimeActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);
        finish();
    }

    public void pushGreyHeart(View view) {
        isFavorited = true;
        greyHeartButton.setAlpha(0);
        redHeartButton.setAlpha(1);
        greyHeartButton.setEnabled(false);
        redHeartButton.setEnabled(true);

        if (toastRemoveFromFavorites.getView().isShown()) {
            toastRemoveFromFavorites.cancel();
        }
        toastAddToFavorites = Toast.makeText(this, "Tilføjet til favoritter!", Toast.LENGTH_SHORT);
        toastAddToFavorites.show();
    }

    public void pushRedHeart(View view) {
        isFavorited = false;
        greyHeartButton.setAlpha(1);
        redHeartButton.setAlpha(0);
        greyHeartButton.setEnabled(true);
        redHeartButton.setEnabled(false);
        if (toastAddToFavorites.getView().isShown()) {
            toastAddToFavorites.cancel();
        }
        toastRemoveFromFavorites = Toast.makeText(this, "Fjernet fra favoritter", Toast.LENGTH_SHORT);
        toastRemoveFromFavorites.show();
    }

    public String convertTime(String totalTime) {
        int timeInteger = Integer.parseInt(totalTime);
        if (timeInteger == 60) {
            return "60 sek";
        }
        int seconds = timeInteger % 60;
        int minutes = timeInteger / 60;
        if (minutes > 0) {
            return "" + minutes + " min " + seconds + " sek";
        } else {
            return "" + seconds + " sek";
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_final_page);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        redHeartButton = findViewById(R.id.finalRedHeartButton);
        redHeartButton.setEnabled(false);
        redHeartButton.setAlpha(0);
        greyHeartButton = findViewById(R.id.finalGreyHeartButton);
        toastAddToFavorites = Toast.makeText(this, "non-null toast", Toast.LENGTH_SHORT);
        toastRemoveFromFavorites = Toast.makeText(this, "non-null toast", Toast.LENGTH_SHORT);
        finalValuesTextView= findViewById(R.id.finalValuesTextView);

        seekBar = findViewById(R.id.finalSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(7);

        finalValues = brewValues.get(0)+" g\n" +
                brewValues.get(1)+" ml / g\n" +
                brewValues.get(2)+"\n" +
                brewValues.get(3)+" \u00B0C\n"+
                brewValues.get(4)+" ml\n"+
                convertTime(brewValues.get(5))+"\n"+
                convertTime(brewValues.get(6))+"";

        finalValuesTextView.setText(finalValues);

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