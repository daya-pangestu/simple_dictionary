package com.daya.jojoman.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.jojoman.db.indo.DictIdDao;
import com.daya.jojoman.db.indo.DictIndoDatabase;
import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.HistoryDao;
import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.db.indo.model.relation.History;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;


public class DictRepository {


    private DictIdDao indDao;
    DictIndoDatabase db;
    private HistoryDao hDao;

    public DictRepository(Application application) {
        db = DictIndoDatabase.getINSTANCE(application);
        indDao = db.dictIdDao();
        hDao = db.hDao();
    }

    //history
    public LiveData<List<HistoryModel>> gethistory() {
        return hDao.loadhistory();
    }

    public void addHistory(HistoryModel historyModel) {
        //hDao.insert(historyModel);
        new insertHistoryAsynck(hDao).execute(historyModel);
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


    private static class insertHistoryAsynck extends AsyncTask<HistoryModel, Void, Void> {
        HistoryDao dao;

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
