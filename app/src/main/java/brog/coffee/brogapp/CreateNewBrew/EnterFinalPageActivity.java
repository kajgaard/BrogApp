package brog.coffee.brogapp.CreateNewBrew;

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

import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.StartActivity.HomePage;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import brog.coffee.brogapp.R;
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

    public void finalStartButtonPushed(View view) { // User pushes BREW!
        Log.i("Final Page", "Bryg button pushed");
        Intent intent = new Intent(EnterFinalPageActivity.this, BrewStartedActivity.class);
        intent.putExtra("brewValues", brewValues); // Arraylist with all brewvalues transferred to next activity
        intent.putExtra("text","Det nye bryg er nu gemt i din historik"); // Text to be displayed on next screen
        startActivity(intent);
        finish();
    }

    public void finalPreviousButtonPushed(View view) { // User pushes previous
        Log.i("Temp", "Previous button pushed");
        Intent intent = new Intent(EnterFinalPageActivity.this, EnterBrewTimeActivity.class);
        intent.putExtra("brewValues", brewValues); // Arraylist transferred to previous screen
        startActivity(intent);
        finish();
    }

    public void pushGreyHeart(View view) { // Early idea to have heart-button toggling favorited/not favorited
        isFavorited = true;
        greyHeartButton.setAlpha(0);
        redHeartButton.setAlpha(1);
        greyHeartButton.setEnabled(false);
        redHeartButton.setEnabled(true);

        if (toastRemoveFromFavorites.getView().isShown()) { // Early idea to have heart-button toggling favorited/not favorited
            toastRemoveFromFavorites.cancel();
        }
//        toastAddToFavorites = Toast.makeText(this, "Tilføjet til favoritter!", Toast.LENGTH_SHORT);
//        toastAddToFavorites.show();
    }

    public void pushRedHeart(View view) { // Early idea to have heart-button toggling favorited/not favorited
        isFavorited = false;
        greyHeartButton.setAlpha(1);
        redHeartButton.setAlpha(0);
        greyHeartButton.setEnabled(true);
        redHeartButton.setEnabled(false);
        if (toastAddToFavorites.getView().isShown()) {
            toastAddToFavorites.cancel();
        }
//        toastRemoveFromFavorites = Toast.makeText(this, "Fjernet fra favoritter", Toast.LENGTH_SHORT);
//        toastRemoveFromFavorites.show();
    }

    public String convertTime(String totalTime) { // Convert string of seconds to sec/min
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

        redHeartButton = findViewById(R.id.finalRedHeartButton); // Early idea to have heart-button toggling favorited/not favorited
        redHeartButton.setEnabled(false);
        redHeartButton.setAlpha(0);
        greyHeartButton = findViewById(R.id.finalGreyHeartButton);
        toastAddToFavorites = Toast.makeText(this, "non-null toast", Toast.LENGTH_SHORT);
        toastRemoveFromFavorites = Toast.makeText(this, "non-null toast", Toast.LENGTH_SHORT);
        finalValuesTextView= findViewById(R.id.finalValuesTextView);

        seekBar = findViewById(R.id.finalSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(7);

        // Creates entire right column with values to show on screen
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