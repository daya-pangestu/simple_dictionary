package com.daya.dictio.viewmodel;

import android.app.Application;

import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.repo.DictRepository;

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

    public LiveData<Integer> getTotalFavorite() {
        return dictRepository.getTotalFavorite();
    }

    public void addFavorite(FavoritModel fm) {

        dictRepository.addFavorite(fm);
    }

    public LiveData<List<FavoriteJoinDict>> getList() {
        return dictRepository.getFavorite();
    }

    public void deleteAllFavorite() {
        dictRepository.deleteFavorite();
    }

    public void deleteFavoriteAt(FavoritModel favoritModel) {
        dictRepository.deleteFavoriteAt(favoritModel);
    }

    //next implementation
    public boolean isFavoritExists(int s) {
        return dictRepository.isfavoritExist(s);
    }


}
