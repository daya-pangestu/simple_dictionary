package com.daya.jojoman.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.jojoman.db.indo.DictIdDao;
import com.daya.jojoman.db.indo.DictIndoDatabase;
import com.daya.jojoman.db.indo.DictIndonesia;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;


public class DictRepository {


    private DictIdDao indDao;
    DictIndoDatabase db;

    public DictRepository(Application application) {
        db = DictIndoDatabase.getINSTANCE(application);

        indDao = db.dictIdDao();
    }

    public LiveData<List<DictIndonesia>> getSearch(String s) {
        return indDao.getAllsearch(s);
    }


    public DataSource.Factory<Integer, DictIndonesia> getAllKataPaged() {
        return indDao.getAllPaged();
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


    public void insert(DictIndonesia dictIndonesia) {
        new insertAsyncTask(indDao).execute(dictIndonesia);
    }


    private static class insertAsyncTask extends AsyncTask<DictIndonesia, Void, Void> {
        private DictIdDao asyncIdDao;

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
        private DictIndoDatabase db;

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
