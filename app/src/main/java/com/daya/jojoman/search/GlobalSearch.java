package com.daya.jojoman.search;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.daya.jojoman.R;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.l4digital.fastscroll.FastScroller;
import com.mancj.materialsearchbar.MaterialSearchBar;

public class GlobalSearch {

    private KataINDAdapter adapter;
    private MaterialSearchBar materialSearchBar;
    FastScroller fastScroller;
    private Activity activity;


    public GlobalSearch(KataINDAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;

    }


    public void activateGlobalsearch() {
        materialSearchBar = activity.findViewById(R.id.searchBar);
        materialSearchBar.setCardViewElevation(6);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  adapter.getFilter().filter(materialSearchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });





        materialSearchBar.getSearchEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);


        materialSearchBar.getSearchEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_HIDDEN);

                    return true;
                }

                return false;
            }
        });


    }
}
