package com.daya.jojoman.search;

import android.app.Activity;
import android.view.inputmethod.EditorInfo;

import com.daya.jojoman.R;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.l4digital.fastscroll.FastScroller;

import androidx.appcompat.widget.SearchView;

public class GlobalSearch {

    private KataINDAdapter adapter;
    private SearchView materialSearchBar;
    FastScroller fastScroller;
    private Activity activity;
    KataViewModel kataViewModel;


    public GlobalSearch(KataINDAdapter adapter, Activity activity,KataViewModel v) {
        this.adapter = adapter;
        this.activity = activity;
        this.kataViewModel = v;

    }



    }
