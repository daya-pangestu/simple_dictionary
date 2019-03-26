package com.daya.dictio.view.bottomNav;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.model.SENDER;
import com.daya.dictio.view.layout_thing.BottomNavigationBehavior;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapter;
import com.daya.dictio.viewmodel.WordViewModel;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Fdashboard extends Fragment {


    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    private Unbinder unbinder;
    private WordIndAdapter wordIndAdapterPaged;

    public Fdashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fdashboard, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
        new BottomNavigationBehavior().animateBarVisibility(v, true);

        //viewmodel
        WordViewModel wordViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        //recyclerview
        wordIndAdapterPaged = new WordIndAdapter(SENDER.DASHBOARD);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(wordIndAdapterPaged);
        wordViewModel.getAllWordPaged().observe(this, dictIndonesias ->
                wordIndAdapterPaged.setDict(dictIndonesias));
        //fastscroll
        fastScrollerGlobal.setSectionIndexer(wordIndAdapterPaged);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);
        return v;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.delete_menu_toolbar).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
