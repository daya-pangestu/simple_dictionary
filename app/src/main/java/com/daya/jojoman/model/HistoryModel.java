package com.daya.jojoman.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String kataHistory;
    private String PenjelasanHistory;

    public HistoryModel(String kataHistory, String PenjelasanHistory) {
        this.kataHistory = kataHistory;
        this.PenjelasanHistory = PenjelasanHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKataHistory() {
        return kataHistory;
    }

    public void setKataHistory(String kataHistory) {
        this.kataHistory = kataHistory;
    }

    public String getPenjelasanHistory() {
        return PenjelasanHistory;
    }

    public void setPenjelasanHistory(String penjelasanHistory) {
        PenjelasanHistory = penjelasanHistory;
    }
}
