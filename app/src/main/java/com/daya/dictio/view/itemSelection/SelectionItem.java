package com.daya.dictio.view.itemSelection;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

public class SelectionItem {

    public static SelectionTracker<Long> instance(RecyclerView recyclerView) {
        SelectionTracker<Long> selectionTracker = new SelectionTracker.Builder<>("selection-id",
                recyclerView,
                new KeyProvider(recyclerView.getAdapter()),
                new DetailSLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(new Predicate())
                .build();

        return selectionTracker;
    }

}
