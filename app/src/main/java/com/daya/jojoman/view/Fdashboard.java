package com.daya.jojoman.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.daya.jojoman.R;
import com.daya.jojoman.view.layout_thing.BottomNavigationBehavior;
import com.daya.jojoman.view.recyclerview.KataINDAdapter;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.daya.jojoman.view.MainActivity.FROM_DASHBOARD;


public class Fdashboard extends Fragment {


    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    private BottomNavigationView bottomNavigationView;
    private Unbinder unbinder;
    private KataViewModel kataViewModel;

    public Fdashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fdashboard, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
        unbinder = ButterKnife.bind(this, v);


        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        new BottomNavigationBehavior().animateBarVisibility(v, true);

        kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        KataINDAdapter kataINDAdapter = new KataINDAdapter(position -> {
        }, FROM_DASHBOARD);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(kataINDAdapter);

        kataViewModel.getAllKata().observe(getActivity(), kataINDAdapter::setDict);//butuh paging

        fastScrollerGlobal.setSectionIndexer(kataINDAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);
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
