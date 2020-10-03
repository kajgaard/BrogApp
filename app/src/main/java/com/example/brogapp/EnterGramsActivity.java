package com.example.brogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterGramsActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;
    int minimumGrams = 10;
    int maximumGrams = 50;

    TextView gramsTextView;

    public void nextButtonPushed(View view) {
        brewValues.set(0,Integer.toString(gramsInteger));
        Toast.makeText(this, brewValues.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Button","Next button pushed");

        Intent intent = new Intent(EnterGramsActivity.this,EnterWaterPerGramActivity.class);
        intent.putExtra("brewValues",brewValues);
        startActivity(intent);

    }

    public void upButtonpush(View view){
        if(gramsInteger<maximumGrams) {
            gramsInteger++;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    public void downButtonpush(View view){
        if(gramsInteger>minimumGrams) {
            gramsInteger--;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_grams);

        seekBar = findViewById(R.id.gramsSeekBar);
        seekBar.setEnabled(false);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        gramsTextView = findViewById(R.id.gramsTextView);
        gramsInteger=Integer.parseInt(brewValues.get(0));
        gramsTextView.setText(gramsInteger+" g");
    }
}