package com.daya.dictio.view.recyclerview_adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.viewmodel.OtherViewModel;
import com.github.zagum.switchicon.SwitchIconView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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

    public class OtherDetail extends RecyclerView.ViewHolder implements View.OnClickListener {
        final OtherViewModel otherViewModel;
        @BindView(R.id.text_other_detail)
        TextView textOtherDetail;
        @BindView(R.id.swipe_other_meaning)
        SwipeRevealLayout swipeOtherMeaning;
        @BindView(R.id.switch_icon_delete_Other)
        SwitchIconView switchIconDelete;
        @BindView(R.id.back_frame_other_meaning)
        RelativeLayout backFrameOtherMeaning;
        @BindView(R.id.front_frame_other_meaning)
        LinearLayout frontFrameOtherMeaning;
        private OtherMeaningModel inHolderOtherMeaning;

        OtherDetail(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            otherViewModel = ViewModelProviders.of((FragmentActivity) context).get(OtherViewModel.class);


            backFrameOtherMeaning.setOnClickListener(this);
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
                    switchIconDelete.switchState();
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // hold item before removed for 0.5 sec
                        removeItemAt(getAdapterPosition());
                        otherViewModel.deleteOtherMeaning(new OtherMeaningModel(id, meaning, idOwner));


                    }, 300);


                    break;
                default:
                    break;

            }
        }
    }
}
