package com.daya.jojoman.search;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.daya.jojoman.R;
import com.daya.jojoman.db.indo.DictIndonesia;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.daya.jojoman.repo.KataViewModel;
import com.l4digital.fastscroll.FastScroller;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import static com.facebook.stetho.inspector.network.ResponseHandlingInputStream.TAG;

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


    public void activateGlobalsearch() {
        materialSearchBar = activity.findViewById(R.id.searchBar_bar);


        materialSearchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);



        }


    }
