package com.daya.dictio.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daya.dictio.R;
import com.daya.dictio.dictio;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;
import com.lapism.searchview.Search;
import com.lapism.searchview.widget.SearchView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

//TODO oncinfirmed search masih ngelag sangad
public class FSearch extends Fragment {
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    @BindView(R.id.list_empty)
    ImageView listEmpty;
    @BindView(R.id.rv_root)
    RelativeLayout rvRoot;
    @BindView(R.id.searchView_fsearch)
    SearchView searchView;
    @BindView(R.id.progress_f_search)
    ProgressBar progressFSearch;

    private FavoriteViewModel mFavoritViewModel;
    private WordViewModel mWordViewModel;
    private HistoryViewModel mHistoryViewModel;
    @BindView(R.id.text_empty)
    TextView textEmpty;
    private Unbinder unbinder;

    public FSearch() {
        // Required empty public constructor
    }

    private WordIndAdapterPaged wordIndAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fsearch, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        rvRoot.setVisibility(View.GONE);

        //viewmodel
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mFavoritViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);


        searchView.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                startSearch(query.toString());

                searchView.close();

                return true;
            }

            @Override
            public void onQueryTextChange(CharSequence newText) {

            }
        });
        searchView.setOnLogoClickListener(() -> Navigation.findNavController(view).navigateUp());


        //recyclerview
        wordIndAdapter = new WordIndAdapterPaged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(wordIndAdapter);




        return view;
    }




    private void startSearch(String query) { //call inside search
        progressFSearch.setVisibility(View.VISIBLE);
        listEmpty.setVisibility(View.GONE);
        textEmpty.setVisibility(View.GONE);

        rvGlobal.setAlpha(0f);
        rvRoot.setVisibility(View.VISIBLE);

        fastScrollerGlobal.setSectionIndexer(wordIndAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        Runnable runnable = () -> {


            if (searchView != null) {

                mWordViewModel.getSearchPaged(query).observe(getViewLifecycleOwner(), dictIndonesias -> {
                    wordIndAdapter.submitList(dictIndonesias);
                    Timber.i("startSearch: %s", dictIndonesias.size());
                });

            }
            rvGlobal.animate()
                    .alpha(1f)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(300L)
                    .start();
            progressFSearch.setVisibility(View.GONE);
        };

        new Handler().postDelayed(runnable, 1000L);
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
