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
    private HistoryViewModel mHistoryViewModel;
    private HistoryAdapter mHistoryAdapter;


    public Fhistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fhistory, container, false);

        unbinder = ButterKnife.bind(this, view);

        //viewmodel
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        //toolbar
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getResources().getString(R.string.history));
        setHasOptionsMenu(true);


        //recyclerview
        mHistoryAdapter = new HistoryAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(mHistoryAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        mHistoryViewModel.getList().observe(this, historyJoinDicts -> mHistoryAdapter.setHistory(historyJoinDicts));

        fastScrollerGlobal.setSectionIndexer(mHistoryAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);



        return view;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu_toolbar:
                mHistoryViewModel.deleteHistory();
                Snackbar.make(Objects.requireNonNull(getView()), "deleted", Snackbar.LENGTH_LONG).setAction(getString(R.string.ok), v -> {
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
