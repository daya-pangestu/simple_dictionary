package com.daya.jojoman.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.R;
import com.daya.jojoman.view.layout_thing.BottomNavigationBehavior;
import com.daya.jojoman.view.recyclerview.FavoritAdapter;
import com.daya.jojoman.viewmodel.FavoriteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    //disini butuh gimana caranya supaya bottomnavigation keliatan kalo semua favorite didelete manual
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    FavoritAdapter adapter;
    BottomNavigationView bottomNavigationView;
    BottomNavigationBehavior b;
    int panjangNavigation;
    private Unbinder unbinder;
    private FavoriteViewModel favoriteViewModel;

    public Ffavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ffavorite, container, false);
        unbinder = ButterKnife.bind(this, v);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite");

        b = new BottomNavigationBehavior();


        setHasOptionsMenu(true);

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FavoritAdapter(position -> {
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(adapter);

        favoriteViewModel.getList().observe(this, dict -> {
            adapter.setDict(dict);

        });

        fastScrollerGlobal.setSectionIndexer(adapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        favoriteViewModel.getJumlahFavorite().observe(this, integers -> {
            if (integers == 0) {
                new BottomNavigationBehavior().showBottomNavigationView(bottomNavigationView);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu_toolbar:

                favoriteViewModel.deleteAllFavorite();
                adapter.clearData();
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_fovorite_pop);
                Snackbar.make(getView(), "return to Dashboard", Snackbar.LENGTH_LONG).setAction("OK", v -> {
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
