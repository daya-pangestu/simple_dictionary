package com.daya.dictio.view.bottomNav;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.view.layout_thing.BottomNavigationBehavior;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
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
    FastScroller mFastScrollerGlobal;

    private Unbinder unbinder;
    private WordIndAdapterPaged mWordIndAdapterPaged;
    private FavoriteViewModel mfavoriteViewModel;
    private HistoryViewModel mHistoryViewModel;
    private WordViewModel mWordViewModel;

    public Fdashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fdashboard, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, v);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.dashboard));
        new BottomNavigationBehavior().animateBarVisibility(v, true);

        //viewmodel
         mWordViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        mfavoriteViewModel = ViewModelProviders.of(getActivity()).get(FavoriteViewModel.class);
        mHistoryViewModel = ViewModelProviders.of(getActivity()).get(HistoryViewModel.class);

        //recyclerview
        mWordIndAdapterPaged = new WordIndAdapterPaged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.addItemDecoration(new DividerItemDecoration(v.getContext(), DividerItemDecoration.VERTICAL));
        rvGlobal.setAdapter(mWordIndAdapterPaged);


        //tampilkan semua isi db
        mWordViewModel.getAllWordPaged().observe(getViewLifecycleOwner(), dictIndonesias -> mWordIndAdapterPaged.submitList(dictIndonesias));

        //onclick recyclerview
        mWordIndAdapterPaged.setOnindClickListener(new OnItemClickListener() {
            @Override
            public void dashboardClicked(View view, DictIndonesia dictIndonesia, int position) {
                int id = dictIndonesia.getIdIndo();
                String word = dictIndonesia.getWord();
                String meaning = dictIndonesia.getMeaning();

                Snackbar snackbar;
                switch (view.getId()) {
                    case R.id.front_frame:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_fDetail_ragment2);
                        mWordViewModel.setSendToDetail(dictIndonesia);
                        mHistoryViewModel.addHistory(new HistoryModel(id));

                        break;
                    case R.id.back_frame:
                        mfavoriteViewModel.addFavorite(new FavoritModel(id));
                        snackbar = Snackbar.make(view, word + getString(R.string.added_to_favvorite), Snackbar.LENGTH_LONG).setAction("OK", v1 -> {
                        });
                        snackbar.show();
                        break;

                    case R.id.copy_content_main:
                        Spanned stringe = Html.fromHtml(word + "\n\n" + meaning);
                        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", stringe.toString());
                        clipboard.setPrimaryClip(clip);
                        snackbar = Snackbar.make(v, dictIndonesia.getWord() + getString(R.string.copied), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    default:
                        break;
                }
            }

        });

        //fastscroll
        mFastScrollerGlobal.setSectionIndexer(mWordIndAdapterPaged);
        mFastScrollerGlobal.attachRecyclerView(rvGlobal);
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
