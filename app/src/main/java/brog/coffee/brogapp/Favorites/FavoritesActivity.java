package brog.coffee.brogapp.Favorites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import brog.coffee.brogapp.BrewActivity.BrewItem;
import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.CreateNewBrew.BrewStartedActivity;
import brog.coffee.brogapp.StartActivity.HomePage;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import brog.coffee.brogapp.R;

import brog.coffee.brogapp.BrewActivity.StartBrewFragment;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnListItemClick {

    private static final String TAG = "FavoritesActivity";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FavoritesAdapter mAdapter;
    StartBrewFragment startBrewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        //Query for database
        Query query = fStore.collection("users").document(userID).collection("favorites");

        //Paging so in case we have a lot of data in database, it loads in pages
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(15)
                .setPageSize(5)
                .build();

        //Recycler options (github dependency) //se youtube.com/watch?v=LatlcDZhpd4
        FirestorePagingOptions<BrewItem> options = new FirestorePagingOptions.Builder<BrewItem>()
                .setLifecycleOwner(this) //No longer need onStart() and onStop()
                .setQuery(query, config, new SnapshotParser<BrewItem>() {
                    @NonNull
                    @Override
                    public BrewItem parseSnapshot(@NonNull DocumentSnapshot snapshot) { //so we can get ID for all documents in collection
                        BrewItem brewItem = snapshot.toObject(BrewItem.class);
                        String brewId = snapshot.getId();
                        brewItem.setbrewID();
                        return brewItem;
                    }
                })
                .build();

        //Adapter
        mAdapter = new FavoritesAdapter(options, this);
        mRecyclerView = findViewById(R.id.historyHolderRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        //Initialize and assign navbar variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);

        //Set home iteam as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

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

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("CLICK","item was clicked at pos. " + position + "\nID is " + snapshot.getId());

        startBrewFragment = new StartBrewFragment();
        startBrewFragment.setDocumentSnapshot(snapshot);
        startBrewFragment.show(getSupportFragmentManager(), "Getting ready to brew");
    }

    public void tilbagePush(View view) {
        startBrewFragment.dismiss();
    }

    public void brygPush(View view) {
        ArrayList<String> brewValues = new ArrayList<>();
        brewValues.add("20");       // Grams of coffee
        brewValues.add("60");       // grams of coffee per liter of water
        brewValues.add("Medium");   // Coffee ground coarseness
        brewValues.add("92");       // Water temperature
        brewValues.add("40");       // Bloom water
        brewValues.add("30");       // Bloom Time
        brewValues.add("180");      // Brew time

        Intent intent = new Intent(FavoritesActivity.this, BrewStartedActivity.class);
        intent.putExtra("brewValues", brewValues);
        intent.putExtra("text","Velbekomme!");
        startActivity(intent);
        startBrewFragment.dismiss();
    }
}