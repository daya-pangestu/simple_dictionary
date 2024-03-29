package com.daya.dictio.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(entity = DictIndonesia.class,
                parentColumns = "idIndo",
                childColumns = "idOwner",
                onDelete = CASCADE,
                onUpdate = CASCADE),
        indices = {@Index(value = "idOwner", unique = true)}

)
public class HistoryModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idOwner;

    public HistoryModel(int idOwner) {
        this.idOwner = idOwner;
    }

    @Ignore // digunain buat delete
    public HistoryModel(int id, int idOwner) {
        this.id = id;
        this.idOwner = idOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }


}
