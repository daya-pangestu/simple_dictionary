package com.daya.jojoman.search;

import android.app.Activity;

import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.l4digital.fastscroll.FastScroller;

import androidx.appcompat.widget.SearchView;

class GlobalSearch {

    private SearchView materialSearchBar;
    FastScroller fastScroller;
    private final KataViewModel kataViewModel;


    public GlobalSearch(KataINDAdapter adapter, Activity activity,KataViewModel v) {
        KataINDAdapter adapter1 = adapter;
        Activity activity1 = activity;
        this.kataViewModel = v;

    }



    }
