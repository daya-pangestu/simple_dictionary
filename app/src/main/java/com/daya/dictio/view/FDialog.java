package com.daya.dictio.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.daya.dictio.R;
import com.daya.dictio.view.layout_thing.DialogSubmitListener;
import com.daya.dictio.viewmodel.FavoriteViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FDialog extends DialogFragment {
    @BindView(R.id.txt_add_meaning)
    EditText edtAddMeaning;
    @BindView(R.id.ok_dialog)
    Button btnOk;
    @BindView(R.id.cancel_dialog)
    Button btnCancel;
    DialogSubmitListener dialogListener;
    FavoriteViewModel favoriteViewModel;
    private Unbinder unbinder;

    public FDialog() {
    }


    public void setDialogListener(DialogSubmitListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    public static FDialog newInstance() {
        Bundle args = new Bundle();
        FDialog fragment = new FDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();

        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, v);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("add costum meaning");
        edtAddMeaning.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onfinishedDialog(edtAddMeaning.getText().toString());
                dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}






