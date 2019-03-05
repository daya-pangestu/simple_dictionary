package com.daya.jojoman.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.jojoman.repo.KataViewModel;
import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.DictIndonesia;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class KataINDAdapter extends RecyclerView.Adapter<KataINDAdapter.KataHolder> implements  /*Filterable,*/ FastScroller.SectionIndexer {
    private List<DictIndonesia> listKamus;
  /*  private List<DictIndonesia> listFull;*/
    private OnItemClickListener onItemClickListener;

    private Context context;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public KataINDAdapter(@NonNull OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        viewBinderHelper.setOpenOnlyOne(true);
    }


    @NonNull
    @Override
    public KataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        this.context = parent.getContext();

        KataHolder kataHolder = new KataHolder(itemView, onItemClickListener);
        return kataHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull KataHolder holder, int position) {
        DictIndonesia current = listKamus.get(position);

        if (current != null) {
            holder.bindTo(current);
        } else {
            holder.clear();
        }

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
       /* listFull = new ArrayList<>(dict);*/

    }

    protected String fapatkanItem(int position) {
        return listKamus.get(position).getKata();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }


    /*@Override
    public Filter getFilter() {
        return filterListe;
    }*/

   /* private Filter filterListe = new Filter() {
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



    private static DiffUtil.ItemCallback<DictIndonesia> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DictIndonesia>() {


                @Override
                public boolean areItemsTheSame(@NonNull DictIndonesia oldItem, @NonNull DictIndonesia newItem) {
                    return oldItem.getIdIndo()== newItem.getIdIndo();
                }

                @Override
                public boolean areContentsTheSame(@NonNull DictIndonesia oldItem, @NonNull DictIndonesia newItem) {
                    return oldItem.equals(newItem);
                }
            };
*/
    public class KataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView kata, penjelasan;
        private KataViewModel kataViewModel;
        private OnItemClickListener onItemClickListener;
        private SwipeRevealLayout swipeRevealLayout;
        private View foregroundView;
        private ImageButton btnFavRecycler;

        KataHolder(View view, OnItemClickListener onItemClickListener) {

            super(view);

            kata = view.findViewById(R.id.recycler_kata);
            penjelasan = view.findViewById(R.id.recycler_penjelasan);
            swipeRevealLayout = view.findViewById(R.id.swipper);
            kataViewModel = ViewModelProviders.of((FragmentActivity) context).get(KataViewModel.class);
            this.onItemClickListener = onItemClickListener;
            foregroundView = view.findViewById(R.id.card_view_rcycler);
            foregroundView.setOnClickListener(this);
        }

        void bindTo(DictIndonesia dictIndonesia) {
            kata.setText(dictIndonesia.getKata());
            penjelasan.setText(dictIndonesia.getKata());
        }

        void clear() {
            itemView.invalidate();
            kata.invalidate();
            penjelasan.invalidate();
            foregroundView.invalidate();
            swipeRevealLayout.invalidate();
        }

        @Override
        public void onClick(View v) {
           /* DictIndonesia pass = new DictIndonesia(listFull.get(getAdapterPosition()).getKata(), listFull.get(getAdapterPosition()).getPenjelasn());
             kataViewModel.setSendToDetail(pass);*/
            NavController navigation = Navigation.findNavController(v);
            navigation.navigate(R.id.action_navigation_home_to_fDetail_ragment2);

            onItemClickListener.itemclicked(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
    void itemclicked(int position);
    }
}