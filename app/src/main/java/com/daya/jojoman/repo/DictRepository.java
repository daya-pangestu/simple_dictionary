package com.daya.jojoman.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.daya.jojoman.db.indo.DictIdDao;
import com.daya.jojoman.db.indo.DictIndoDatabase;
import com.daya.jojoman.db.indo.DictIndonesia;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DictRepository {


    private DictIdDao indDao;


    public DictRepository(Application application) {
        DictIndoDatabase db = DictIndoDatabase.getINSTANCE(application);
        indDao = db.dictIdDao();

    }

    public LiveData<List<DictIndonesia>> getAllKata() {
        return  indDao.getAll();
    }

    public void insertTransaction(DictIndonesia dictIndonesia) {
       /* db.runInTransaction(() -> {
            for (DictIndonesia dics : dictIndonesia) {
                db.dictIdDao().insert(dics);
            }
        });*/

        new insertAsyncTask(indDao).execute(dictIndonesia);
    }


    public void insert(DictIndonesia dictIndonesia) {
        indDao.insert(dictIndonesia);

    }




    private static class insertAsyncTask extends AsyncTask<DictIndonesia, Void, Void> {
        private DictIdDao asyncIdDao;

        insertAsyncTask(DictIdDao dao) {
            asyncIdDao = dao;
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
