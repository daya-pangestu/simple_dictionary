package com.daya.dictio.view.recyclerview_adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.view.FDialog;
import com.daya.dictio.view.layout_thing.DialogSubmitListener;
import com.daya.dictio.viewmodel.OtherViewModel;
import com.github.zagum.switchicon.SwitchIconView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherDetailAdapter extends RecyclerView.Adapter<OtherDetailAdapter.OtherDetail> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<OtherMeaningModel> listOther;
    private Context context;

    @NonNull
    @Override
    public OtherDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_detail_other_meaning, parent, false);
        this.context = parent.getContext();
        viewBinderHelper.setOpenOnlyOne(true);
        return new OtherDetail(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherDetail holder, int position) {
        OtherMeaningModel current = listOther.get(position);
        viewBinderHelper.bind(holder.swipeOtherMeaning, String.valueOf(current.getIdOther()));

        holder.bind(current);


    }

    @Override
    public int getItemCount() {
        return (listOther != null) ? listOther.size() : 0;
    }

    public void addOtherMeaning(List<OtherMeaningModel> otherMeaning) {
        this.listOther = otherMeaning;
        notifyDataSetChanged();
    }


    private void removeItemAt(int index) {
        if (index != -1 && index < listOther.size()) {
            listOther.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }

    public class OtherDetail extends RecyclerView.ViewHolder implements View.OnClickListener, DialogSubmitListener {
        final OtherViewModel otherViewModel;
        @BindView(R.id.text_other_detail)
        TextView textOtherDetail;

        @BindView(R.id.btn_edit_other_detail)
        SwitchIconView btnEditOtherDetail;

        @BindView(R.id.swipe_other_meaning)
        SwipeRevealLayout swipeOtherMeaning;

        @BindView(R.id.switch_icon_delete_Other)
        SwitchIconView switchIconDeleteOther;

        @BindView(R.id.back_frame_other_meaning)
        LinearLayout backFrameOtherMeaning;

        @BindView(R.id.front_frame_other_meaning)
        LinearLayout frontFrameOtherMeaning;

        private OtherMeaningModel inHolderOtherMeaning;

        OtherDetail(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            otherViewModel = ViewModelProviders.of((FragmentActivity) context).get(OtherViewModel.class);
            backFrameOtherMeaning.setOnClickListener(this);
            btnEditOtherDetail.setOnClickListener(this);
            frontFrameOtherMeaning.requestLayout();

        }

        void bind(OtherMeaningModel otherMeaning) {
            textOtherDetail.setText(otherMeaning.Other);
            inHolderOtherMeaning = otherMeaning;
        }


        @Override
        public void onClick(View v) {
            int id = inHolderOtherMeaning.idOther;
            String meaning = inHolderOtherMeaning.getOther();
            int idOwner = inHolderOtherMeaning.getIdOwner();
            switch (v.getId()) {
                case R.id.back_frame_other_meaning:
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // hold item before removed for 0.5 sec
                        removeItemAt(getAdapterPosition());
                        otherViewModel.deleteOtherMeaning(new OtherMeaningModel(id, meaning, idOwner));
                    }, 300);
                    break;
                case R.id.btn_edit_other_detail:
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FDialog dialog = FDialog.Instance();
                    dialog.setEditListener(this, inHolderOtherMeaning.getOther());
                    dialog.show(fragmentManager, "dialog");
                    swipeOtherMeaning.close(true);
                default:
                    break;

            }
        }

        @Override
        public void onfinishedDialog(String text) {
            otherViewModel.updateOtherMeaning(new OtherMeaningModel(inHolderOtherMeaning.getIdOther(), text, inHolderOtherMeaning.getIdOwner()));
        }
    }
}
