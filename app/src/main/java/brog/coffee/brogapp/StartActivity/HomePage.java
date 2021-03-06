package brog.coffee.brogapp.StartActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import brog.coffee.brogapp.BrewActivity.BrewItem;
import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.Favorites.FavoritesActivity;
import brog.coffee.brogapp.History.HistoryActivity;

import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.R;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button favorites, history;
    TextView date;
    String dayString;
    RecyclerView content;
    RecyclerView.Adapter contentAdapter;
    RecyclerView.LayoutManager contentLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Dag i ugen beregnes
        Calendar cal = Calendar.getInstance();

        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 2: dayString = "MANDAG";
                    break;
            case 3: dayString = "TIRSDAG";
                break;
            case 4: dayString = "ONSDAG";
                break;
            case 5: dayString = "TORSDAG";
                break;
            case 6: dayString = "FREDAG";
                break;
            case 0: dayString = "LØRDAG";
                break;
            case 1: dayString = "SØNDAG";
                break;
            default: dayString = "";
        }


        date = findViewById(R.id.dateTV);
        date.setText(dayString+" "+new SimpleDateFormat("d. MMMM").format(cal.getTime()).toUpperCase());

        favorites = findViewById(R.id.newFavBtn);
        favorites.setOnClickListener(this);

        history = findViewById(R.id.newHistBtn);
        history.setOnClickListener(this);

        content = findViewById(R.id.contentHolderRV);
        content.setHasFixedSize(true);

        //Fill out recyclerView Favoritter
        ArrayList<Integer> contentList = new ArrayList<>();
        contentList.add(R.drawable.news1);
        contentList.add(R.drawable.news2);
        contentList.add(R.drawable.news3);


        contentLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        contentAdapter = new ContentAdapter(contentList);

        content.setLayoutManager(contentLayoutManager);
        content.setAdapter(contentAdapter);


        //Test kode for at teste Crashlytics
        /*
        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

         */

        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //Set up listener, for determine if other icon is pressed
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if(view == favorites){
            startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
            //finish();
        }else if(view == history){
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            //finish();
        }
    }
}