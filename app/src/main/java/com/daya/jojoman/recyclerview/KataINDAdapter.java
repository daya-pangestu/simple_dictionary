package com.daya.jojoman.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.daya.jojoman.KataViewModel;
import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.DictIndonesia;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class KataINDAdapter extends RecyclerView.Adapter <KataINDAdapter.KataHolder> implements  Filterable, FastScroller.SectionIndexer {
    private List<DictIndonesia> listKamus;
    private List<DictIndonesia> listFull;
    private OnItemClickListener onItemClickListener;

    private Context context;

    public KataINDAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public KataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        this.context = parent.getContext();

        KataHolder kataHolder = new KataHolder(itemView,onItemClickListener);
        return kataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KataHolder holder, int position) {
        DictIndonesia current = listKamus.get(position);

        holder.kata.setText(current.getKata());
        holder.penjelasan.setText(current.getPenjelasn());

    }

    @Override
    public int getItemCount() {

        //size dari yang didapat di list
        if (listKamus != null){
            return listKamus.size();
        } else return 0;
    }

    public void setDict(List<DictIndonesia> dict) {
        this.listKamus = dict;
        listFull = new ArrayList<>(dict);

    }

    private String getItem(int position) {
        return listKamus.get(position).getKata();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(getItem(position).charAt(0));
    }

    //buat filter

    @Override
    public Filter getFilter() {
        return filterListe;
    }

    private Filter filterListe = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DictIndonesia> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listFull);

            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (DictIndonesia item : listFull) {
                    if (item.getKata().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listKamus.clear();
            listKamus.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };





    class KataHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView kata, penjelasan;
        private KataViewModel kataViewModel;
        private OnItemClickListener onItemClickListener;

        KataHolder(View view, OnItemClickListener onItemClickListener) {

            super(view);
            kata = view.findViewById(R.id.recycler_kata);
            penjelasan = view.findViewById(R.id.recycler_penjelasan);
            kataViewModel = ViewModelProviders.of((FragmentActivity) context).get(KataViewModel.class);
            this.onItemClickListener = onItemClickListener;

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            DictIndonesia pass = new DictIndonesia(listFull.get(getAdapterPosition()).getKata(), listFull.get(getAdapterPosition()).getPenjelasn());

            kataViewModel.setSendToDetail(pass);
            NavController navigation = Navigation.findNavController(v);
            navigation.navigate(R.id.action_navigation_home_to_fDetail_ragment2);

            onItemClickListener.itemclicked(getAdapterPosition());

        }
    }

    public interface OnItemClickListener {
    void itemclicked(int position);
    }












}