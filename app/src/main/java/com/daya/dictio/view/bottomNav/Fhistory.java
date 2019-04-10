package com.daya.dictio.view.bottomNav;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.view.recyclerview_adapter.HistoryAdapter;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class Fhistory extends Fragment {//perlu relasi

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    private Unbinder unbinder;
    private HistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;


    public Fhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhistory, container, false);
        unbinder = ButterKnife.bind(this, view);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getResources().getString(R.string.history));
        setHasOptionsMenu(true);

        historyAdapter = new HistoryAdapter();

        //recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(historyAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        historyViewModel.getList().observe(this, historyJoinDicts -> historyAdapter.setHistory(historyJoinDicts));

        fastScrollerGlobal.setSectionIndexer(historyAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);


        return view;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu_toolbar:
                historyViewModel.deleteHistory();
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_history_pop);
                Snackbar.make(Objects.requireNonNull(getView()), getString(R.string.return_to_dashboard), Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), v -> {
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
