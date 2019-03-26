package com.daya.dictio.view.recyclerview_adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
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
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.SENDER;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
import com.google.android.material.snackbar.Snackbar;
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

import static com.google.android.material.snackbar.Snackbar.make;

public class WordIndAdapter extends RecyclerView.Adapter<WordIndAdapter.WordHolder> implements FastScroller.SectionIndexer {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final SENDER sender;
    private List<DictIndonesia> listKamus;
    private Context context;

    public WordIndAdapter(SENDER kode) {
        ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        viewBinderHelper.setOpenOnlyOne(true);
        sender = kode;
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

        final View viewForSnackbar;
        @BindView(R.id.front_frame)
        CoordinatorLayout frontFrame;
        private final WordViewModel wordViewModel;
        private final HistoryViewModel historyViewModel;
        private final FavoriteViewModel favoriteViewModel;
        @BindView(R.id.copy_content_main)
        ImageButton btnCopy;
        private DictIndonesia inHolderDict;

        WordHolder(View view) {
            super(view);
            this.viewForSnackbar = view;
            ButterKnife.bind(this, view);

            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);
            historyViewModel = ViewModelProviders.of((FragmentActivity) context).get(HistoryViewModel.class);

            frontFrame.setOnClickListener(this);
            backFrame.setOnClickListener(this);
            btnCopy.setOnClickListener(this);
        }

        void bindTo(DictIndonesia dictIndonesia) {
            kataFrontFrame.setText(Html.fromHtml(dictIndonesia.getWord()));
            penjelasanFrontFrame.setText(Html.fromHtml(dictIndonesia.getMeaning()));
            this.inHolderDict = dictIndonesia;
        }

        void navigationFronLayout(View v, int id, String word, String meaning) {
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
                    make(itemView, word + " added to favorite", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            String word = inHolderDict.getWord();
            String meaning = inHolderDict.getMeaning();
            int id = inHolderDict.getIdIndo();
            Snackbar snackbar;
            switch (v.getId()) {
                case R.id.front_frame:
                    navigationFronLayout(v, id, word, meaning);

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
