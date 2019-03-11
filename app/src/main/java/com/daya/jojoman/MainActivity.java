package com.daya.jojoman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.daya.jojoman.layout.BottomNavigationBehavior;
import com.daya.jojoman.repo.HistoryViewModel;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    public String TAG = getClass().getSimpleName();
    public static final int FROM_DASHBOARD = 1;
    public static final int FROM_SEARCH = 2;
    public static final int FROM_HISTORY = 3;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;

    private NavHostFragment navHostFragment;
    private HistoryViewModel historyViewModel;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(navigation, navHostFragment.getNavController());
        }


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        params.setBehavior(new BottomNavigationBehavior());
        toolbarMain.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarMain);


        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBar_bar:
                Navigation.findNavController(navHostFragment.getView()).navigate(R.id.action_global_FSearch_layout);
                //navigation.setVisibility(View.GONE);
                break;

            case R.id.delete_history:
                historyViewModel.deleteHistory();
                Navigation.findNavController(navHostFragment.getView()).navigate(R.id.action_navigation_history_self);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
