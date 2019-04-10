package com.daya.dictio.repo;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Appreferen {
    private final SharedPreferences sharedPreferences;

    private static final String FIRST_RUN = "first_run";
    private static final String LANGUAGE_KEY = "language_key";
    private SharedPreferences.Editor editor;


    public Appreferen(Context context) {
        sharedPreferences = context.getSharedPreferences("preferencesDictio", MODE_PRIVATE);

    }

    public void setFirstRun() {
        editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }

    public void setlanguage(String language) {
        editor = sharedPreferences.edit();
        editor.putString(LANGUAGE_KEY, language);
        editor.apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE_KEY, null);
    }


    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
