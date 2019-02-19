package com.daya.jojoman.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.DictIndonesia;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SearchSugest extends SuggestionsAdapter<DictIndonesia, SearchSugest.SugestionHlder>  {
    List<DictIndonesia> listSugest;

    public SearchSugest(LayoutInflater inflater) {
        super(inflater);
    }

    @NonNull
    @Override
    public SugestionHlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.sugestion_search, parent, false);
        return new SugestionHlder(view);
    }


    @Override
    public void onBindSuggestionHolder(DictIndonesia dictIndonesia, SugestionHlder sugestionHlder, int i) {
        sugestionHlder.title.setText(dictIndonesia.getKata());
        sugestionHlder.penjelasan.setText(dictIndonesia.getPenjelasn());
    }



    @Override
    public int getSingleViewHeight() {
        return 60;
    }



    public void addSugestions(DictIndonesia r) {
        listSugest.add(r);

    }

    public void setSugestions(List<DictIndonesia> sugestions) {
        this.listSugest = sugestions;
        listSugest = new ArrayList<>(sugestions);
    }

    public void clearSugestions() {
        this.listSugest.clear();
    }

    public void deleteSugestion(int position, DictIndonesia r) {
        this.listSugest.remove(position);
    }

    public List<DictIndonesia> getSugestion() {
        return listSugest;
    }


    static class SugestionHlder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView penjelasan;


        public SugestionHlder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_sugestion);
            penjelasan = itemView.findViewById(R.id.penjelasan_sugestion);
        }
    }


}
