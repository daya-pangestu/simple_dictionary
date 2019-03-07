package com.daya.jojoman;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.daya.jojoman.repo.RecyclerViewModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.daya.jojoman.MainActivity.FROM_DASHBOARD;


public class Fdashboard extends Fragment {
    RecyclerViewModel recyclerViewModel;

    public Fdashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fdashboard, container, false);
        setHasOptionsMenu(true);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFDashboard = view.findViewById(R.id.rv_global);
        FastScroller fastScroller = view.findViewById(R.id.fast_scroller_global);
        //recyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);

        KataINDAdapter kataINDAdapter = new KataINDAdapter(new KataINDAdapter.OnItemClickListener() {
            @Override
            public void itemclicked(int position) {
            }
        }, FROM_DASHBOARD);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvFDashboard.setLayoutManager(layoutManager);
        rvFDashboard.setHasFixedSize(true);


        KataViewModel kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);
        rvFDashboard.setAdapter(kataINDAdapter);

        kataViewModel.getAllKata().observe(getActivity(), kataINDAdapter::setDict);//butuh paging


        fastScroller.setSectionIndexer(kataINDAdapter);

        fastScroller.attachRecyclerView(rvFDashboard);
    }


    public List<DictIndonesia> dummylist() {
        List<DictIndonesia> dictIndonesiaList = new ArrayList<>();

        for (int q = 0; q < 10; q++) {
            dictIndonesiaList.add(new DictIndonesia("asertg","wertgyfd"));
        }

        return dictIndonesiaList;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.delete_history).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }


}
