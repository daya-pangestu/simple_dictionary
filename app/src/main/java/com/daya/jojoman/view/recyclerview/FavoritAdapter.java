package com.daya.jojoman.view.recyclerview;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.jojoman.R;
import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.model.FavoritModel;
import com.daya.jojoman.model.HistoryModel;
import com.daya.jojoman.view.layout_thing.OnItemClickListener;
import com.daya.jojoman.viewmodel.FavoriteViewModel;
import com.daya.jojoman.viewmodel.HistoryViewModel;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.KataHolderFavorite> implements FastScroller.SectionIndexer {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public int jumlahSisaFavorite;
    private OnItemClickListener onItemClickListener;
    private List<FavoritModel> listKamus;
    private Context context;

    public FavoritAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public KataHolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_favorite, parent, false);
        this.context = parent.getContext();
        return new KataHolderFavorite(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull KataHolderFavorite holder, int position) {
        FavoritModel current = listKamus.get(position);
        viewBinderHelper.bind(holder.swipperFavorite, String.valueOf(current.getIdFavorit()));
        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        if (listKamus != null) {
            Log.i(TAG, "getItemCount: " + listKamus.size());
            return listKamus.size();
        } else return 0;
    }

    public void setDict(List<FavoritModel> dict) {
        this.listKamus = dict;
        notifyDataSetChanged();
    }

    public void clearData() {
        listKamus.clear();
        notifyDataSetChanged();
    }


    private String fapatkanItem(int position) {
        return listKamus.get(position).getKataFavorit();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }

    private void removeItemAt(int index) {
        listKamus.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount());
    }


    public class KataHolderFavorite extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.back_frame_favorite)
        CoordinatorLayout backFrameFavorite;
        @BindView(R.id.recycler_kata_favorite)
        TextView kataFavoriteFront;
        @BindView(R.id.recycler_penjelasan_favorite)
        TextView penjelasanFavoriteFront;
        @BindView(R.id.card_view_rcycler_favorite)
        CoordinatorLayout frontFrameFavorite;
        @BindView(R.id.swipper_favorite)
        SwipeRevealLayout swipperFavorite;

        private KataViewModel kataViewModel;
        private HistoryViewModel historyViewModel;
        private FavoriteViewModel favoriteViewModel;
        private OnItemClickListener onItemClickListener;

        KataHolderFavorite(View view, OnItemClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            historyViewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(HistoryViewModel.class);


            kataViewModel = ViewModelProviders.of((FragmentActivity) context).get(KataViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);

            this.onItemClickListener = onItemClickListener;
            frontFrameFavorite.setOnClickListener(this);

            backFrameFavorite.setOnClickListener(this);
        }


        void bindTo(FavoritModel favoritModel) {
            kataFavoriteFront.setText(Html.fromHtml(favoritModel.getKataFavorit()));
            penjelasanFavoriteFront.setText(Html.fromHtml(favoritModel.getPenjelasan()));
        }


        @Override
        public void onClick(View v) {
            int id = listKamus.get(getAdapterPosition()).getIdFavorit();
            String kata = listKamus.get(getAdapterPosition()).getKataFavorit();
            String penjelasan = listKamus.get(getAdapterPosition()).getPenjelasan();
            switch (v.getId()) {
                case R.id.card_view_rcycler_favorite:
                    kataViewModel.setSendToDetail(new DictIndonesia(kata, penjelasan));
                    historyViewModel.addHistory(new HistoryModel(kata, penjelasan));
                    NavController navigation = Navigation.findNavController(v);
                    navigation.navigate(R.id.action_navigation_fovorite_to_fDetail_ragment);
                    onItemClickListener.itemclicked(getAdapterPosition());
                    break;
                case R.id.back_frame_favorite:
                    removeItemAt(getAdapterPosition());
                    favoriteViewModel.deleteFavoriteAt(new FavoritModel(id, kata, penjelasan));
                    Log.i(TAG, "onClick: sisa listkamus " + listKamus.size());


            }
        }
    }


}
