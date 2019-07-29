package com.daya.dictio.view.recyclerview_adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.dictio;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.join.HistoryJoinDict;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.WordHolderHistory> implements FastScroller.SectionIndexer {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<HistoryJoinDict> listHistoryJoin;
    private static OnItemClickListener mOnitemClickListener;


    @NonNull
    @Override
    public WordHolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history, parent, false);
        viewBinderHelper.setOpenOnlyOne(true);
        return new WordHolderHistory(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolderHistory holder, int position) {
        HistoryJoinDict current = listHistoryJoin.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(current.getId()));

        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return (listHistoryJoin != null) ? listHistoryJoin.size() : 0;
    }

    public void setHistory(List<HistoryJoinDict> dict) {
        this.listHistoryJoin = dict;
        notifyDataSetChanged();
    }

    private String fapatkanItem(int position) {
        return listHistoryJoin.get(position).getWord();
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }

    private void removeItemAt(int index) {
        if (index != -1 && index < listHistoryJoin.size()) {
            listHistoryJoin.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }

    public void setOnitemClickListener(OnItemClickListener onitemClickListener) {
        mOnitemClickListener = onitemClickListener;
    }


    public class WordHolderHistory extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final HistoryViewModel historyViewModel;

        @BindView(R.id.back_frame_history)
        RelativeLayout backFrameHistory;

        @BindView(R.id.switch_icon_delete_history)
        SwitchIconView switchIconDeleteHistory;

        @BindView(R.id.text_kata_history)
        TextView recyclerKataHistory;

        @BindView(R.id.text_penjelasan_history)
        TextView recyclerPenjelasanHistory;

        @BindView(R.id.front_frame_histo)
        CoordinatorLayout frontFrameHisto;

        @BindView(R.id.swipe_history)
        SwipeRevealLayout swipeRevealLayout;

        HistoryJoinDict inHolderHisJoint;
        private OnItemClickListener onItemClickListener;

        WordHolderHistory(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            historyViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(HistoryViewModel.class);
            this.onItemClickListener =  HistoryAdapter.mOnitemClickListener;

            backFrameHistory.setOnClickListener(this);
            frontFrameHisto.setOnClickListener(this);
        }


        void bindTo(HistoryJoinDict historyJoinDict) {
            recyclerKataHistory.setText(Html.fromHtml(historyJoinDict.getWord()));
            recyclerPenjelasanHistory.setText(Html.fromHtml(historyJoinDict.getMeaning()));
            inHolderHisJoint = historyJoinDict;
        }

        @Override
        public void onClick(View v) {
            int id = inHolderHisJoint.getId();
            int idOwner = inHolderHisJoint.getIdOwner();
            switch (v.getId()) {
                case R.id.back_frame_history:

                    //TODO change switch icon to standart item delete, so you can use move this into Fhistory instead
                    switchIconDeleteHistory.setIconEnabled(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // hold item before removed for 0.5 sec
                        removeItemAt(getAdapterPosition());
                        historyViewModel.deleteHistoryAt(new HistoryModel(id, idOwner));
                    }, 300);
                    dictio.showtoast(v.getContext(),v.getContext().getString(R.string.item_removed));
                    break;
                default:
                    break;

            }

            onItemClickListener.historyClicked(v, inHolderHisJoint, getAdapterPosition());
        }
    }
}
