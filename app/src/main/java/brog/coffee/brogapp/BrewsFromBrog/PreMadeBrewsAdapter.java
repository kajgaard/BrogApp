package brog.coffee.brogapp.BrewsFromBrog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import brog.coffee.brogapp.BrewActivity.BrewItem;
import brog.coffee.brogapp.Favorites.FavoritesAdapter;
import brog.coffee.brogapp.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;


public class PreMadeBrewsAdapter extends FirestorePagingAdapter<BrewItem, PreMadeBrewsAdapter.BrewFaveViewHolder> {

    private FavoritesAdapter.OnListItemClick onListItemClick;

    public PreMadeBrewsAdapter(@NonNull FirestorePagingOptions<BrewItem> options, FavoritesAdapter.OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull PreMadeBrewsAdapter.BrewFaveViewHolder holder, int position, @NonNull BrewItem model) {
        switch (model.getImageRessource()){
            case 0:
                holder.mImage.setImageResource(R.drawable.coffee_pic);
                break;
            case 1:
                holder.mImage.setImageResource(R.drawable.coffeetwo_pic);
                break;
            case 2:
                holder.mImage.setImageResource(R.drawable.coffeethree_pic);
                break;
            case 3:
                holder.mImage.setImageResource(R.drawable.coffeefour_pic);
                break;
            case 4:
                holder.mImage.setImageResource(R.drawable.coffeefive_pic);
                break;
            case 5:
                holder.mImage.setImageResource(R.drawable.coffeesix_pic);
                break;
            default:
                holder.mImage.setImageResource(R.drawable.coffee_pic);
        }
        holder.brewName.setText(model.getBrewName());
        holder.brewScore.setText(model.getBrewScore());
    }

    @NonNull
    @Override
    public PreMadeBrewsAdapter.BrewFaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brewfaves_adapter, parent, false);
        return new PreMadeBrewsAdapter.BrewFaveViewHolder(v);
    }

    public class BrewFaveViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        TextView brewName, brewScore;

        public BrewFaveViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.brewFavePic);
            brewName = itemView.findViewById(R.id.brewFavName);
            brewScore = itemView.findViewById(R.id.brewFaveScore);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);

        switch (state) {
            case ERROR:
                Log.d("PAGING_LOG", "Error while loading data");
                break;

            case FINISHED:
                Log.d("PAGING_LOG", "Finished loading data");
                break;

            case LOADED:
                Log.d("PAGING_LOG", "Items loaded" + getItemCount());
                break;

            case LOADING_MORE:
                Log.d("PAGING_LOG", "currently loading next page" + getItemCount());
                break;

            case LOADING_INITIAL:
                Log.d("PAGING_LOG", "Loading first page" + getItemCount());
                break;
        }
    }
}
