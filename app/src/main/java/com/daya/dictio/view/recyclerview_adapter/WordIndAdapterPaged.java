package com.daya.dictio.view.recyclerview_adapter;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daya.dictio.R;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.SENDER;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class WordIndAdapterPaged extends PagedListAdapter<DictIndonesia, WordIndAdapterPaged.WordHolder> {
    private static final DiffUtil.ItemCallback<DictIndonesia> diffcallBack = new DiffUtil.ItemCallback<DictIndonesia>() {
        @Override
        public boolean areItemsTheSame(@NonNull DictIndonesia oldItem, @NonNull DictIndonesia newItem) {
            return oldItem.getIdIndo() == newItem.getIdIndo();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DictIndonesia oldItem, @NonNull DictIndonesia newItem) {
            return oldItem == newItem;
        }
    };
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final SENDER sender;
    private Context context;

    public WordIndAdapterPaged(SENDER sender) {
        super(diffcallBack);
        this.sender = sender;
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        this.context = parent.getContext();
        return new WordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        holder.bindTo(getItem(position));
        Timber.i("onBindViewHolder: %s", getItem(position).getWord());
    }

    public class WordHolder extends RecyclerView.ViewHolder {
        final View viewForSnackbar;
        private final WordViewModel wordViewModel;
        private final HistoryViewModel historyViewModel;
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
        @BindView(R.id.copy_content_main)
        ImageButton btnCopy;
        @BindView(R.id.front_frame)
        CoordinatorLayout frontFrame;

        WordHolder(View view) {
            super(view);
            this.viewForSnackbar = view;
            ButterKnife.bind(this, view);

            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            historyViewModel = ViewModelProviders.of((FragmentActivity) context).get(HistoryViewModel.class);
         /*   frontFrame.setOnClickListener(this);
            backFrame.setOnClickListener(this);
            btnCopy.setOnClickListener(this);*/

        }

        void bindTo(DictIndonesia dictIndonesia) {
            kataFrontFrame.setText(Html.fromHtml(dictIndonesia.getWord()));
            penjelasanFrontFrame.setText(Html.fromHtml(dictIndonesia.getMeaning()));
        }

       /* void navigationFronLayout(View v, int id,String word,String meaning) {
            NavController navigation = Navigation.findNavController(v);
            wordViewModel.setSendToDetail(new DictIndonesia(id, word, meaning));
            switch (sender) {
                case DASHBOARD:
                    historyViewModel.addHistory(new HistoryModel(id));
                    navigation.navigate(R.id.fDetail);
                    break;

                case SEARCH:
                    historyViewModel.addHistory(new HistoryModel(id));
                    navigation.navigate(R.id.fDetail);
                    make(itemView, word+" added to favorite", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;

        }

        @Override
        public void onClick(View v) {

            Snackbar snackbar;
            switch (v.getId()) {
                case R.id.front_frame:
                    navigationFronLayout(v,id,word,meaning);

                    break;
                case R.id.back_frame:
                    iconView.switchState(true);
                    favoriteViewModel.addFavorite(new FavoritModel(id));
                    snackbar = Snackbar.make(viewForSnackbar, word + " added to favorite", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;

                case R.id.copy_content_main:
                    Spanned stringe = Html.fromHtml(word + "\n\n" + meaning);
                    ClipboardManager clipboard = (ClipboardManager) viewForSnackbar.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", stringe.toString());
                    clipboard.setPrimaryClip(clip);
                    snackbar = Snackbar.make(viewForSnackbar, listKamus.get(getAdapterPosition()).getWord() + " copied", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                default:
                    break;
            }

        }
}
    }
*/

    }
}