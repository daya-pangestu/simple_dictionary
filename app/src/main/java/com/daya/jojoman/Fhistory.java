package com.daya.jojoman;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.db.indo.model.relation.History;
import com.daya.jojoman.repo.HistoryViewModel;
import com.daya.jojoman.repo.RecyclerViewModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;
import static com.daya.jojoman.MainActivity.FORM_HISTORY;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fhistory extends Fragment {


    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    Unbinder unbinder;
    RecyclerViewModel recyclerViewModel;
    HistoryViewModel historyViewModel;

    public Fhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<DictIndonesia> indonesiaList;


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        recyclerViewModel.setAdapter(FORM_HISTORY);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
