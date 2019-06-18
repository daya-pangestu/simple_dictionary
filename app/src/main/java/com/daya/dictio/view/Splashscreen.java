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
import io.reactivex.internal.util.ArrayListSupplier;
import timber.log.Timber;

public class Splashscreen extends LocalizationActivity {

    @BindView(R.id.splash_progress)
    ProgressBar splashProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);

        //new LoadData().execute();
        Intent o = new Intent(Splashscreen.this, MainActivity.class);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(o);

    }

}
/*probably change this into fragment with navigation component
 * loaded data it just sample testing perpose
 * change into real one next*/