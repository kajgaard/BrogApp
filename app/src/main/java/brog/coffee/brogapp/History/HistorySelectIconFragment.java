package brog.coffee.brogapp.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import brog.coffee.brogapp.R;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static android.view.View.*;

public class HistorySelectIconFragment extends DialogFragment implements OnClickListener {

    private ImageView pic0, pic1, pic2, pic3, pic4, pic5;
    int selectedIcon = 0;

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
            selectedIcon = 0;
        } else if (view == pic1) {
            selectedIcon = 1;
        } else if (view == pic2) {
            selectedIcon = 2;
        } else if (view == pic3) {
            selectedIcon = 3;
        } else if (view == pic4) {
            selectedIcon = 4;
        } else if (view == pic5) {
            selectedIcon = 5;
        }


        try {               // To mitigate risk of nullpointer exeption
            ((HistoryAddToF) getActivity()).setIconImage(selectedIcon);
        } catch (Exception e){
            e.printStackTrace();
        }

        dismiss();
    }
}
