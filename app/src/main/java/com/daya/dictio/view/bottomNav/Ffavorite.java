package com.daya.dictio.view.bottomNav;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daya.dictio.R;
import com.daya.dictio.dictio;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.model.join.HistoryJoinDict;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.view.layout_thing.OnItemPopulated;
import com.daya.dictio.view.recyclerview_adapter.FavoritAdapter;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.SharedDataViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class Ffavorite extends Fragment  {
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller mFastScrollerGlobal;
    @BindView(R.id.list_empty_f_favorite)
    ImageView listEmptyFFavorite;
    @BindView(R.id.text_empty_f_favorite)
    TextView textEmptyFFavorite;


    private FavoritAdapter mFavoritAdapter;
    private Unbinder unbinder;
    private FavoriteViewModel mFavoriteViewModel;
    private WordViewModel mWordViewModel;
    private HistoryViewModel mHistoryViewModel;
    private SharedDataViewModel mSharedDataViewModel;


    public Ffavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ffavorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        //viewmodel
        mFavoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        mSharedDataViewModel = ViewModelProviders.of(this).get(SharedDataViewModel.class);

        //toolbar
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(getString(R.string.favorite));
        setHasOptionsMenu(true);

        //recyclerview
        mFavoritAdapter = new FavoritAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(mFavoritAdapter);
        mFavoritAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void dashboardClicked(View view, DictIndonesia dictIndonesia, int position) {
                //do nothing
            }

            @Override
            public void historyClicked(View view, HistoryJoinDict historyJoinDict, int position) {
                //do nothing
            }

            @Override
            public void favoriteClicked(View view, FavoriteJoinDict favoriteJoinDict, int position) {
                switch (view.getId()) {
                    case R.id.card_view_rcycler_favorite:
                        mFavoriteViewModel.isFavoritExists(favoriteJoinDict.getIdOwner());
                        mSharedDataViewModel.setDictIndonesia(new DictIndonesia(favoriteJoinDict.getId(), favoriteJoinDict.getWord(), favoriteJoinDict.getMeaning()));
                        mHistoryViewModel.addHistory(new HistoryModel(favoriteJoinDict.getId()));
                        NavController navigation = Navigation.findNavController(view);
                        navigation.navigate(R.id.action_navigation_fovorite_to_fDetail_ragment);
                        break;

                    default:
                        break;

                }
            }
        });



        //ambil list favorit dari db
        mFavoriteViewModel.getList().observe(this, favoriteJoinDicts -> mFavoritAdapter.setDict(favoriteJoinDicts));


        //fastscroll
        mFastScrollerGlobal.setSectionIndexer(mFavoritAdapter);
        mFastScrollerGlobal.attachRecyclerView(rvGlobal);

        return view;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_menu_toolbar) {
            mFavoriteViewModel.deleteAllFavorite();
            mFavoritAdapter.clearData();
            Snackbar.make(Objects.requireNonNull(getView()), "deleted", Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), v -> {
            }).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }



}
