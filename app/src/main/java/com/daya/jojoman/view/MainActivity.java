package com.daya.jojoman.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daya.jojoman.R;
import com.daya.jojoman.view.layout_thing.BottomNavigationBehavior;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final int FROM_DASHBOARD = 1;
    public static final int FROM_SEARCH = 2;
    public static final int FROM_HISTORY = 3;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;

    private NavHostFragment navHostFragment;

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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBar_bar:
                /*Navigation.findNavController(navHostFragment.getView()).navigate(R.id.action_global_FSearch_layout);*/
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
                navigation.setVisibility(View.GONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
