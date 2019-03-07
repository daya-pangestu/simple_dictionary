package com.daya.jojoman.repo;

import android.app.Application;

import com.daya.jojoman.db.indo.model.DictIndonesia;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class KataViewModel extends AndroidViewModel {

    private DictRepository dictRepository;
    private LiveData<List<DictIndonesia>> limitRandomKata;
    private LiveData<List<DictIndonesia>> allKata;
    private LiveData<List<DictIndonesia>> allKataOnly;
    private DictIndonesia sendToDetail;
    private LiveData<List<DictIndonesia>> search;

    public LiveData<PagedList<DictIndonesia>> dictList;

    public KataViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        limitRandomKata = dictRepository.getLimitRandomKata();
        allKata = dictRepository.getAllKata();
        allKataOnly = dictRepository.getAllKataOnly();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(10)
                        .setPageSize(20).build();


    }
    public LiveData<List<DictIndonesia>> getSearch(String s) {
        return search = dictRepository.getSearch(s);
    }

    public LiveData<List<DictIndonesia>> getLimitRandomKata() {
        return limitRandomKata;
    }

    public LiveData<List<DictIndonesia>> getAllKata() {
        return allKata;
    }

    public LiveData<List<DictIndonesia>> getAllKataOnly() {
        return allKataOnly;
    }

    public void inserttransactional(DictIndonesia dictIndonesia) {
        dictRepository.insertTransaction(dictIndonesia);

    }


    public DictIndonesia getSendToDetail() {
        return sendToDetail;
    }

    public void setSendToDetail(DictIndonesia sendToDetail) {
        this.sendToDetail = sendToDetail;
    }


}
