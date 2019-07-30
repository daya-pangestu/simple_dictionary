package com.daya.dictio.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
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
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.model.join.HistoryJoinDict;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.SharedDataViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;
import com.squareup.haha.perflib.Main;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;

//TODO oncinfirmed search masih ngelag sangad
public class FSearch extends Fragment {
    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;
    @BindView(R.id.list_empty)
    ImageView listEmpty;

/*    @BindView(R.id.rv_rot)
    RelativeLayout rvRoot;*/
    @BindView(R.id.progress_f_search)
    ProgressBar progressFSearch;

    private FavoriteViewModel mFavoritViewModel;
    private WordViewModel mWordViewModel;
    private HistoryViewModel mHistoryViewModel;
    private SharedDataViewModel mSharedDataViewModel;
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

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.search);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        rvGlobal.setVisibility(View.GONE);
        progressFSearch.setVisibility(GONE);

        //viewmodel
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mFavoritViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        mSharedDataViewModel = ViewModelProviders.of(this).get(SharedDataViewModel.class);


        //recyclerview
        wordIndAdapter = new WordIndAdapterPaged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(wordIndAdapter);
        wordIndAdapter.setOnItemClicked(new OnItemClickListener() {
            @Override
            public void dashboardClicked(View view, DictIndonesia dictIndonesia, int position) {
                switch (view.getId()){
                    case R.id.front_frame:
                        mHistoryViewModel.addHistory(new HistoryModel(dictIndonesia.getIdIndo()));
                        mSharedDataViewModel.setDictIndonesia(dictIndonesia);

                        Navigation.findNavController(view).navigate(R.id.action_FSearch_layout_to_fDetail_ragment);
                        break;
                    case R.id.back_frame:
                        mFavoritViewModel.addFavorite(new FavoritModel(dictIndonesia.getIdIndo()));
                        dictio.showtoast(getContext(), dictIndonesia.getWord() + " " + getString(R.string.added_to_favvorite));
                        break;
                    case R.id.copy_content_main:
                        Spanned stringe = Html.fromHtml(dictIndonesia.getWord() + "\n\n" + dictIndonesia.getMeaning());
                        ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", stringe.toString());
                        clipboard.setPrimaryClip(clip);
                        dictio.showtoast(getContext(), dictIndonesia.getWord() + " " + getString(R.string.copied));
                        break;
                }
            }

            @Override
            public void historyClicked(View view, HistoryJoinDict historyJoinDict, int position) {
                // do nothing
            }

            @Override
            public void favoriteClicked(View view, FavoriteJoinDict favoriteJoinDict, int position) {
                //do nothing
            }
        });

        return view;
    }




    private void startSearch(String query) { //call inside search
        progressFSearch.setVisibility(View.VISIBLE);
        placeholder(GONE);

        rvGlobal.setAlpha(0f);

        fastScrollerGlobal.setSectionIndexer(wordIndAdapter);
        fastScrollerGlobal.attachRecyclerView(rvGlobal);

    Runnable runnable = () -> {

                mWordViewModel.getSearchPaged(query).observe(getViewLifecycleOwner(), dictIndonesias -> {
                    wordIndAdapter.submitList(dictIndonesias);
                    Timber.i("startSearch: %s", dictIndonesias.size());
                    if (dictIndonesias.size() == 0) {
                        placeholder(INVISIBLE);
                    } else {
                        resultEmpty();
                    }
                });

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_toolbar, menu);
        //super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchMenu = menu.findItem(R.id.searchview_toolbar);
        searchMenu.expandActionView();
        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //dang
                Navigation.findNavController(getView()).navigateUp();
                return false;
            }
        });

        SearchView searchView = (SearchView) searchMenu.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchview_toolbar:
                Toast.makeText(getContext(), "yuhuuu", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void placeholder(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            textEmpty.setVisibility(visibility);
            listEmpty.setVisibility(visibility);
        } else {
            throw new IllegalArgumentException();
        }
    }

    void resultEmpty() {
        listEmpty.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_found));
        textEmpty.setText("Result not found, Try again");
    }
}
