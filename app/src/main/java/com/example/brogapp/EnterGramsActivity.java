package com.example.brogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterGramsActivity extends AppCompatActivity {

    ArrayList<String> brewValues;
    SeekBar seekBar;
    int gramsInteger;

    TextView gramsTextView;

    public void testButtonPushed(View view) {
        brewValues.set(0,Integer.toString(gramsInteger));
        Toast.makeText(this, brewValues.toString(), Toast.LENGTH_SHORT).show();
    }

    public void upButtonpush(View view){
        if(gramsInteger<60) {
            gramsInteger++;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    public void downButtonpush(View view){
        if(gramsInteger>10) {
            gramsInteger--;
            gramsTextView.setText(gramsInteger + " g");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_grams);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setEnabled(false);

        brewValues = (ArrayList<String>) getIntent().getSerializableExtra("brewValues");

        gramsTextView = findViewById(R.id.gramsTextView);
        gramsInteger=Integer.parseInt(brewValues.get(0));
        gramsTextView.setText(gramsInteger+" g");





    }


}