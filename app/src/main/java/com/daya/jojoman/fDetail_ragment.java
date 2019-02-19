package com.daya.jojoman;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daya.jojoman.db.indo.DictIndonesia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class fDetail_ragment extends Fragment {



    public fDetail_ragment() {
        // Required empty public constructor
    }

    KataViewModel kataViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_detail_ragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textDetail = view.findViewById(R.id.text_detail);
        TextView textPenjelasan = view.findViewById(R.id.penjelasan_detail);

        kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);

        DictIndonesia dictIndonesia = kataViewModel.getSendToDetail();

        textDetail.setText(dictIndonesia.getKata());
        textPenjelasan.setText(dictIndonesia.getPenjelasn());

    }
}
