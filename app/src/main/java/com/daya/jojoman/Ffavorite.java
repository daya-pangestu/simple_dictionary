package com.daya.jojoman;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ffavorite extends Fragment {




    public Ffavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ffavorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button goToDetail = view.findViewById(R.id.goToDetail);

        goToDetail.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_fovorite_to_fDetail_ragment));



    }
}
