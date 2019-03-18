package com.daya.dictio.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.view.adapter.WordIndAdapter;
import com.daya.dictio.viewmodel.WordViewModel;
import com.l4digital.fastscroll.FastScroller;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem;
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener;
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FSearch extends Fragment {
    public static final String TAG = FSearch.class.getSimpleName();
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    @BindView(R.id.persistentSearchView)
    PersistentSearchView searchView;

    private WordViewModel wordViewModel;
    private Unbinder unbinder;
    private WordIndAdapter wordIndAdapter;

    public FSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fsearch_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        searchView.setSuggestionsDisabled(false);
        searchView.setOnLeftBtnClickListener(v -> getActivity().onBackPressed());
        searchView.setOnRightBtnClickListener(v -> Toast.makeText(getContext(), "right button pressed", Toast.LENGTH_SHORT).show());
        searchView.setOnSearchQueryChangeListener((searchView, oldQuery, newQuery) -> wordViewModel.getSearch(newQuery).observe(FSearch.this, FSearch.this::setSuggestion));
        searchView.setOnSuggestionChangeListener(new OnSuggestionChangeListener() {
            @Override
            public void onSuggestionPicked(SuggestionItem suggestion) {
                String query = suggestion.getItemModel().getText();

                wordViewModel.getSearch(query).observe(FSearch.this, (List<DictIndonesia> searchModelFts) -> {
                    setSuggestion(searchModelFts);
                });

                startSearch(query);
            }

            @Override
            public void onSuggestionRemoved(SuggestionItem suggestion) {

            }
        });
        searchView.setOnSearchConfirmedListener((searchView, query) -> {
            searchView.collapse();
            startSearch(query);
        });


        return view;
    }


    private void setSuggestion(List<DictIndonesia> query) throws IllegalStateException {
        List<String> queryString = new ArrayList<>();

        for (DictIndonesia uery : query) {
            queryString.add(uery.getWord());
        }

        List<SuggestionItem> suggestions = SuggestionCreationUtil.asRegularSearchSuggestions(queryString);

        searchView.setSuggestions(suggestions, false);
    }

    private void startSearch(String query) { //call inside search
        wordIndAdapter = new WordIndAdapter(position -> {
        }, WordIndAdapter.SENDER.SEARCH);
        wordViewModel.getSearch(query).observe(getActivity(), (List<DictIndonesia> dict) -> {
            wordIndAdapter.setDict(dict);
            Log.i(TAG, "startSearch: isi dict " + dict.get(0).getWord());

        });

        searchView.showProgressBar();
        Runnable runnable = () -> {
            searchView.hideProgressBar();
            searchView.showLeftButton();
        };


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(wordIndAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fastScrollerGlobal.setSectionIndexer(wordIndAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        searchView.hideProgressBar();

        rvGlobal.animate()
                .alpha(1f)
                .setInterpolator(new LinearInterpolator())
                .setDuration(300L)
                .start();

        new Handler().postDelayed(runnable, 1000L);
        searchView.hideLeftButton(false);
        searchView.showProgressBar();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


}
