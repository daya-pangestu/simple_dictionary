package com.daya.jojoman.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.DictIndonesia;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KataINDAdapter extends RecyclerView.Adapter <KataINDAdapter.KataHolder>{
    private List<DictIndonesia> listKamus;
    LayoutInflater layoutInflater;

    public KataINDAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);

    }
    //list didapat dari database, tambahan konstruktor


    @NonNull
    @Override
    public KataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recycler, parent, false);

        return new KataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KataHolder holder, int position) {
        holder.kata.setText(listKamus.get(position).getKata());
    }

    public void setListKamus(List<DictIndonesia> dictIndonesias) {
        listKamus = dictIndonesias;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        //size dari yang didapat di list
        if (listKamus != null){
            return listKamus.size();
        } else return 0;

    }

    public class KataHolder extends RecyclerView.ViewHolder {
        private TextView kata, penjelasan;

        public KataHolder(View view) {
            super(view);
            kata = view.findViewById(R.id.recycler_kata);
            penjelasan = view.findViewById(R.id.recycler_penjelasan);
        }
    }
}
