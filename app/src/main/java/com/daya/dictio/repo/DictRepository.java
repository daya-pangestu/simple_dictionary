package com.daya.dictio.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.db.DictIdDao;
import com.daya.dictio.model.db.DictIndoDatabase;
import com.daya.dictio.model.db.FavoriteDao;
import com.daya.dictio.model.db.HistoryDao;

import java.util.List;

import androidx.lifecycle.LiveData;


public class DictRepository {

    private final DictIdDao indDao;
    private final DictIndoDatabase db;
    private final HistoryDao historyDao;
    private final FavoriteDao favoritDao;

    public DictRepository(Application application) {
        db = DictIndoDatabase.getINSTANCE(application);
        indDao = db.dictIdDao();
        historyDao = db.historyDao();
        favoritDao = db.favoriteDao();
    }

    //dashboard
    public LiveData<List<DictIndonesia>> getAllWord() {
        return indDao.getAll();
    }

    public void insertTransaction(DictIndonesia dictIndonesia) {

        new inserTransactAsyncTask(db).execute(dictIndonesia);
    }

    //history
    public LiveData<List<HistoryModel>> gethistory() {
        return historyDao.loadhistory();
    }

    public void addHistory(HistoryModel historyModel) {
        //hDao.insert(historyModel);
        new insertHistoryAsynck(historyDao).execute(historyModel);
    }

    public void deleteHistory() {
        new deleteHistoryAsynck(historyDao).execute();

    }


    //favorite
    public LiveData<List<FavoritModel>> getFavorite() {
        return favoritDao.loadFavorite();
    }

    public void addFavorite(FavoritModel favoritModel) {
        new insertFavoriteAsync(favoritDao).execute(favoritModel);
    }

    public void deleteFavorite() {
        new deleteFavoriteAsync(favoritDao).execute();

    }

    public void deleteFavoriteAt(FavoritModel favoritModel) {
        new deleteFavoriteAsyncAt(favoritDao).execute(favoritModel);

    }

    public boolean isfavoritExists(String s) {

        return false; //not  yet configured
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


/*    --------------------------asynctask class -----------------------
    --------------------change to Rxjava in future-------------------*/


    //history
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


    //favorite
    private static class insertFavoriteAsync extends AsyncTask<FavoritModel, Void, Void> {
        final FavoriteDao dao;

        insertFavoriteAsync(FavoriteDao dao) {
            this.dao = dao;
        }


        @Override
        protected Void doInBackground(FavoritModel... favoritModels) {
            dao.insert(favoritModels[0]);

            return null;
        }
    }

    private static class deleteFavoriteAsync extends AsyncTask<Void, Void, Void> {
        final FavoriteDao dao;

        deleteFavoriteAsync(FavoriteDao favoritDao) {
            this.dao = favoritDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.delete();

            return null;
        }
    }

    private static class deleteFavoriteAsyncAt extends AsyncTask<FavoritModel, Void, Void> {
        final FavoriteDao dao;

        deleteFavoriteAsyncAt(FavoriteDao favoritDao) {
            this.dao = favoritDao;
        }


        @Override
        protected Void doInBackground(FavoritModel... favoritModels) {
            dao.deleteAt(favoritModels[0]);
            return null;
        }
    }


    //insert to db
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

}
