package com.daya.dictio.view.layout_thing;

import android.view.View;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.model.join.HistoryJoinDict;
import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;

public interface OnItemClickListener {
    void dashboardClicked( View view, DictIndonesia dictIndonesia, int position);

    void historyClicked(View view, HistoryJoinDict historyJoinDict, int position);

    void favoriteClicked(View view, FavoriteJoinDict favoriteJoinDict, int position);
}
