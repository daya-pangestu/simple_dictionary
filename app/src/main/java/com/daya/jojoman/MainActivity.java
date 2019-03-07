package com.daya.jojoman;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.daya.jojoman.repo.KataViewModel;
import com.daya.jojoman.search.GlobalSearch;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    public String TAG = getClass().getSimpleName();
    public static final int FROM_DASHBOARD = 1;
    public static final int FROM_SEARCH = 2;
    public static final int FORM_HISTORY = 3;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;

    NavHostFragment navHostFragment;

    public static String settag(Class c) {
        return c.getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
/*
        SearchView searchBar = (SearchView) menu.findItem(R.id.searchBar_bar).getActionView();
        searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
*/

        return true;
    }

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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(navigation, navHostFragment.getNavController());
        }
        toolbarMain.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarMain);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBar_bar:
               /* Intent i = new Intent(this, SearchActivity.class);
                startActivity(i);*/

                Navigation.findNavController(navHostFragment.getView()).navigate(R.id.action_global_FSearch_layout);

        }

        return super.onOptionsItemSelected(item);
    }

}
