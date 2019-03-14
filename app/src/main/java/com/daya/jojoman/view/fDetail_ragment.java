package com.daya.jojoman.view;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daya.jojoman.R;
import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.view.layout_thing.DialogSubmitListener;
import com.daya.jojoman.viewmodel.KataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.TransitionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class fDetail_ragment extends Fragment implements DialogSubmitListener {


    @BindView(R.id.text_detail)
    TextView textDetail;
    @BindView(R.id.expandCollapse_icon)
    ImageView expandCollapseIcon;
    @BindView(R.id.textView)
    TextView tex;
    @BindView(R.id.penjelasan_detail)
    TextView penjelasanDetail;
    @BindView(R.id.card_detail)
    CardView cardDetail;
    Unbinder unbinder;
    @BindView(R.id.fab_detail_add_meaning)
    FloatingActionButton fabAddMeaning;

    private BottomNavigationView bottomNavigationView;
    private KataViewModel kataViewModel;
    private int rotate = 0;

    public fDetail_ragment() {
        // Required empty public constructor
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.delete_menu_toolbar).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavHostFragment.findNavController(this).navigateUp();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_f_detail_ragment, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail");

        unbinder = ButterKnife.bind(this, v);

        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kataViewModel = ViewModelProviders.of(getActivity()).get(KataViewModel.class);

        DictIndonesia dict = kataViewModel.getSendToDetail();

        textDetail.setText(dict.getKata());

        penjelasanDetail.setText(Html.fromHtml(dict.getPenjelasn()));

        cardDetail.setOnClickListener(v -> expandCollapseCardView(v));


        fabAddMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FDialogFragment dialog = FDialogFragment.newInstance();
                dialog.setDialogListener(fDetail_ragment.this);

                dialog.show(fm, "dialog");
            }
        });

    }

    @Override
    public void onfinishedDialog(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    private void expandCollapseCardView(View view) {

        rotate = rotate == 0 ? 180 : 0;
        expandCollapseIcon.animate().rotation(rotate).setDuration(500).start();

        if (tex.getVisibility() == View.VISIBLE || penjelasanDetail.getVisibility() == View.VISIBLE) {

            TransitionManager.beginDelayedTransition(cardDetail);
            penjelasanDetail.setVisibility(View.GONE);
            tex.setVisibility(View.GONE);

            Snackbar snackbar = Snackbar.make(view, "collapsed", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            TransitionManager.beginDelayedTransition(cardDetail);
            penjelasanDetail.setVisibility(View.VISIBLE);
            tex.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(view, "expanded", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    //onpause, onresume, onactivitycreated,handleonbackpressed semuanya menangani tombol tombol back muncul di disini tapi hilang id dashboard
    @Override
    public void onPause() {
        super.onPause();
        bottomNavigationView.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


}

