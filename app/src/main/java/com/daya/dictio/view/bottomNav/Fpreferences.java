package com.daya.dictio.view.bottomNav;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.daya.dictio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import im.delight.android.languages.Language;

public class Fpreferences extends PreferenceFragmentCompat {
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_app, rootKey);
        setHasOptionsMenu(true);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.setting));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.delete_menu_toolbar).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPause() {
        Language.setFromPreference(getActivity(), getString(R.string.language_key), true);
        super.onPause();
    }


    //useless for now
}
