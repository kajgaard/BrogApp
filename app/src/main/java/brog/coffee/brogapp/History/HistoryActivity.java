package brog.coffee.brogapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import brog.coffee.brogapp.BrewActivity.BrewFromFirestore;
import brog.coffee.brogapp.BrewActivity.BrewItem;
import brog.coffee.brogapp.BrewActivity.BrewMainActivity;
import brog.coffee.brogapp.CleanActivity.CleanActivity;
import brog.coffee.brogapp.StartActivity.HomePage;
import brog.coffee.brogapp.ProfileActivity.ProfilePage;
import brog.coffee.brogapp.ScanActivity.ScanActivity;

import brog.coffee.brogapp.R;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static brog.coffee.brogapp.R.id.historyHolderRV;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.OnListItemClick{

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    HistoryAdapter mAdapter;
    String clickedBrewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //Query for database. Ordered by timestamp.
        CollectionReference timeStampRef = fStore.collection("users").document(userID).collection("history");
        Query query = timeStampRef.orderBy("timeStamp", Query.Direction.DESCENDING);
        //Query query = fStore.collection("users").document(userID).collection("history");  // without ordering

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
        mAdapter = new HistoryAdapter(options, this);
        mRecyclerView = findViewById(historyHolderRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        //mAdapter = new BrewListAdapter(listOfFaves);

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

    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("CLICK","item was clicked at pos. " + position + "\nID is " + snapshot.getId());
        Log.d("CLICK","in snapshot is " + snapshot.getData() + "");
        clickedBrewID = snapshot.getId();

        DialogFragmentBrewFromHistory myFragment = new DialogFragmentBrewFromHistory();
        myFragment.setDocumentSnapshot(snapshot); // The fragment is handed the snapshot before .show()
        myFragment.show(getSupportFragmentManager(),"Brew from History Fragment");
    }
}