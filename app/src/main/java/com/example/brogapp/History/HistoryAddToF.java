package com.example.brogapp.History;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.brogapp.R;

public class HistoryAddToF extends AppCompatActivity {

    public ImageView iconPic;
    private Button chooseIconButton;

    public void pickIconButtonPushed(View view){
        Toast.makeText(this,"Pick icon button pushed",Toast.LENGTH_SHORT).show();
        HistorySelectIconFragment hsif = new HistorySelectIconFragment();
        hsif.show(getSupportFragmentManager(),"test");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_add_to_favorites);

        iconPic = findViewById(R.id.pickIconIV);
        chooseIconButton = findViewById(R.id.pickIconButton);

        setIconImage();
    }

    public void setIconImage(){
        Toast.makeText(this,""+HistoryHoldIconNumber.iconNumber,Toast.LENGTH_SHORT).show();
        switch (HistoryHoldIconNumber.iconNumber){
            case 0:
                iconPic.setImageResource(R.drawable.coffee_pic);
                break;
            case 1:
                iconPic.setImageResource(R.drawable.coffeetwo_pic);
                break;
            case 2:
                iconPic.setImageResource(R.drawable.coffeethree_pic);
                break;
            case 3:
                iconPic.setImageResource(R.drawable.coffeefour_pic);
                break;
            case 4:
                iconPic.setImageResource(R.drawable.coffeefive_pic);
                break;
            case 5:
                iconPic.setImageResource(R.drawable.coffeesix_pic);
                break;
            default:
                iconPic.setImageResource(R.drawable.coffee_pic);
        }

    }
}