package com.daya.dictio.viewmodel;

import android.app.Application;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class WordViewModel extends AndroidViewModel {

    private final DictRepository dictRepository;
    private final LiveData<List<DictIndonesia>> allWord;
    private DictIndonesia sendToDetail;
    private final PagedList.Config pagedListConfid = (new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20).build());

    public WordViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        allWord = dictRepository.getAllWord();

    }
    public LiveData<List<DictIndonesia>> getSearch(String s) {
        return dictRepository.getSearch(s);
    }


    public LiveData<PagedList<DictIndonesia>> getAllWordPaged() {

        return dictRepository.getAllWordPaged();
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

    //next implementation
    public LiveData<PagedList<DictIndonesia>> getSearchPaged(String s) {
        String wildcardQueryIfNotSingle = (s.length() != 1) ? String.format("*%s*", s) : s;


        LiveData<PagedList<DictIndonesia>> searchPaged = new LivePagedListBuilder<>(
                dictRepository.getSearchPaged(wildcardQueryIfNotSingle), pagedListConfid).build();
        return searchPaged;
    }

    public LiveData<List<DictIndonesia>> getAllWord() {
        return allWord;
    }


}
