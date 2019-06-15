package com.daya.dictio.view.layout_thing;

import android.view.View;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.model.join.HistoryJoinDict;

public interface OnItemClickListener {
    void dashboardClicked(View view, DictIndonesia dictIndonesia, int position);


}
