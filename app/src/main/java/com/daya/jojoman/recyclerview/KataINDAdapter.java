package com.daya.jojoman.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.jojoman.MainActivity;
import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.repo.FavoriteViewModel;
import com.daya.jojoman.repo.HistoryViewModel;
import com.daya.jojoman.repo.KataViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class KataINDAdapter extends RecyclerView.Adapter<KataINDAdapter.KataHolder> implements FastScroller.SectionIndexer {
    private static final String TAG = KataINDAdapter.class.getSimpleName();
    private static int kode;
    private final OnItemClickListener onItemClickListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<DictIndonesia> listKamus;
    private Context context;

    public KataINDAdapter(@NonNull OnItemClickListener onItemClickListener, int kode) {
        ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        KataINDAdapter.kode = kode;
        this.onItemClickListener = onItemClickListener;
        viewBinderHelper.setOpenOnlyOne(true);
    }


    @NonNull
    @Override
    public KataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        this.context = parent.getContext();
        return new KataHolder(itemView, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull KataHolder holder, int position) {
        DictIndonesia current = listKamus.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(current.getIdIndo()));
        if (current != null) {
            holder.bindTo(current);
        } else {
            holder.clear();
        }
    }

    @Override
    public int getItemCount() {

        if (listKamus != null) {
            Log.i(TAG, "getItemCount: " + listKamus.size());
            return listKamus.size();
        } else return 0;
    }

    public void setDict(List<DictIndonesia> dict) {
        this.listKamus = dict;
        notifyDataSetChanged();

    }

    private String fapatkanItem(int position) {
        return listKamus.get(position).getKata();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }


    public interface OnItemClickListener {
        void itemclicked(int position);
    }

    public class KataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView kata;
        private final TextView penjelasan;
        private final KataViewModel kataViewModel;
        private final OnItemClickListener onItemClickListener;
        private final SwipeRevealLayout swipeRevealLayout;
        private final View foregroundView;
        private View backgroundView;
        private HistoryViewModel historyViewModel;
        private FavoriteViewModel favoriteViewModel;

        KataHolder(View view, OnItemClickListener onItemClickListener) {

            super(view);
            historyViewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(HistoryViewModel.class); //apakah benar?
            kata = view.findViewById(R.id.recycler_kata);
            penjelasan = view.findViewById(R.id.recycler_penjelasan);
            swipeRevealLayout = view.findViewById(R.id.swipper);
            foregroundView = view.findViewById(R.id.card_view_rcycler);
            backgroundView = view.findViewById(R.id.back_frame);

            kataViewModel = ViewModelProviders.of((FragmentActivity) context).get(KataViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);

            this.onItemClickListener = onItemClickListener;
            foregroundView.setOnClickListener(this);

            backgroundView.setOnClickListener(this);


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

        void navigationFronLayout(View v, String kata, String penjelasan) {
            NavController navigation = Navigation.findNavController(v);
            switch (KataINDAdapter.kode) {
                case MainActivity.FROM_DASHBOARD:
                    historyViewModel.addHistory(new HistoryModel(kata, penjelasan));
                    navigation.navigate(R.id.action_navigation_home_to_fDetail_ragment2);
                    break;
                case MainActivity.FROM_SEARCH:
                    historyViewModel.addHistory(new HistoryModel(kata, penjelasan));
                    navigation.navigate(R.id.action_FSearch_layout_to_fDetail_ragment);
                    break;
                case MainActivity.FROM_HISTORY:
                    navigation.navigate(R.id.action_navigation_history_to_fDetail_ragment);
                default:
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            String kata = listKamus.get(getAdapterPosition()).getKata();
            String penjelasan = listKamus.get(getAdapterPosition()).getPenjelasn();

            kataViewModel.setSendToDetail(new DictIndonesia(kata, penjelasan));

            switch (v.getId()) {
                case R.id.card_view_rcycler:
                    navigationFronLayout(v, kata, penjelasan);
                    break;

                case R.id.back_frame:
                    Snackbar snackbar = Snackbar.make(v.getRootView().getRootView().findViewById(R.id.container), kata + " added to favorite", Snackbar.LENGTH_LONG).setAction("Undo", v1 -> {
                    });

                    snackbar.show();
                    break;
                default:
                    break;

            }


            onItemClickListener.itemclicked(getAdapterPosition());
        }


    }


}
