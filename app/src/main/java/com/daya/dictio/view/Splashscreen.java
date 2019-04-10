package com.daya.dictio.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.repo.Appreferen;
import com.daya.dictio.viewmodel.WordViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class Splashscreen extends LocalizationActivity {

    @BindView(R.id.splash_progress)
    ProgressBar splashProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        new LoadData().execute();

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

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        double dprogres;
        double dmaxProgress;
        WordViewModel wordViewModel;
        Appreferen appreferen;

        @Override
        protected void onPreExecute() {
            wordViewModel = ViewModelProviders.of(Splashscreen.this).get(WordViewModel.class);
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
                    wordViewModel.inserttransactional(dictList);
                    dprogres += dProgressDiff;
                    publishProgress((int) dprogres);
                    Timber.i("doInBackground: %s", dictList.getWord());

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
            o.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(o);
        }
    }
}
/*probably change this into fragment with navigation component
 * loaded data it just sample testing perpose
 * change into real one next*/