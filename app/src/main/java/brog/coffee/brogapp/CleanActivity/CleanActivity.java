package brog.coffee.brogapp.CleanActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.R;
import brog.coffee.brogapp.ScanActivity.ScanActivity;
import brog.coffee.brogapp.StartActivity.HomePage;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CleanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);

        tv = findViewById(R.id.textView);
        //tv.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Toast.makeText(this,"Ikke implementeret",Toast.LENGTH_SHORT).show();

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_wash);

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
                        startActivity(new Intent(getApplicationContext(),CleanActivity.class));
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

    @Override
    public void onClick(View view) {
//        if (view == tv){
//            //For populating database
//            Map<String, Object> newbrew = new HashMap<>();
//            newbrew.put("brewName", "Sunday");
//            newbrew.put("brewDescription", "Smager meget godt");
//            newbrew.put("brewScore", "4.1");
//            newbrew.put("imageRessource", 0);
//            newbrew.put("coffeeAmount", "30");
//            newbrew.put("grindSize", "medium");
//            newbrew.put("waterRatio", "70");
//            newbrew.put("brewTemp", "93");
//            newbrew.put("bloomWater", "45");
//            newbrew.put("bloomTime", "30");
//            newbrew.put("brewTime", "180");
//            //newbrew.put("timeStamp",1605771956572L);
//
//            Map<String, Object> newbrew1 = new HashMap<>();
//            newbrew1.put("brewName", "London");
//            newbrew1.put("brewDescription", "God med Starbuks bønnerne!");
//            newbrew1.put("brewScore", "4.5");
//            newbrew1.put("imageRessource", 1);
//            newbrew1.put("coffeeAmount", "20");
//            newbrew1.put("grindSize", "medium");
//            newbrew1.put("waterRatio", "60");
//            newbrew1.put("brewTemp", "93");
//            newbrew1.put("bloomWater", "45");
//            newbrew1.put("bloomTime", "30");
//            newbrew1.put("brewTime", "180");
//            //newbrew.put("timeStamp",1606383638000L);
//
//            Map<String, Object> newbrew2 = new HashMap<>();
//            newbrew2.put("brewName", "New York");
//            newbrew2.put("brewDescription", "Når veninderne er på besøg!");
//            newbrew2.put("brewScore", "3.8");
//            newbrew2.put("imageRessource", 2);
//            newbrew2.put("coffeeAmount", "20");
//            newbrew2.put("grindSize", "medium");
//            newbrew2.put("waterRatio", "60");
//            newbrew2.put("brewTemp", "93");
//            newbrew2.put("bloomWater", "45");
//            newbrew2.put("bloomTime", "30");
//            newbrew2.put("brewTime", "180");
//
//            Map<String, Object> newbrew3 = new HashMap<>();
//            newbrew3.put("brewName", "Copenhagen");
//            newbrew3.put("brewDescription", "Svigermors ynglings! Peter kan ikke lide den så meget");
//            newbrew3.put("brewScore", "4.3");
//            newbrew3.put("imageRessource", 3);
//            newbrew3.put("coffeeAmount", "20");
//            newbrew3.put("grindSize", "medium");
//            newbrew3.put("waterRatio", "60");
//            newbrew3.put("brewTemp", "93");
//            newbrew3.put("bloomWater", "45");
//            newbrew3.put("bloomTime", "30");
//            newbrew3.put("brewTime", "180");
//
//            fStore.collection("brews").document().set(newbrew);
//            fStore.collection("brews").document().set(newbrew1);
//            fStore.collection("brews").document().set(newbrew2);
//            fStore.collection("brews").document().set(newbrew3);
//        }
    }
}