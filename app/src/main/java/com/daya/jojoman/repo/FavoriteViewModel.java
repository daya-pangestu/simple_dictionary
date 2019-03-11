package com.daya.jojoman.repo;

import android.app.Application;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.model.FavoritModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteViewModel extends AndroidViewModel {
    private final DictRepository dictRepository;
    List<DictIndonesia> list;


    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
    }

    public void addFavorite(FavoritModel fm) {
        //insert transaction to db tabel history

        //list.add(d)

        dictRepository.addFavorite(fm);

    }

    public LiveData<List<FavoritModel>> getList() {
        return dictRepository.getFavorite();
    }

    public void deleteFavorite() {
        dictRepository.deleteFavorite();
    }
}
