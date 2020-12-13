package brog.coffee.brogapp.StartActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import brog.coffee.brogapp.R;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private ArrayList<Integer> mList;

    public static class ContentHolder extends RecyclerView.ViewHolder {

        ImageView mImage;


        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.newsImage);

        }
    }

    public ContentAdapter(ArrayList<Integer> contentList) {
        mList = contentList;
    }

    @NonNull
    @Override
    public ContentAdapter.ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_adapter, parent, false);
        ContentAdapter.ContentHolder contentvh = new ContentAdapter.ContentHolder(v);
        return contentvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ContentHolder holder, int position) {
        int currentItem = mList.get(position);
        holder.mImage.setImageResource(currentItem);
        Log.i("BrewFaveAdapter getImageResource",Integer.toString(currentItem));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
