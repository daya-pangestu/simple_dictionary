package com.daya.jojoman;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.daya.jojoman.db.indo.DictIndonesia;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.search.GlobalSearch;
import com.l4digital.fastscroll.FastScroller;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fdashboard extends Fragment implements MaterialSearchBar.OnSearchActionListener {

    private KataINDAdapter kataINDAdapter;
    private List<DictIndonesia> listDictID;
    KataViewModel kataViewModel;

    public Fdashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fdashboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFDashboard = view.findViewById(R.id.rv_f_dashboard);
        FastScroller fastScroller = view.findViewById(R.id.fast_scroller_dashboard);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvFDashboard.setLayoutManager(layoutManager);
        rvFDashboard.setHasFixedSize(true);

        KataViewModel kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);

        kataINDAdapter = new KataINDAdapter(position -> {
            Log.i(TAG, "onViewCreated: " + kataViewModel.getSendToDetail().getKata()+" "+kataViewModel.getSendToDetail().getPenjelasn());
        });

                //butuh paging
                kataViewModel.getAllKata().observe(getActivity(), dictIndonesias ->
                        kataINDAdapter.setDict(dictIndonesias));



         rvFDashboard.setAdapter(kataINDAdapter);

        fastScroller.setSectionIndexer(kataINDAdapter);

        fastScroller.attachRecyclerView(rvFDashboard);

        GlobalSearch globalSearch = new GlobalSearch(kataINDAdapter, getActivity());
        globalSearch.activateGlobalsearch();



    }


    public List<DictIndonesia> dummylist() {
        List<DictIndonesia> dictIndonesiaList = new ArrayList<>();

        for (int q = 0; q < 10; q++) {
            dictIndonesiaList.add(new DictIndonesia("asertg","wertgyfd"));
        }

        return dictIndonesiaList;
    }


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
