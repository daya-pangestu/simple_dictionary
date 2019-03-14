package com.daya.jojoman.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.daya.jojoman.R;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.l4digital.fastscroll.FastScroller;
import com.paulrybitskyi.persistentsearchview.PersistentSearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import br.com.liveo.searchliveo.SearchLiveo;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchLiveo.OnSearchListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.persistentSearchView)
    PersistentSearchView searchView;

    @BindView(R.id.rv_global)
    RecyclerView rvGlobal;
    @BindView(R.id.fast_scroller_global)
    FastScroller fastScrollerGlobal;

    private KataViewModel kataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        kataViewModel = ViewModelProviders.of(this).get(KataViewModel.class);
        searchView.setOnLeftBtnClickListener(v -> onBackPressed());
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_search, menu);

            toolbarSearch.hasExpandedActionView();

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvGlobal.setLayoutManager(layoutManager);
            rvGlobal.setHasFixedSize(true);
            kataINDAdapter = new KataINDAdapter(position -> {

            }, FROM_SEARCH);

            fastScrollerGlobal.setSectionIndexer(kataINDAdapter);

            fastScrollerGlobal.attachRecyclerView(rvGlobal);

            return true;
        }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changedSearch(CharSequence charSequence) {
        Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show();
    }
}
