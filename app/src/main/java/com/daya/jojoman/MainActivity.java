package com.daya.jojoman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.daya.jojoman.db.indo.DictIndonesia;
import com.daya.jojoman.search.SearchSugest;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    public String TAG = getClass().getSimpleName();

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        KataViewModel kataViewModel = ViewModelProviders.of(this).get(KataViewModel.class);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(navigation, navHostFragment.getNavController());
        }
        toolbarMain.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        LayoutInflater inflater1 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        SearchSugest sugest = new SearchSugest(inflater1);
        kataViewModel.getAllKata().observe(this, new Observer<List<DictIndonesia>>() {
            @Override
            public void onChanged(List<DictIndonesia> dictIndonesias) {
                sugest.setSugestions(dictIndonesias);
                searchBar.setCustomSuggestionAdapter(sugest);
            }
        });





    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
/*

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MaterialSearchBar searchBar = (MaterialSearchBar)menu.findItem(R.id.searchBar).getActionView();


        return true;
    }*/
}
