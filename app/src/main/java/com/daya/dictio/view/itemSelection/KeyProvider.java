package com.daya.dictio.view.itemSelection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class KeyProvider extends ItemKeyProvider<Long> {
    private RecyclerView.Adapter adapter;

    protected KeyProvider(RecyclerView.Adapter adapter) {
        super(ItemKeyProvider.SCOPE_MAPPED);
        this.adapter = adapter;

    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) position;
    }

    @Override
    public int getPosition(@NonNull Long key) {
        long value = key;
        return (int) value;
    }


}
