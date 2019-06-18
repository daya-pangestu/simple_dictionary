package com.daya.dictio.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.android.dbexporterlibrary.ExportDbUtil;
import com.android.dbexporterlibrary.ExporterListener;
import com.daya.dictio.R;
import com.daya.dictio.view.layout_thing.BottomNavigationBehavior;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
        if (item.getItemId() == R.id.searchBar_bar) {
            Navigation.findNavController(Objects.requireNonNull(navHostFragment.getView())).navigate(R.id.action_global_FSearch_layout);

              /*  if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);
                } else {

                    ExportDbUtil exportDbUtil = new ExportDbUtil(this, "databases/DictindonesiaDatabase", "backup", new ExporterListener() {
                        @Override
                        public void success(@NotNull String s) {
                            Toast.makeText(MainActivity.this, "sukses", Toast.LENGTH_SHORT).show();
                            Timber.i(s);
                        }

                        @Override
                        public void fail(@NotNull String s, @NotNull String s1) {
                            Timber.i("fail: " + s);

                        }
                    });

                exportDbUtil.exportDb("/data/com.daya.jojoman/databases/");
                }
*/
        }
        return super.onOptionsItemSelected(item);
    }


    public void initViewDetailOnPause() {
        setToolbarScroled(true);
        viewNavigation(true);
        setUpButton(false);
    }

    public boolean initViewDetailOnResume() {
        setToolbarScroled(false);
        viewNavigation(false);
        setUpButton(true);
        showHideToolbar(true);
        return true;
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
