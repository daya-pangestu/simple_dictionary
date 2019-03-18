package com.daya.dictio.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener;
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.persistentSearchView)
    PersistentSearchView searchView;

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    WordIndAdapter wordIndAdapter;
    private WordViewModel kataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        kataViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        searchView.setSuggestionsDisabled(false);
        searchView.setOnLeftBtnClickListener(v -> onBackPressed());
        searchView.setOnRightBtnClickListener(v -> Toast.makeText(this, "right button pressed", Toast.LENGTH_SHORT).show());
        searchView.setOnSearchConfirmedListener((PersistentSearchView searchView, String query) -> {
            searchView.collapse();
            startSearch(query);
        });
        searchView.setOnSearchQueryChangeListener((searchView, oldQuery, newQuery) -> {
            kataViewModel.getSearch(newQuery).observe(this, this::setSuggestion);
        });


        searchView.setOnSearchConfirmedListener(new OnSearchConfirmedListener() {
            @Override
            public void onSearchConfirmed(PersistentSearchView searchView, String query) {
                SearchConfirmed(searchView, query);
                searchView.collapse();
            }
        });


    }

    private void SearchConfirmed(PersistentSearchView searchView, String query) {


        kataViewModel.getSearch(query).observe(this, new Observer<List<DictIndonesia>>() {
            @Override
            public void onChanged(List<DictIndonesia> dictIndonesias) {
                wordIndAdapter.setDict(dictIndonesias);//buat adapter berbeda untuk sugesti dan yang diatampilkan
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
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
        kataViewModel.getSearch(query).observe(this, (List<DictIndonesia> dict) -> {
            wordIndAdapter.setDict(dict);

        });


        searchView.showProgressBar();
        Runnable runnable = () -> {
            searchView.hideProgressBar();
            searchView.showLeftButton();
        };


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        rvGlobal.setAdapter(wordIndAdapter);
        rvGlobal.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }

}
