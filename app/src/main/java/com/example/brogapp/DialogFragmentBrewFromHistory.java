package com.example.brogapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.brogapp.CreateNewBrew.BrewStartedActivity;
import com.example.brogapp.History.HistoryAddToF;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class DialogFragmentBrewFromHistory extends DialogFragment implements View.OnClickListener {

    Button brewBtn;
    Button toFavBtn;
    DocumentSnapshot snapshot;

    public DialogFragmentBrewFromHistory() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return getActivity().getLayoutInflater().inflate(R.layout.tap_brew_in_history_fragtment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brewBtn = view.findViewById(R.id.brewBtn);
        brewBtn.setOnClickListener(this);
        toFavBtn = view.findViewById(R.id.toFaveBtn);
        toFavBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == brewBtn){
            ArrayList<String> brewValues = new ArrayList<>();
            brewValues.add("20");       // Grams of coffee
            brewValues.add("60");       // grams of coffee per liter of water
            brewValues.add("Medium");   // Coffee ground coarseness
            brewValues.add("92");       // Water temperature
            brewValues.add("40");       // Bloom water
            brewValues.add("30");       // Bloom Time
            brewValues.add("180");      // Brew time

            Intent intent = new Intent(getContext(), BrewStartedActivity.class);
            intent.putExtra("brewValues", brewValues);
            intent.putExtra("text","Velbekomme!");
            startActivity(intent);
            dismiss();
        }

        if (view == toFavBtn){

            Intent intent = new Intent(getContext(), HistoryAddToF.class);
            intent.putExtra("IdOfSelectedHistory", snapshot.getId());
            startActivity(intent);
            dismiss();
        }
    }

    public void setDocumentSnapshot (DocumentSnapshot snapshot){
        this.snapshot = snapshot;
    }
}
