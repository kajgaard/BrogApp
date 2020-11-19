package com.example.brogapp.Favorites;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brogapp.BrewItem;
import com.example.brogapp.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class FavoritesAdapter extends FirestorePagingAdapter<BrewItem, FavoritesAdapter.FavoritesViewHolder> {

    private OnListItemClick onListItemClick;

    public FavoritesAdapter(@NonNull FirestorePagingOptions<BrewItem> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position, @NonNull BrewItem model) {
        holder.mImage.setImageResource(model.getImageResource());
        holder.brewName.setText(model.getBrewName());
        holder.brewDescription.setText(model.getBrewDescription());
        holder.brewScore.setText(model.getBrewScore());

    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.faves_adapter, parent, false);
        return new FavoritesViewHolder(v);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);

        switch(state){
            case ERROR:
                Log.d("PAGING_LOG","Error while loading data");
                break;

            case FINISHED:
                Log.d("PAGING_LOG","Finished loading data");
                break;

            case LOADED:
                Log.d("PAGING_LOG","Items loaded" + getItemCount());
                break;

            case LOADING_MORE:
                Log.d("PAGING_LOG","currently loading next page" + getItemCount());
                break;

            case LOADING_INITIAL:
                Log.d("PAGING_LOG","Loading first page" + getItemCount());
                break;

        }
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView mImage;
    TextView brewName, brewDescription, brewScore;

    public FavoritesViewHolder(@NonNull View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.itemPicIV);
        brewName = itemView.findViewById(R.id.brewNameTV);
        brewDescription = itemView.findViewById(R.id.brewDescriptionTV);
        brewScore = itemView.findViewById(R.id.scoreTV);

        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnListItemClick {
        void onItemClick (DocumentSnapshot snapshot, int position);
    }

}
