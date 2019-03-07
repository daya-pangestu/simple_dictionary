package com.daya.jojoman;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;
import static com.daya.jojoman.MainActivity.FROM_SEARCH;


/**
 * A simple {@link Fragment} subclass.
 */
public class FSearch_layout extends Fragment {

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    private SearchView searchBar;
    private KataViewModel model;
    private Unbinder unbinder;

    public FSearch_layout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fsearch_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        model = ViewModelProviders.of(this).get(KataViewModel.class);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        Log.i(TAG, "onCreateOptionsMenu: menu called within fragment");

        searchBar = (SearchView) menu.findItem(R.id.search_sactivity).getActionView();
        searchBar.setIconified(true);
        searchBar.setFocusable(false);
        searchBar.clearFocus();

        KataINDAdapter kataINDAdapter = new KataINDAdapter(position -> {
        }, FROM_SEARCH);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(kataINDAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        Log.i(TAG, "onCreateOptionsMenu: search is openend " + searchBar.isIconified());

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextChange: " + query);
                model.getSearch(query).observe(getViewLifecycleOwner(), kataINDAdapter::setDict);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
/*
                Log.i(TAG, "onQueryTextChange: " + newText);
                model.getSearch(newText).observe(SearchActivity.this, new Observer<List<DictIndonesia>>() {
                    @Override
                    public void onChanged(List<DictIndonesia> dictIndonesias) {
                        if (dictIndonesias != null) {

                        Log.i(TAG, "onChanged: " + dictIndonesias.get(0).getKata() + dictIndonesias.get(0).getPenjelasn());
                        }

                    }
                });*/
                return false;
            }
        });

        fastScrollerGlobal.setSectionIndexer(kataINDAdapter);

        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        searchBar.onActionViewExpanded();

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        unbinder.unbind();
    }
}
