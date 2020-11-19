package com.example.brogapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brogapp.BrewFromFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.brogapp.CreateNewBrew.BrewStartedActivity;
import com.example.brogapp.Favorites.FavoritesActivity;

public class DialogFragmentBrewFromHistory extends DialogFragment implements View.OnClickListener {

    Button yesBtn;
    TextView noBtn;
    BrewFromFirestore brewFromFirestore;

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
        yesBtn = view.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == yesBtn){
            //brewFromFirestore.extractUserValues("3Xki8keCiRPH8T6ZYZzv",false);

            Toast.makeText(this.getContext(), "Arraylist: ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(),CleanActivity.class));

        }
    }
}
