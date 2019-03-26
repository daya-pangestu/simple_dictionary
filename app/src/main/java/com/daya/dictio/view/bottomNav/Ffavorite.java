package com.daya.dictio.view.bottomNav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.view.layout_thing.BottomNavigationBehavior;
import com.daya.dictio.view.recyclerview_adapter.FavoritAdapter;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

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
    //disini butuh gimana caranya supaya bottomnavigation keliatan kalo semua favorite didelete manual
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    FavoritAdapter adapter;
    BottomNavigationView bottomNavigationView;
    private Unbinder unbinder;
    private FavoriteViewModel favoriteViewModel;

    public Ffavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ffavorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite");
        setHasOptionsMenu(true);

        adapter = new FavoritAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(adapter);


        favoriteViewModel.getList().observe(this, favoriteJoinDicts -> adapter.setDict(favoriteJoinDicts));
        fastScrollerGlobal.setSectionIndexer(adapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        favoriteViewModel.getTotalFavorite().observe(this, integers -> {
            if (integers == 0) {
                new BottomNavigationBehavior().showBottomNavigationView(bottomNavigationView);
            }
        });

        return view;

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
