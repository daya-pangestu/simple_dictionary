package com.daya.jojoman;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.daya.jojoman.MainActivity.FROM_SEARCH;

public class SearchActivity extends AppCompatActivity {
    private static String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    SearchView searchBar;
    KataViewModel model;
    KataINDAdapter kataINDAdapter;

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        model = ViewModelProviders.of(this).get(KataViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchBar = (SearchView) menu.findItem(R.id.search_sactivity).getActionView();
        //searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);

        toolbarSearch.hasExpandedActionView();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGlobal.setLayoutManager(layoutManager);
        rvGlobal.setHasFixedSize(true);
        kataINDAdapter = new KataINDAdapter(new KataINDAdapter.OnItemClickListener() {
            @Override
            public void itemclicked(int position) {

            }
        }, FROM_SEARCH);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextChange: " + query);
                model.getSearch(query).observe(SearchActivity.this, new Observer<List<DictIndonesia>>() {
                    @Override
                    public void onChanged(List<DictIndonesia> dictIndonesias) {
                        if (dictIndonesias != null) {

                            kataINDAdapter.setDict(dictIndonesias);
                            Log.i(TAG, "onChanged:panjang  " + dictIndonesias.size());
                            rvGlobal.setAdapter(kataINDAdapter);
                        } else {
                            Toast.makeText(SearchActivity.this, "nothing to show", Toast.LENGTH_SHORT).show();
                            kataINDAdapter.setDict(dictIndonesias);
                            Log.i(TAG, "onChanged:panjang  " + dictIndonesias.size());
                            rvGlobal.setAdapter(kataINDAdapter);
                        }
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.i(TAG, "onQueryTextChange: " + newText);
              /*  model.getSearch(newText).observe(SearchActivity.this, new Observer<List<DictIndonesia>>() {
                    @Override
                    public void onChanged(List<DictIndonesia> dictIndonesias) {
                        if (dictIndonesias != null) {

                        Log.i(TAG, "onChanged: " + dictIndonesias.get(0).getKata() + dictIndonesias.get(0).getPenjelasn());
                        }

                    }
                });*/
                return false;
            }
        });

        fastScrollerGlobal.setSectionIndexer(kataINDAdapter);

        fastScrollerGlobal.attachRecyclerView(rvGlobal);

        searchBar.onActionViewExpanded();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
