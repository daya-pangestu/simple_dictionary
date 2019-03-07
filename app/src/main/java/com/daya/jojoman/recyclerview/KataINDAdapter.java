package com.daya.jojoman.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.jojoman.MainActivity;
import com.daya.jojoman.repo.KataViewModel;
import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class KataINDAdapter extends RecyclerView.Adapter<KataINDAdapter.KataHolder> implements  /*Filterable,*/ FastScroller.SectionIndexer {
    static final String TAG = KataINDAdapter.class.getSimpleName();
    public static int kode;

    private List<DictIndonesia> listKamus;

    private OnItemClickListener onItemClickListener;
    private Context context;
    private final ViewBinderHelper viewBinderHelper;

    public KataINDAdapter(@NonNull OnItemClickListener onItemClickListener, int kode) {
        viewBinderHelper = new ViewBinderHelper();
        this.kode = kode;
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

        if (listKamus != null) {
            Log.i(TAG, "getItemCount: ");
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
            DictIndonesia pass = new DictIndonesia(listKamus.get(getAdapterPosition()).getKata(), listKamus.get(getAdapterPosition()).getPenjelasn());
            kataViewModel.setSendToDetail(pass);


            NavController navigation = Navigation.findNavController(v);
            switch (KataINDAdapter.kode) {
                case MainActivity.FROM_DASHBOARD:

                    navigation.navigate(R.id.action_navigation_home_to_fDetail_ragment2);
                    break;
                case MainActivity.FROM_SEARCH:
                    navigation.navigate(R.id.action_FSearch_layout_to_fDetail_ragment);
                    break;
                case MainActivity.FROM_HISTORY:
                    navigation.navigate(R.id.action_navigation_history_to_fDetail_ragment);
                default:
                    break;

            }

            onItemClickListener.itemclicked(getAdapterPosition());
        }
    }
}