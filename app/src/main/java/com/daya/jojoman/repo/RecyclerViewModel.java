package com.daya.jojoman.repo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.recyclerview.KataINDAdapter;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class RecyclerViewModel extends AndroidViewModel {
    private final Context context;
    private final RecyclerView recyclerView;
    private final FastScroller fastScroller;
    private KataINDAdapter kataINDAdapter;

    public RecyclerViewModel(@NonNull Application application, RecyclerView recyclerView, FastScroller fastScroller) {

        super(application);
        this.context = application.getApplicationContext();
        this.recyclerView = recyclerView;
        this.fastScroller = fastScroller;
    }

    public void setlist(List<DictIndonesia> dictIndonesias) {
        if (dictIndonesias != null) {
            kataINDAdapter.setDict(dictIndonesias);
            kataINDAdapter.notifyDataSetChanged();
            Log.i(TAG, "onChanged:panjang  " + dictIndonesias.size());
        } else {
            kataINDAdapter.setDict(null);
            kataINDAdapter.notifyDataSetChanged();
            Log.i(TAG, "onChanged:panjang  recyclerview kososng ");
        }
    }

    private KataINDAdapter getAdapter() {
        return kataINDAdapter;
    }

    public void setAdapter(int kode) {
        kataINDAdapter = new KataINDAdapter(position -> {

        }, kode);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager); //belum ada referensi
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(getAdapter());


        fastScroller.setSectionIndexer(getAdapter());
        fastScroller.attachRecyclerView(recyclerView);

    }
}
