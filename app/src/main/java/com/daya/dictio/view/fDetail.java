package com.daya.dictio.view;

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

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.view.layout_thing.DialogSubmitListener;
import com.daya.dictio.view.recyclerview_adapter.OtherDetailAdapter;
import com.daya.dictio.viewmodel.OtherViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class fDetail extends Fragment implements DialogSubmitListener {


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
    @BindView(R.id.fab_detail_add_meaning)
    FloatingActionButton fabAddMeaning;
    @BindView(R.id.recycler_detail)
    RecyclerView recyclerDetail;

    private BottomNavigationView bottomNavigationView;
    private WordViewModel wordViewModel;
    private int rotate = 0;
    private OtherViewModel otherViewModel;
    private OtherDetailAdapter otherDetailAdapter;
    private DictIndonesia dict;
    private Unbinder unbinder;

    public fDetail() {
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
        View view = inflater.inflate(R.layout.fragment_f_detail_ragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail");
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        bottomNavigationView = getActivity().findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);


        otherViewModel = ViewModelProviders.of(this).get(OtherViewModel.class);
        wordViewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);

        dict = wordViewModel.getSendToDetail();

        textDetail.setText(dict.getWord());

        penjelasanDetail.setText(Html.fromHtml(dict.getMeaning()));

        cardDetail.setOnClickListener(this::expandCollapseCardView);


        fabAddMeaning.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FDialog dialog = FDialog.newInstance();
            dialog.setDialogListener(fDetail.this);
            if (fm != null) {
                dialog.show(fm, "dialog");
            }
        });

        //recyclerview
        otherDetailAdapter = new OtherDetailAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerDetail.setLayoutManager(layoutManager);
        recyclerDetail.setHasFixedSize(true);
        recyclerDetail.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerDetail.setAdapter(otherDetailAdapter);

        otherViewModel.getOtherMeaning(dict.getIdIndo()).observe(this, otherMeaningModels -> {
            if (!otherMeaningModels.isEmpty()) {
                otherDetailAdapter.addOtherMeaning(otherMeaningModels);
            }
        });

        return view;
    }


    @Override
    public void onfinishedDialog(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

        otherViewModel.addMeaning(new OtherMeaningModel(text, dict.getIdIndo()));
    }

    private void expandCollapseCardView(View view) {

        rotate = rotate == 0 ? 180 : 0;
        expandCollapseIcon.animate().rotation(rotate).setDuration(300).start();

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

