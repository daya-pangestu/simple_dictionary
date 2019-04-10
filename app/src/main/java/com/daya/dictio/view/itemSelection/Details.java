package com.daya.dictio.view.itemSelection;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class Details extends ItemDetailsLookup.ItemDetails<Long> {
    private long position;

    public Details() {

    }

    @Override
    public int getPosition() {
        return (int) position;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        return position;
    }

    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
        return true;
    }
}
