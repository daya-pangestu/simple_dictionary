package com.daya.dictio.view.recyclerview_adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private List<FavoriteJoinDict> listKamus;
    private Context context;


    @NonNull
    @Override
    public WordolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_favorite, parent, false);
        this.context = parent.getContext();
        viewBinderHelper.setOpenOnlyOne(true);
        return new WordolderFavorite(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordolderFavorite holder, int position) {
        FavoriteJoinDict current = listKamus.get(position);
        viewBinderHelper.bind(holder.swipperFavorite, String.valueOf(current.getId()));
        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return (listKamus != null) ? listKamus.size() : 0;

    }

    public void setDict(List<FavoriteJoinDict> dict) {
        this.listKamus = dict;
        notifyDataSetChanged();
    }

    public void clearData() {
        listKamus.clear();
        notifyDataSetChanged();
    }


    private String fapatkanItem(int position) {
        return listKamus.get(position).getWord();
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

        private final WordViewModel wordViewModel;
        private final HistoryViewModel historyViewModel;
        private final FavoriteViewModel favoriteViewModel;
        @Nullable
        @BindView(R.id.list_empty)
        ImageView listEmpty;
        private FavoriteJoinDict inHolderFav;


        WordolderFavorite(View view) {
            super(view);
            ButterKnife.bind(this, view);

            historyViewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(HistoryViewModel.class);
            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);

            frontFrameFavorite.setOnClickListener(this);
            backFrameFavorite.setOnClickListener(this);

        }


        void bindTo(FavoriteJoinDict favoriteJoinDict) {
            kataFavoriteFront.setText(Html.fromHtml(favoriteJoinDict.getWord()));
            penjelasanFavoriteFront.setText(Html.fromHtml(favoriteJoinDict.getMeaning()));
            inHolderFav = favoriteJoinDict;
        }

        @Override
        public void onClick(View v) {
            int id = inHolderFav.getId();
            int idOwner = inHolderFav.getIdOwner();
            String wordFavorit = inHolderFav.getWord();
            String meaning = inHolderFav.getMeaning();

            switch (v.getId()) {
                case R.id.card_view_rcycler_favorite:

                    wordViewModel.setSendToDetail(new DictIndonesia(id, wordFavorit, meaning));
                    historyViewModel.addHistory(new HistoryModel(id));

                    NavController navigation = Navigation.findNavController(v);
                    navigation.navigate(R.id.action_navigation_fovorite_to_fDetail_ragment);
                    break;
                case R.id.back_frame_favo:
                    iconDelete.setIconEnabled(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // hold item before removed for 0.5 sec
                        removeItemAt(getAdapterPosition());
                        favoriteViewModel.deleteFavoriteAt(new FavoritModel(id, idOwner));
                        Timber.i("onClick: sisa listkamus %s", listKamus.size());
                    }, 300);

            }
        }
    }


}
