package com.example.brogapp.History;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brogapp.R;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HistoryAddToFavorites extends DialogFragment implements View.OnClickListener {

    public ImageView iconPic;
    private  Button chooseIconButton;
    public int selectedIcon = 0;

    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_history_add_to_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iconPic = view.findViewById(R.id.pickIconIV);
        chooseIconButton = view.findViewById(R.id.pickIconButton);
        iconPic.setOnClickListener(this);
        chooseIconButton.setOnClickListener(this);

        setIconImage();
    }

    private void setIconImage(){
        Toast.makeText(getContext(),""+HistoryHoldIconNumber.iconNumber,Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        if(view == iconPic || view == chooseIconButton){
            Log.i("iconbutton","pushed");
            HistorySelectIconFragment hsif = new HistorySelectIconFragment();
            hsif.show(getFragmentManager(),"test");
        }
    }
}
