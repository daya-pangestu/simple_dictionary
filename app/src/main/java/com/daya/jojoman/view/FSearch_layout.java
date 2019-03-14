package com.daya.jojoman.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.R;
import com.daya.jojoman.view.recyclerview.KataINDAdapter;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;
import static com.daya.jojoman.view.MainActivity.FROM_SEARCH;


public class FSearch_layout extends Fragment {

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    private KataViewModel model;
    private Unbinder unbinder;
    private BottomNavigationView bottomNavigationView;


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
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.delete_menu_toolbar).setVisible(false);
        Log.i(TAG, "onCreateOptionsMenu: menu called within fragment");


        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavHostFragment.findNavController(this).navigateUp();
                return true;
            case R.id.searchBar_bar:
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }


    public void fixedSearch() {
        SearchView searchBar = null;// = (SearchView) menu.findItem(R.id.search_sactivity).getActionView();
        searchBar.setMaxWidth(Integer.MAX_VALUE);

        searchBar.setIconified(false);
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

                return false;
            }
        });

        fastScrollerGlobal.setSectionIndexer(kataINDAdapter);

        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        searchBar.onActionViewExpanded();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
