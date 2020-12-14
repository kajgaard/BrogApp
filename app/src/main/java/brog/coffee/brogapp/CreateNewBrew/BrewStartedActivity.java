package brog.coffee.brogapp.CreateNewBrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.StartActivity.HomePage;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import brog.coffee.brogapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BrewStartedActivity extends AppCompatActivity {

    ArrayList<String> brewValues; // Contains the values from the brew the user just created
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Date date;
    String dateName;
    String text;
    TextView message;

    public void okPushed(View view){
        finish();
    } // Simply finishes the activity

    private void saveBrew(){    // Saving the brew to FireStore, history collection

        // Formatting the date to wanted format
        date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm",Locale.GERMAN);
        dateName = formatter.format(date);
        Log.i("date",formatter.format(date));

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Map<String, Object> newbrew = new HashMap<>(); // New hashmap, will be populated and sent to FireStore
        newbrew.put("brewName", dateName);
        newbrew.put("brewDescription", "Nyt bryg. Der er ikke tilføjet beskrivelse.");
        newbrew.put("brewScore", "0.0");
        newbrew.put("imageRessource", 0);
        newbrew.put("coffeeAmount", brewValues.get(0));
        newbrew.put("grindSize", brewValues.get(2));
        newbrew.put("waterRatio", brewValues.get(1));
        newbrew.put("brewTemp", brewValues.get(3));
        newbrew.put("bloomWater", brewValues.get(4));
        newbrew.put("bloomTime", brewValues.get(5));
        newbrew.put("brewTime", brewValues.get(6));
        newbrew.put("timeStamp",System.currentTimeMillis());

        // Storing in FireStore
        fStore.collection("users").document(userID).collection("history").document().set(newbrew);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_started);

        message = findViewById(R.id.finalIntroTextView3);
        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");
        text = (String) getIntent().getSerializableExtra("text");
        message.setText(text); // Meassage is different depending on where this activity is started from

        saveBrew();

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home item as selected
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