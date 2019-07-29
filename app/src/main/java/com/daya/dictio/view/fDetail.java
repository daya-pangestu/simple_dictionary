package com.daya.dictio.view;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.MOVE;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.view.layout_thing.DialogSubmitListener;
import com.daya.dictio.view.recyclerview_adapter.OtherDetailAdapter;
import com.daya.dictio.viewmodel.OtherViewModel;
import com.daya.dictio.viewmodel.SharedDataViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
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
    RelativeLayout cardDetail;
    @BindView(R.id.fab_detail_add_meaning)
    FloatingActionButton fabAddMeaning;
    @BindView(R.id.recycler_detail)
    RecyclerView recyclerDetail;
    @BindView(R.id.copy_content_detail)
    ImageButton btnCopyDetail;
    @BindView(R.id.fDetail_root)
    CoordinatorLayout fDetailRoot;


    private SharedDataViewModel mSharedDataViewModel;

    private OtherViewModel otherViewModel;
    private OtherDetailAdapter otherDetailAdapter;
    private DictIndonesia dict;
    private Unbinder unbinder;
    private int rotate = 0;
    private boolean visibility = false;

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
        if (item.getItemId() == android.R.id.home) {
            NavHostFragment.findNavController(this).navigateUp();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fdetail, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(getString(R.string.detail));
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        otherViewModel = ViewModelProviders.of(getActivity()).get(OtherViewModel.class);
        mSharedDataViewModel = ViewModelProviders.of(getActivity()).get(SharedDataViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardDetail.setOnClickListener(this::expandCollapseCardView);

        fabAddMeaning.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FDialog dialog = FDialog.Instance();
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
        recyclerDetail.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerDetail.setAdapter(otherDetailAdapter);


        recyclerDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fabAddMeaning.isShown()) {
                    fabAddMeaning.hide();
                } else {
                    fabAddMeaning.show();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mSharedDataViewModel.getDictIndonesia().observe(getViewLifecycleOwner(), object -> {
            dict = object;
            textDetail.setText(object.getWord());
            penjelasanDetail.setText(Html.fromHtml(object.getMeaning()));
            setAnotherMeaning(object.getIdIndo());
        });



    }

    private void setAnotherMeaning(int s) {
        otherViewModel.getOtherMeaning(s).observe(getViewLifecycleOwner(), otherMeaningModels -> {
            if (!otherMeaningModels.isEmpty()) {
                otherDetailAdapter.addOtherMeaning(otherMeaningModels);
            }
            if (otherMeaningModels.size() >= 7) {
                moveFabPosition(MOVE.START);
            } else {
                moveFabPosition(MOVE.END);
            }
        });
    }



    private void moveFabPosition(@MOVE int  position) {
        Handler handler = new Handler();
        handler.postDelayed(()  -> {
                TransitionManager.beginDelayedTransition(fDetailRoot);
                CoordinatorLayout.LayoutParams fabPositionParams = (CoordinatorLayout.LayoutParams) fabAddMeaning.getLayoutParams();
                fabPositionParams.gravity = (position == MOVE.END) ? (Gravity.END | Gravity.BOTTOM) : (Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                fabAddMeaning.setLayoutParams(fabPositionParams);

        }, 500L);
    }

    private void expandCollapseCardView(View view) {
        rotate = rotate == 0 ? 180 : 0;
        expandCollapseIcon.animate().rotation(rotate).setDuration(300).start();
        TransitionManager.beginDelayedTransition(cardDetail);
        visibility = !visibility;
        penjelasanDetail.setVisibility(visibility ? View.VISIBLE : View.GONE);
        tex.setVisibility(visibility ? View.VISIBLE : View.GONE);
        btnCopyDetail.setVisibility(visibility ? View.VISIBLE : View.GONE);

        Snackbar snackbar = Snackbar.make(view, visibility ? getString(R.string.expanded) : getString(R.string.collapsed), Snackbar.LENGTH_LONG);
        snackbar.show();

    }


    @Override
    public void onfinishedDialog(String text) {
        Toast.makeText(getActivity(), text + " added", Toast.LENGTH_SHORT).show();
        otherViewModel.addMeaning(new OtherMeaningModel(text, dict.getIdIndo()));
    }

/*    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).initViewDetailOnPause();
        ((MainActivity) getActivity()).animateNavShown();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).initViewDetailOnResume();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}

