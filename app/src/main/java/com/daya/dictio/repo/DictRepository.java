package com.daya.dictio.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.model.db.DictIdDao;
import com.daya.dictio.model.db.DictIndoDatabase;
import com.daya.dictio.model.db.FavoriteDao;
import com.daya.dictio.model.db.HistoryDao;
import com.daya.dictio.model.db.OtherMeaningDao;
import com.daya.dictio.model.join.FavoriteJoinDict;
import com.daya.dictio.model.join.HistoryJoinDict;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;


public class DictRepository {
    private static final String TAG = DictRepository.class.getSimpleName();
    private final DictIdDao indDao;
    private final HistoryDao historyDao;
    private final FavoriteDao favoritDao;
    private final OtherMeaningDao otherMeaningDao;
    private final PagedList.Config pagedListConfid = (new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20).build());

    public DictRepository(Application application) {
        DictIndoDatabase db = DictIndoDatabase.getINSTANCE(application);
        indDao = db.dictIdDao();
        historyDao = db.historyDao();
        favoritDao = db.favoriteDao();
        otherMeaningDao = db.otherMeaningDao();
    }

    //dashboard
    public LiveData<List<DictIndonesia>> getAllWord() {
        return indDao.getAll();
    }

    public LiveData<PagedList<DictIndonesia>> getAllWordPaged() {

        new LivePagedListBuilder<>(
                indDao.getAllPaged(), pagedListConfid).build();
        DataSource.Factory<Integer, DictIndonesia> factory = indDao.getAllPaged();

        return new LivePagedListBuilder<>(factory, pagedListConfid).build();
    }

    public void insertTransaction(DictIndonesia dictIndonesia) {
        AsyncTask.execute(() -> indDao.insertTransac(dictIndonesia));
    }

    //history
    public LiveData<List<HistoryJoinDict>> gethistory() {
        return historyDao.loadhistory();
    }


    public void addHistory(HistoryModel historyModel) {
        AsyncTask.execute(() -> historyDao.insert(historyModel));
    }

    public void deleteHistory() {
        AsyncTask.execute(historyDao::delete);

    }

    public void deleteHistoryAt(HistoryModel historyModel) {
        AsyncTask.execute(() -> historyDao.deleteAt(historyModel));

    }

    //favorite
    public LiveData<List<FavoriteJoinDict>> getFavorite() {
        return favoritDao.loadFavorite();
    }

    public void addFavorite(FavoritModel favoritModel) {
        AsyncTask.execute(() -> favoritDao.insert(favoritModel));

    }

    public void deleteFavorite() {
        AsyncTask.execute(favoritDao::delete);

    }

    public void deleteFavoriteAt(FavoritModel favoritModel) {
        AsyncTask.execute(() -> favoritDao.deleteAt(favoritModel));

    }


    //replace thos with proper code -> rxjava
    public boolean isfavoritExists(int s) {

        AsyncTask.execute(() -> favoritDao.isfavoritExists(s));

        return true;
    }

    public LiveData<Integer> getTotalFavorite() {
        return favoritDao.getTotalFavorite();
    }


    //dictionary search
    public LiveData<List<DictIndonesia>> getSearch(String s) {
        String wildcardQuery;
        wildcardQuery = String.format("*%s*", s);

        return indDao.getSearchQuery(wildcardQuery);
    }

    public LiveData<PagedList<DictIndonesia>> getSearchPaged(String s) {
        String wildcardQuery;
        wildcardQuery = String.format("*%s*", s);

        LiveData<PagedList<DictIndonesia>> searchPaged = new LivePagedListBuilder<>(
                indDao.getSearchQueryPaged(wildcardQuery), pagedListConfid).build();
        return searchPaged;
    }

    //other meaning
    public void addMeaning(OtherMeaningModel otherMeaning) {
        AsyncTask.execute(() -> otherMeaningDao.insertOther(otherMeaning));

    }

    public LiveData<List<OtherMeaningModel>> getOtherMeaning(int s) {
        return otherMeaningDao.getOtherMeaning(s);
    }

    public void deleteOtherMeaning(OtherMeaningModel otherMeaningModel) {
        AsyncTask.execute(() -> otherMeaningDao.deleteOtherMeaning(otherMeaningModel));
    }



}
