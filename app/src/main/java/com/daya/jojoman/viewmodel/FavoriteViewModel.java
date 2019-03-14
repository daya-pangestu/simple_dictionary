package com.daya.jojoman.viewmodel;

import android.app.Application;

import com.daya.jojoman.model.FavoritModel;
import com.daya.jojoman.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavoriteViewModel extends AndroidViewModel {
    private final DictRepository dictRepository;


    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);

    }

    public LiveData<Integer> getJumlahFavorite() {
        return dictRepository.getJumlahFavorite();
    }

    public void addFavorite(FavoritModel fm) {

        dictRepository.addFavorite(fm);
    }

    public LiveData<List<FavoritModel>> getList() {
        return dictRepository.getFavorite();
    }

    public void deleteAllFavorite() {
        dictRepository.deleteFavorite();
    }

    public void deleteFavoriteAt(FavoritModel favoritModel) {
        dictRepository.deleteFavoriteAt(favoritModel);
    }

}
