package com.daya.dictio.view.bottomNav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.view.recyclerview_adapter.FavoritAdapter;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Ffavorite extends Fragment {
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    private FavoritAdapter adapter;
    private Unbinder unbinder;
    private FavoriteViewModel favoriteViewModel;

    public Ffavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ffavorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(getString(R.string.favorite));
        setHasOptionsMenu(true);

        adapter = new FavoritAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(adapter);


        favoriteViewModel.getList().observe(this, favoriteJoinDicts -> adapter.setDict(favoriteJoinDicts));
        fastScrollerGlobal.setSectionIndexer(adapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);



        return view;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu_toolbar:

                favoriteViewModel.deleteAllFavorite();
                adapter.clearData();
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_fovorite_pop);
                Snackbar.make(Objects.requireNonNull(getView()), getString(R.string.return_to_dashboard), Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), v -> {
                }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


}
