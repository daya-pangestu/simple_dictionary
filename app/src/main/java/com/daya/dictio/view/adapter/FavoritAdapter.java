package com.daya.dictio.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
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
import timber.log.Timber;


public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.WordolderFavorite> implements FastScroller.SectionIndexer {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private OnItemClickListener onItemClickListener;
    private List<FavoritModel> listKamus;
    private Context context;

    public FavoritAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WordolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_favorite, parent, false);
        this.context = parent.getContext();
        return new WordolderFavorite(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordolderFavorite holder, int position) {
        FavoritModel current = listKamus.get(position);
        viewBinderHelper.bind(holder.swipperFavorite, String.valueOf(current.getIdFavorit()));
        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return (listKamus != null) ? listKamus.size() : 0;

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
        return listKamus.get(position).getWordFavorit();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }

    private void removeItemAt(int index) {
        if (index != -1 && index < listKamus.size()) {
            listKamus.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }


    public class WordolderFavorite extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.back_frame_favo)
        RelativeLayout backFrameFavorite;

        @BindView(R.id.switch_icon_favo)
        SwitchIconView iconDelete;

        @BindView(R.id.recycler_kata_favorite)
        TextView kataFavoriteFront;
        @BindView(R.id.recycler_penjelasan_favorite)
        TextView penjelasanFavoriteFront;
        @BindView(R.id.card_view_rcycler_favorite)
        CoordinatorLayout frontFrameFavorite;
        @BindView(R.id.swipper_favorite)
        SwipeRevealLayout swipperFavorite;


        private WordViewModel wordViewModel;
        private HistoryViewModel historyViewModel;
        private FavoriteViewModel favoriteViewModel;
        private OnItemClickListener onItemClickListener;

        WordolderFavorite(View view, OnItemClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            historyViewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(HistoryViewModel.class);


            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);

            this.onItemClickListener = onItemClickListener;
            frontFrameFavorite.setOnClickListener(this);

            backFrameFavorite.setOnClickListener(this);
        }


        void bindTo(FavoritModel favoritModel) {
            kataFavoriteFront.setText(Html.fromHtml(favoritModel.getWordFavorit()));
            penjelasanFavoriteFront.setText(Html.fromHtml(favoritModel.getMeaning()));
        }

        @Override
        public void onClick(View v) {
            int id = listKamus.get(getAdapterPosition()).getIdFavorit();
            String wordFavorit = listKamus.get(getAdapterPosition()).getWordFavorit();
            String meaning = listKamus.get(getAdapterPosition()).getMeaning();
            switch (v.getId()) {
                case R.id.card_view_rcycler_favorite:
                    wordViewModel.setSendToDetail(new DictIndonesia(wordFavorit, meaning));
                    historyViewModel.addHistory(new HistoryModel(wordFavorit, meaning));
                    NavController navigation = Navigation.findNavController(v);
                    navigation.navigate(R.id.action_navigation_fovorite_to_fDetail_ragment);
                    onItemClickListener.itemclicked(getAdapterPosition());
                    break;
                case R.id.back_frame_favo:
                    iconDelete.setIconEnabled(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // hold item before removed for 0.5 sec
                        removeItemAt(getAdapterPosition());
                        favoriteViewModel.deleteFavoriteAt(new FavoritModel(id, wordFavorit, meaning));
                        Timber.i("onClick: sisa listkamus %s", listKamus.size());
                    }, 300);

            }
        }
    }


}
