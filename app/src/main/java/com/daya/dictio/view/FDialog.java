package com.daya.dictio.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daya.dictio.R;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.view.layout_thing.DialogSubmitListener;
import com.daya.dictio.viewmodel.OtherViewModel;
import com.daya.dictio.viewmodel.SharedDataViewModel;

import org.jetbrains.annotations.NotNull;

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

    private DialogSubmitListener dialogListener;
    private SharedDataViewModel mSharedDataViewModel;
    private Unbinder unbinder;
    private String text_edit;

    private OtherViewModel mOtherViewModel;


    public FDialog() {
    }

    static FDialog INSTANCE;

    public static FDialog Instance() {
        if (INSTANCE == null) {
            return new FDialog();
        } else {
            return INSTANCE;
        }
    }

    public void setDialogListener(DialogSubmitListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void setEditListener(DialogSubmitListener dialogListener, String text_edit) {
        this.dialogListener = dialogListener;
        this.text_edit = text_edit;

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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, v);
        mSharedDataViewModel = ViewModelProviders.of(this).get(SharedDataViewModel.class);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtAddMeaning.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        if (text_edit != null) {
            edtAddMeaning.setText(text_edit);
            edtAddMeaning.setSelection(text_edit.length());
        }

        btnOk.setOnClickListener(v -> {
            if (edtAddMeaning.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), getString(R.string.text_empty), Toast.LENGTH_SHORT).show();
            } else {
                dialogListener.onfinishedDialog(edtAddMeaning.getText().toString());
                dismiss();
            }
        });


        btnCancel.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}






