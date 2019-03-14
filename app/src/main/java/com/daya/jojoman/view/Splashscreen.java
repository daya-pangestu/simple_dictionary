package com.daya.jojoman.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.daya.jojoman.R;
import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.repo.Appreferen;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class Splashscreen extends AppCompatActivity {

    @BindView(R.id.splash_progress)
    ProgressBar splashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        new LoadData().execute();

    }


    private class LoadData extends AsyncTask<Void, Integer, Void> {
        double dprogres;
        double dmaxProgress;
        KataViewModel kataViewModel;
        Appreferen appreferen;

        @Override
        protected void onPreExecute() {
            kataViewModel = ViewModelProviders.of(Splashscreen.this).get(KataViewModel.class);
            appreferen = new Appreferen(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstrun = appreferen.getFirstRun();

            if (firstrun) {

                List<DictIndonesia> dictIndonesiaList = preLoadDict();
                int panjangdict = dictIndonesiaList.size();

                dprogres = 30;
                publishProgress((int) dprogres);
                Double progressMaInsert = 80.0;
                Double dProgressDiff = (progressMaInsert- dprogres) / (panjangdict);


                for (DictIndonesia dictList : dictIndonesiaList) {
                    kataViewModel.inserttransactional(dictList);
                    dprogres += dProgressDiff;
                    publishProgress((int) dprogres);
                    Log.i(TAG, "doInBackground: " + dictList.getKata());

                }
                publishProgress((int) dmaxProgress);
                appreferen.setFirstRun();
            } else {
                try {
                    synchronized (this) {
                        this.wait(500);
                        publishProgress(50);

                        this.wait(500);
                        publishProgress((int) dmaxProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            splashProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Intent o = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(o);
            finish();
        }
    }

    private List<DictIndonesia> preLoadDict() {
        List<DictIndonesia> preload = new ArrayList<>();

        try {

            InputStream istream = getResources().openRawResource(R.raw.kbbi_data);
            InputStreamReader iStreamReader = new InputStreamReader((istream));
            BufferedReader bfReader = new BufferedReader(iStreamReader);
/*

            InputStream istream = getResources().openRawResource(R.raw.dictionary_english );
            InputStreamReader iStreamReader = new InputStreamReader((istream));
            BufferedReader bfReader = new BufferedReader(iStreamReader);
*/


            String textGabung;

            while ((textGabung = bfReader.readLine()) != null) {

                String spliter[] = textGabung.split("\t", 3);

                DictIndonesia dictIndonesia = new DictIndonesia(spliter[1], spliter[2]);

                preload.add(dictIndonesia);

            }
            // while (textGabung != null) ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return preload;
    }
}
