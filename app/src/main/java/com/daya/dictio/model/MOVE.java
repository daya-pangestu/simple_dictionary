package com.daya.dictio.model;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({MOVE.START, MOVE.END})
@Retention(RetentionPolicy.SOURCE)
public @interface MOVE {
    int START = 0;
    int END = 1;
}
