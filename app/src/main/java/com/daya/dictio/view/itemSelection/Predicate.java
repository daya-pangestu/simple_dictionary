package com.daya.dictio.view.itemSelection;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;

public class Predicate extends SelectionTracker.SelectionPredicate<Long> {
    @Override
    public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
        return true;
    }

    @Override
    public boolean canSetStateAtPosition(int position, boolean nextState) {
        return true;
    }

    @Override
    public boolean canSelectMultiple() {
        return true;
    }
}
