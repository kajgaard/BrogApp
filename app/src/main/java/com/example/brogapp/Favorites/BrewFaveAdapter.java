package com.example.brogapp.Favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brogapp.BrewItem;
import com.example.brogapp.R;

import java.util.ArrayList;

public class BrewFaveAdapter extends RecyclerView.Adapter<BrewFaveAdapter.BrewFaveHolder> {

    private ArrayList<BrewItem> mList;

    public static class BrewFaveHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView brewName, brewScore;

        public BrewFaveHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.brewFavePic);
            brewName = itemView.findViewById(R.id.brewFavName);
            brewScore = itemView.findViewById(R.id.brewFaveScore);
        }
    }

    public BrewFaveAdapter (ArrayList<BrewItem> brewList){
        mList = brewList;
    }

    @NonNull
    @Override
    public BrewFaveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brewfaves_adapter, parent, false);
        BrewFaveHolder bfh = new BrewFaveHolder(v);
        return bfh;
    }

    @Override
    public void onBindViewHolder(@NonNull BrewFaveHolder holder, int position) {
        BrewItem currentItem = mList.get(position);
        holder.mImage.setImageResource(currentItem.getImageResource());
        holder.brewName.setText(currentItem.getBrewName());
        holder.brewScore.setText(currentItem.getBrewScore());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
