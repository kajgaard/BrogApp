package com.example.brogapp.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.brogapp.R;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static android.view.View.*;

public class HistorySelectIconFragment extends DialogFragment implements OnClickListener{


    @Override
    public void onClick(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_icon_select, container, false);
    }
}
