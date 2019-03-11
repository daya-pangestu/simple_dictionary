package com.daya.jojoman.recyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class favoritAdapter extends RecyclerView.Adapter<favoritAdapter.KataHolderFavorite> {


    @NonNull
    @Override
    public KataHolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull KataHolderFavorite holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class KataHolderFavorite extends RecyclerView.ViewHolder {

        public KataHolderFavorite(@NonNull View itemView) {
            super(itemView);
        }
    }
}
