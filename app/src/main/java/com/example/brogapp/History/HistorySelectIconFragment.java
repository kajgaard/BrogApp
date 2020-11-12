package com.example.brogapp.History;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.brogapp.R;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static android.view.View.*;

public class HistorySelectIconFragment extends DialogFragment implements OnClickListener {

    private ImageView pic0, pic1, pic2, pic3, pic4, pic5;
    int selectedIcon = 0;
    ImageView iconImage;

    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_icon_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pic0 = view.findViewById(R.id.coffeeIM0);
        pic1 = view.findViewById(R.id.coffeeIM1);
        pic2 = view.findViewById(R.id.coffeeIM2);
        pic3 = view.findViewById(R.id.coffeeIM3);
        pic4 = view.findViewById(R.id.coffeeIM4);
        pic5 = view.findViewById(R.id.coffeeIM5);


        pic0.setOnClickListener(this);
        pic1.setOnClickListener(this);
        pic2.setOnClickListener(this);
        pic3.setOnClickListener(this);
        pic4.setOnClickListener(this);
        pic5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == pic0) {
            HistoryHoldIconNumber.iconNumber = 0;
        } else if (view == pic1) {
            HistoryHoldIconNumber.iconNumber = 1;
        } else if (view == pic2) {
            HistoryHoldIconNumber.iconNumber = 2;
        } else if (view == pic3) {
            HistoryHoldIconNumber.iconNumber = 3;
        } else if (view == pic4) {
            HistoryHoldIconNumber.iconNumber = 4;
        } else if (view == pic5) {
            HistoryHoldIconNumber.iconNumber = 5;
        }

        Log.i("coffee icon: " , ""+HistoryHoldIconNumber.iconNumber);

        ((HistoryAddToF)getActivity()).setIconImage();

        dismiss();
    }
}
