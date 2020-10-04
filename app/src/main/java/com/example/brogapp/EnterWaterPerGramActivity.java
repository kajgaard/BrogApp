package com.example.brogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EnterWaterPerGramActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;
    int minimumGram = 20;
    int maximumGram = 100;
    int totalBrew;
    int testTotalBrew;
    TextView waterValueTextView;
    TextView waterTotalTextView;


    public void waterNextButtonPushed(View view) {
        Log.i("Water per gram", "Next button pushed");
//        brewValues.set(1,Integer.toString(gramsInteger));
//        Intent intent = new Intent(EnterWaterPerGramActivity.this, EnterCoarseActivity.class);
//        intent.putExtra("brewValues", brewValues);
//        startActivity(intent);

    }

    public void waterUpButtonPush(View view) {
        Log.i("Water per gram", "Up button pushed");
        testTotalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / (gramsInteger + 1));

        if (gramsInteger < maximumGram && testTotalBrew >= 100) {
            gramsInteger = gramsInteger + 1;
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
            waterValueTextView.setText(gramsInteger + " g");
            waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);
        }
    }

    public void waterDownButtonPush(View view) {
        Log.i("Water per gram", "Down button pushed");
        testTotalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / (gramsInteger - 1));

        if (gramsInteger > minimumGram && testTotalBrew <= 600) {
            gramsInteger = gramsInteger - 1;
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
            waterValueTextView.setText(gramsInteger + " g");
            waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);
        }
    }

    public void waterPreviousButtonPushed(View view) {
        Log.i("Water per gram", "Previous button pushed");
        brewValues.set(1, Integer.toString(gramsInteger));
        Intent intent = new Intent(EnterWaterPerGramActivity.this, EnterGramsActivity.class);
        intent.putExtra("brewValues", brewValues);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_water_per_gram);

        seekBar = findViewById(R.id.waterSeekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(1);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        waterValueTextView = findViewById(R.id.waterValueTextView);
        waterTotalTextView = findViewById(R.id.waterTotalTextView);
        gramsInteger = Integer.parseInt(brewValues.get(1));
        waterValueTextView.setText(gramsInteger + " g");

        totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
        if (totalBrew > 600) {
            gramsInteger = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / 600) + 1;
            waterValueTextView.setText(gramsInteger + " g");
            totalBrew = (int) (Integer.parseInt(brewValues.get(0)) * 1000 / gramsInteger);
        }
        waterTotalTextView.setText("Samlet mængde kaffebryg: " + totalBrew);
    }


}