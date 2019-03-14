package com.daya.jojoman.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.model.FavoritModel;
import com.daya.jojoman.model.HistoryModel;
import com.daya.jojoman.model.db.DictIdDao;
import com.daya.jojoman.model.db.DictIndoDatabase;
import com.daya.jojoman.model.db.FavoritDao;
import com.daya.jojoman.model.db.HistoryDao;

import java.util.List;

import androidx.lifecycle.LiveData;


public class DictRepository {

    private final DictIdDao indDao;
    private final DictIndoDatabase db;
    private final HistoryDao hDao;
    private final FavoritDao favoritDao;
    private Appreferen appreferen;


    public DictRepository(Application application) {
        db = DictIndoDatabase.getINSTANCE(application);
        indDao = db.dictIdDao();
        hDao = db.hDao();
        favoritDao = db.favoritDao();
        appreferen = new Appreferen(application);
    }

    //history
    public LiveData<List<HistoryModel>> gethistory() {
        return hDao.loadhistory();
    }

    public void addHistory(HistoryModel historyModel) {
        //hDao.insert(historyModel);
        new insertHistoryAsynck(hDao).execute(historyModel);
    }

    public void deleteHistory() {
        new deleteHistoryAsynck(hDao).execute();

    }


    //favorite
    public LiveData<List<FavoritModel>> getFavorite() {
        return favoritDao.loadFavorite();
    }

    public void addFavorite(FavoritModel favoritModel) {
        //hDao.insert(historyModel);
        new insertFavoriteAsync(favoritDao).execute(favoritModel);
    }

    public void deleteFavorite() {
        new deleteFavoriteAsync(favoritDao).execute();

    }

    public void deleteFavoriteAt(FavoritModel favoritModel) {
        new deleteFavoriteAsyncAt(favoritDao).execute(favoritModel);

    }

    public LiveData<Integer> getJumlahFavorite() {
        return favoritDao.getJumlahFavorite();
    }


    //dictionary

    public LiveData<List<DictIndonesia>> getSearch(String s) {
        return indDao.getAllsearch(s);
    }


    public LiveData<List<DictIndonesia>> getAllKata() {
        return indDao.getAll();
    }

    public LiveData<List<DictIndonesia>> getAllKataOnly() {
        return indDao.getAllKataOnly();
    }

    public LiveData<List<DictIndonesia>> getLimitRandomKata() {
        return indDao.get15kataRandom();
    }


    public void insertTransaction(DictIndonesia dictIndonesia) {

        new inserTransactAsyncTask(db).execute(dictIndonesia);
    }

    private static class deleteHistoryAsynck extends AsyncTask<Void, Void, Void> {
        final HistoryDao historyDao;

        deleteHistoryAsynck(HistoryDao historyDao) {
            this.historyDao = historyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            historyDao.delete();
            return null;
        }
    }


    private static class insertFavoriteAsync extends AsyncTask<FavoritModel, Void, Void> {
        final FavoritDao dao;

        insertFavoriteAsync(FavoritDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(FavoritModel... favoritModels) {
            dao.insert(favoritModels[0]);

            return null;
        }
    }


    private static class deleteFavoriteAsync extends AsyncTask<Void, Void, Void> {
        final FavoritDao dao;

        deleteFavoriteAsync(FavoritDao favoritDao) {
            this.dao = favoritDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.delete();

            return null;
        }
    }

    private static class deleteFavoriteAsyncAt extends AsyncTask<FavoritModel, Void, Void> {
        final FavoritDao dao;

        deleteFavoriteAsyncAt(FavoritDao favoritDao) {
            this.dao = favoritDao;
        }


        @Override
        protected Void doInBackground(FavoritModel... favoritModels) {
            dao.deleteAt(favoritModels[0]);
            return null;
        }
    }


    private static class insertHistoryAsynck extends AsyncTask<HistoryModel, Void, Void> {
        final HistoryDao dao;

        insertHistoryAsynck(HistoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(HistoryModel... historyModels) {
            dao.insert(historyModels[0]);

            return null;
        }
    }


    private static class insertAsyncTask extends AsyncTask<DictIndonesia, Void, Void> {
        private final DictIdDao asyncIdDao;

        insertAsyncTask(DictIdDao dao) {
            this.asyncIdDao = dao;
        }

        @Override
        protected Void doInBackground(DictIndonesia... dictIndonesias) {
            //asyncIdDao.insertTransaction(dictIndonesias[0]);
            // asyncIdDao.insert(dictIndonesias);
            asyncIdDao.insert(dictIndonesias[0]);

            return null;
        }
    }

    private static class inserTransactAsyncTask extends AsyncTask<DictIndonesia, Void, Void> {
        private final DictIndoDatabase db;

        inserTransactAsyncTask(DictIndoDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(DictIndonesia... dictIndonesias) {

            db.runInTransaction(() -> {
                for (DictIndonesia dics : dictIndonesias) {
                    db.dictIdDao().insert(dics);
                }
            });

            return null;
        }
    }
}
