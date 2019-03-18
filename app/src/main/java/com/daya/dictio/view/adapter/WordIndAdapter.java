package com.daya.dictio.view.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.viewmodel.FavoriteViewModel;
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

public class WordIndAdapter extends RecyclerView.Adapter<WordIndAdapter.WordHolder> implements FastScroller.SectionIndexer {
    private final OnItemClickListener onItemClickListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private SENDER sender;
    private List<DictIndonesia> listKamus;
    private Context context;

    public WordIndAdapter(@NonNull OnItemClickListener onItemClickListener, SENDER kode) {
        ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        this.onItemClickListener = onItemClickListener;
        viewBinderHelper.setOpenOnlyOne(true);
        sender = kode;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        this.context = parent.getContext();
        return new WordHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        DictIndonesia current = listKamus.get(position);
        viewBinderHelper.bind(holder.swipperMain, String.valueOf(current.getIdIndo()));

        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return (listKamus != null) ? listKamus.size() : 0;
    }

    private String fapatkanItem(int position) {
        return listKamus.get(position).getWord();
    }

    public void setDict(List<DictIndonesia> dict) {
        this.listKamus = dict;
        notifyDataSetChanged();

    }

    public enum SENDER {
        DASHBOARD, HISTORY, SEARCH
    }

    @Override
    public CharSequence getSectionText(int position) {
        return String.valueOf(fapatkanItem(position).charAt(0));
    }

    public void clearData() {
        listKamus.clear();
        notifyDataSetChanged();

    }

    public class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.swipper)
        SwipeRevealLayout swipperMain;
        @BindView(R.id.back_frame)
        RelativeLayout backFrame;

        @BindView(R.id.recycler_kata)
        TextView kataFrontFrame;
        @BindView(R.id.recycler_penjelasan)
        TextView penjelasanFrontFrame;
        @BindView(R.id.switchIconView3)
        SwitchIconView iconView;

        /* @BindView(R.id.btn_back_frame)
         ImageButton btnBackFrame;*/
        @BindView(R.id.front_frame)
        CoordinatorLayout frontFrame;
        private WordViewModel wordViewModel;
        private OnItemClickListener onItemClickListener;

        private HistoryViewModel historyViewModel;
        private FavoriteViewModel favoriteViewModel;


        WordHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            this.onItemClickListener = onItemClickListener;
            ButterKnife.bind(this, view);

            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);
            historyViewModel = ViewModelProviders.of((FragmentActivity) context).get(HistoryViewModel.class);

            frontFrame.setOnClickListener(this);
            backFrame.setOnClickListener(this);


        }

        void bindTo(DictIndonesia dictIndonesia) {
            kataFrontFrame.setText(Html.fromHtml(dictIndonesia.getWord()));
            penjelasanFrontFrame.setText(Html.fromHtml(dictIndonesia.getMeaning()));

        }

        void navigationFronLayout(View v, String kata, String penjelasan) {

            NavController navigation = Navigation.findNavController(v);

            switch (sender) {
                case DASHBOARD:
                    navigation.navigate(R.id.fDetail);
                    break;

                case SEARCH:
                    historyViewModel.addHistory(new HistoryModel(kata, penjelasan));
                    navigation.navigate(R.id.fDetail);
                    break;

                case HISTORY:
                    navigation.navigate(R.id.fDetail);
                default:
                    break;
            }

        }

        @Override
        public void onClick(View v) {
            String word = listKamus.get(getAdapterPosition()).getWord();
            String meaning = listKamus.get(getAdapterPosition()).getMeaning();

            wordViewModel.setSendToDetail(new DictIndonesia(word, meaning));

            switch (v.getId()) {

                case R.id.front_frame:
                    navigationFronLayout(v, word, meaning);
                    break;

                case R.id.back_frame:
                    iconView.switchState(true);

                    break;
                default:
                    break;
            }

            onItemClickListener.itemclicked(getAdapterPosition());
        }
    }

}
