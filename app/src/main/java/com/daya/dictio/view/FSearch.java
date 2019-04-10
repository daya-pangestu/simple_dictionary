package com.daya.dictio.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.SENDER;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;
import com.daya.dictio.viewmodel.WordViewModel;
import com.l4digital.fastscroll.FastScroller;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;
import com.paulrybitskyi.persistentsearchview.adapters.model.SuggestionItem;
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchConfirmedListener;
import com.paulrybitskyi.persistentsearchview.listeners.OnSearchQueryChangeListener;
import com.paulrybitskyi.persistentsearchview.listeners.OnSuggestionChangeListener;
import com.paulrybitskyi.persistentsearchview.utils.SuggestionCreationUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//TODO oncinfirmed search masih ngelag sangad
public class FSearch extends Fragment {
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    @BindView(R.id.persistentSearchView)
    PersistentSearchView searchView;

    @BindView(R.id.list_empty)
    ImageView listEmpty;
    @BindView(R.id.rv_root)
    RelativeLayout rvRoot;
    private final OnSearchQueryChangeListener onSearchQueryChangeListener = new OnSearchQueryChangeListener() {
        @Override
        public void onSearchQueryChanged(PersistentSearchView searchView, String oldQuery, String newQuery) {
            wordViewModel.getSearch(newQuery).observe(FSearch.this, FSearch.this::setSuggestion);
        }
    };

    private WordViewModel wordViewModel;
    @BindView(R.id.text_empty)
    TextView textEmpty;
    private Unbinder unbinder;

    public FSearch() {
        // Required empty public constructor
    }

    private WordIndAdapterPaged wordIndAdapter;
    private final OnSearchConfirmedListener onSearchConfirmedListener = (searchView, query) -> {
        startSearch(query);
        searchView.collapse(true);
    };
    private final OnSuggestionChangeListener onSuggestionChangeListener = new OnSuggestionChangeListener() {
        @Override
        public void onSuggestionPicked(SuggestionItem suggestion) {
            String query = suggestion.getItemModel().getText();
            wordViewModel.getSearch(query).observe(FSearch.this, (List<DictIndonesia> searchModelFts) ->
                    setSuggestion(searchModelFts));
            startSearch(query);
        }
        @Override
        public void onSuggestionRemoved(SuggestionItem suggestion) {
        }
    };

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fsearch, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        listEmpty.setVisibility(View.VISIBLE);
        rvRoot.setVisibility(View.GONE);

        //recyclerview
        wordIndAdapter = new WordIndAdapterPaged(SENDER.SEARCH);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(wordIndAdapter);


        searchView.setOnLeftBtnClickListener(v -> getActivity().onBackPressed());
        searchView.hideRightButton();

        searchView.setOnSearchConfirmedListener(onSearchConfirmedListener);
        searchView.setOnSearchQueryChangeListener(onSearchQueryChangeListener);
        searchView.setOnSuggestionChangeListener(onSuggestionChangeListener);

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
        listEmpty.setVisibility(View.GONE);
        textEmpty.setVisibility(View.GONE);

        rvGlobal.setAlpha(0f);

        rvRoot.setVisibility(View.VISIBLE);

        fastScrollerGlobal.setSectionIndexer(wordIndAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        searchView.showProgressBar();
        Runnable runnable = () -> {
            if (searchView != null) {
                searchView.hideProgressBar(false);
                searchView.showLeftButton();
                //wordIndAdapter.submitList(indonesiaList);
                wordViewModel.getSearchPaged(query).observe(FSearch.this, c -> wordIndAdapter.submitList(c));

                rvGlobal.animate()
                        .alpha(1f)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(300L)
                        .start();

            }


        };

        new Handler().postDelayed(runnable, 1000L);
        searchView.hideLeftButton(false);
        searchView.showProgressBar();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showHideToolbar(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).showHideToolbar(true);

    }


}
