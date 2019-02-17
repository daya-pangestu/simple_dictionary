package com.daya.jojoman.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Appreferen {
    private SharedPreferences sharedPreferences;
    Context context;

    private static final String FIRST_RUN = "first_run";

    public Appreferen(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }
}
