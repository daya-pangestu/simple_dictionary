package com.daya.jojoman;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.repo.HistoryViewModel;
import com.daya.jojoman.repo.KataViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class fDetail_ragment extends Fragment {
    static final String TAG = fDetail_ragment.class.getSimpleName();
    HistoryViewModel historyViewModel;


    public fDetail_ragment() {
        // Required empty public constructor
    }

    KataViewModel kataViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_f_detail_ragment, container, false);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textDetail = view.findViewById(R.id.text_detail);
        TextView textPenjelasan = view.findViewById(R.id.penjelasan_detail);

        kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);

        DictIndonesia dict = kataViewModel.getSendToDetail();

        historyViewModel.addHistory(new HistoryModel(dict.getKata(), dict.getPenjelasn()));
        Log.i(TAG, "onViewCreated: " + historyViewModel.getList());

        textDetail.setText(dict.getKata());

        textPenjelasan.setText(dict.getPenjelasn());

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.delete_history).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
