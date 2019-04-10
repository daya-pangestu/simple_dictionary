package com.daya.dictio.view.itemSelection;

import android.view.MotionEvent;
import android.view.View;

import com.daya.dictio.view.recyclerview_adapter.WordIndAdapterPaged;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class DetailSLookup extends ItemDetailsLookup<Long> {
    private final RecyclerView recyclerView;

    public DetailSLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof WordIndAdapterPaged.WordHolder) {
                return ((WordIndAdapterPaged.WordHolder) viewHolder).getDetails();
            }


        }


        return null;
    }
}
