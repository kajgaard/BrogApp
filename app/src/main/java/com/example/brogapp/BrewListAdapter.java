package com.example.brogapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BrewListAdapter extends RecyclerView.Adapter<BrewListAdapter.BrewViewHolder> {

    private ArrayList<BrewItem> mList;

    public static class BrewViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView brewName, brewDescription, brewScore;

        public BrewViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.itemPicIV);
            brewName = itemView.findViewById(R.id.brewNameTV);
            brewDescription = itemView.findViewById(R.id.brewDescriptionTV);
            brewScore = itemView.findViewById(R.id.scoreTV);
        }
    }

    public BrewListAdapter (ArrayList<BrewItem> brewList){
        mList = brewList;
    }

    @NonNull
    @Override
    public BrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faves_adapter, parent, false);
        BrewViewHolder bvh = new BrewViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BrewViewHolder holder, int position) {
        BrewItem currentItem = mList.get(position);
        holder.mImage.setImageResource(currentItem.getImageResource());
        holder.brewName.setText(currentItem.getBrewName());
        holder.brewDescription.setText(currentItem.getBrewDescription());
        holder.brewScore.setText(currentItem.getBrewScore());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
