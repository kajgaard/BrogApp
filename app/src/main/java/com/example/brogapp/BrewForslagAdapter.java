package com.example.brogapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BrewForslagAdapter extends RecyclerView.Adapter<BrewForslagAdapter.BrewForslagHolder> {

    private ArrayList<BrewItem> mList;

    public static class BrewForslagHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView brewName, brewScore;

        public BrewForslagHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.brewForslagPic);
            brewName = itemView.findViewById(R.id.brewForslagName);
            brewScore = itemView.findViewById(R.id.brewForslagScore);
        }
    }

    public BrewForslagAdapter (ArrayList<BrewItem> brewList){
        mList = brewList;
    }

    @NonNull
    @Override
    public BrewForslagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brewforslag_adapter, parent, false);
        BrewForslagHolder bfh = new BrewForslagHolder(v);
        return bfh;
    }

    @Override
    public void onBindViewHolder(@NonNull BrewForslagHolder holder, int position) {
        BrewItem currentItem = mList.get(position);
        holder.mImage.setImageResource(currentItem.getImageRessource());
        holder.brewName.setText(currentItem.getBrewName());
        holder.brewScore.setText(currentItem.getBrewScore());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
