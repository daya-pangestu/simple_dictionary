package com.daya.dictio.view.bottomNav;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.view.adapter.WordIndAdapter;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

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



/**
 * A simple {@link Fragment} subclass.
 */
public class Fhistory extends Fragment {//perlu relasi
    private static final String TAG = Fhistory.class.getSimpleName();

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    private Unbinder unbinder;


    private HistoryViewModel historyViewModel;
    private WordIndAdapter wordIndAdapter;
    BottomNavigationView bottomNavigationView;



    public Fhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("History");
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<DictIndonesia> listHistory = new ArrayList<>();
        wordIndAdapter = new WordIndAdapter(position -> {
        }, WordIndAdapter.SENDER.HISTORY);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(wordIndAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        historyViewModel.getList().observe(this, historyModels -> {
            if (historyModels.size() != 0) {
                for (HistoryModel s : historyModels) {
                    String d = s.getKataHistory();
                    String q = s.getPenjelasanHistory();

                    Log.i(TAG, "onChanged: " + d + " " + q);
                    listHistory.add(new DictIndonesia(d, q));

                }
            } else Log.i(TAG, "onChanged: history empty");
            wordIndAdapter.setDict(listHistory);

        });

        fastScrollerGlobal.setSectionIndexer(wordIndAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu_toolbar:
                historyViewModel.deleteHistory();
                wordIndAdapter.clearData();
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_history_pop);
                Snackbar.make(getView(), "return to home", Snackbar.LENGTH_LONG).setAction("OK", v -> {
                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
