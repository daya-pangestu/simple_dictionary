package com.daya.jojoman.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SearchSugest extends SuggestionsAdapter<DictIndonesia, SearchSugest.SugestionHlder> implements Filterable {


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
        sugestionHlder.penjelasan.setText(dictIndonesia.getKata());
        sugestionHlder.penjelasan.setText(dictIndonesia.getPenjelasn());

    }

    @Override
    public int getSingleViewHeight() {
        return 60;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString();
                if(term.isEmpty())
                    suggestions = suggestions_clone;
                else {
                    suggestions = new ArrayList<>();
                    for (DictIndonesia item: suggestions_clone)
                        if(item.getKata().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item);
                }
                results.values = suggestions;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<DictIndonesia>) results.values;
                notifyDataSetChanged();
            }
        };
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
