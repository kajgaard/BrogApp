package brog.coffee.brogapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import brog.coffee.brogapp.R;
import com.google.firebase.firestore.DocumentSnapshot;

public class StartBrewFragment extends DialogFragment implements View.OnClickListener {

    Button startBrewButton;
    Button backButton;
    TextView brewValues;
    DocumentSnapshot snapshot;
    TextView titel;
    TextView descrip;

    String name;
    String description;
    String grams;
    String waterPerGram;
    String grind;
    String temp;
    String bloomWater;
    String bloomTime;
    String finalValues;
    String brewTime;

    public StartBrewFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return getActivity().getLayoutInflater().inflate(R.layout.fragment_startbrew,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // startBrewButton = view.findViewById(R.id.XXXXXXXXX);
        // startBrewButton.setOnClickListener(this);
        // backButton = view.findViewById(R.id.YYYYYYYYY);
        // backButton.setOnClickListener(this);
        brewValues = view.findViewById(R.id.brewValuesTV);
        titel = view.findViewById(R.id.titelTV);
        descrip = view.findViewById(R.id.descriptionTV);

        brewValues.setOnClickListener(this);
        name = snapshot.get("brewName").toString();
        description = snapshot.get("brewDescription").toString();
        grams = snapshot.get("coffeeAmount").toString();
        waterPerGram = snapshot.get("waterRatio").toString();
        grind = snapshot.get("grindSize").toString();
        temp = snapshot.get("brewTemp").toString();
        bloomWater = snapshot.get("bloomWater").toString();
        bloomTime = convertTime(snapshot.get("bloomTime").toString());
        brewTime = convertTime(snapshot.get("brewTime").toString());

        finalValues = grams+" g\n" +
                waterPerGram+" ml / g\n" +
                grind+"\n" +
                temp+" \u00B0C\n"+
                bloomWater+" ml\n"+
                bloomTime+"\n"+
                brewTime;

        Log.i("toast",finalValues);
        Log.i("toast",snapshot.getData().toString());
        brewValues.setText(finalValues);
        titel.setText(name);
        descrip.setText(description);


    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(),"test clicked",Toast.LENGTH_SHORT).show();
    }

    public void setDocumentSnapshot (DocumentSnapshot snapshot){
        this.snapshot = snapshot;
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


}
