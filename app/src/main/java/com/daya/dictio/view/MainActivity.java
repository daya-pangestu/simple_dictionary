package com.daya.dictio.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.view.layout_thing.BottomNavigationBehavior;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.paging.PagedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends LocalizationActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;

    private NavHostFragment navHostFragment;
    private AppBarLayout.LayoutParams toolbarScroled;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbarMain);
        }

        toolbarScroled = (AppBarLayout.LayoutParams) toolbarMain.getLayoutParams();

        setToolbarScroled(true);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(navigation, navHostFragment.getNavController());
        }
        //aktifkan ini kalau mau efek bottomnav terscroll
      /*  CoordinatorLayout.LayoutParams paramsBottomNav = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        paramsBottomNav.setBehavior(new BottomNavigationBehavior());*/


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBar_bar:
                Navigation.findNavController(Objects.requireNonNull(navHostFragment.getView())).navigate(R.id.action_global_FSearch_layout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initViewDetailOnPause() {
        setToolbarScroled(true);
        viewNavigation(true);
        setUpButton(false);
    }

    public void initViewDetailOnResume() {
        setToolbarScroled(false);
        viewNavigation(false);
        setUpButton(true);
        showHideToolbar(true);
    }

    //menagtur bisa tidaknya toolbar ter hiden jika scroll dilakukan
    private void setToolbarScroled(boolean scrollingToolbar) {
        if (scrollingToolbar) {
            toolbarScroled.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        } else {
            toolbarScroled.setScrollFlags(0);
        }
    }

    //mengur tampil tidaknya bottomnavigationview
    private void viewNavigation(boolean viewOrHide) {
        if (viewOrHide) {
            navigation.setVisibility(View.VISIBLE);
        } else {
            navigation.setVisibility(View.GONE);
        }
    }

    //mengatur tombol kembali tampil atau tidak
    private void setUpButton(boolean upButtonShown) {
        if (upButtonShown) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    //menjadikan toolbar pasti terlihat
    public void showHideToolbar(boolean showOrHide) {
        if (showOrHide) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }

    public void animateNavShown() {
        new BottomNavigationBehavior().showBottomNavigationView(navigation);
    }
}
