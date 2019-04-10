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
import com.daya.dictio.repo.Appreferen;
import com.daya.dictio.view.itemSelection.Details;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
import com.google.android.material.snackbar.Snackbar;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WordIndAdapterPaged extends PagedListAdapter<DictIndonesia, WordIndAdapterPaged.WordHolder> implements FastScroller.SectionIndexer {
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
        viewBinderHelper.setOpenOnlyOne(true);
        return new WordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        DictIndonesia current = getItem(position);
        if (current != null) {
            holder.bindTo(current);
            viewBinderHelper.bind(holder.swipperMain, String.valueOf(current.getIdIndo()));
        } else {
            holder.clear();
        }


    }


    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public CharSequence getSectionText(int position) {
        return null;
    }


    public class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View viewForSnackbar;
        private final WordViewModel wordViewModel;
        private final HistoryViewModel historyViewModel;
        final Details details;
        final Appreferen appreferen;

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
        private final FavoriteViewModel favoriteViewModel;
        DictIndonesia inHolderDict;

        WordHolder(View view) {
            super(view);
            this.viewForSnackbar = view;
            ButterKnife.bind(this, view);

            wordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            historyViewModel = ViewModelProviders.of((FragmentActivity) context).get(HistoryViewModel.class);
            favoriteViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavoriteViewModel.class);
            frontFrame.setOnClickListener(this);
            backFrame.setOnClickListener(this);
            btnCopy.setOnClickListener(this);

            details = new Details();
            appreferen = new Appreferen(context);


        }

        void bindTo(DictIndonesia dictIndonesia) {
            inHolderDict = dictIndonesia;
            kataFrontFrame.setText(Html.fromHtml(dictIndonesia.getWord()));
            penjelasanFrontFrame.setText(Html.fromHtml(dictIndonesia.getMeaning()));
        }

        void clear() {
            kataFrontFrame.invalidate();
            penjelasanFrontFrame.invalidate();
        }

        @Override
        public void onClick(View v) {
            int id = inHolderDict.getIdIndo();
            String word = inHolderDict.getWord();
            String meaning = inHolderDict.getMeaning();

            Snackbar snackbar;
            switch (v.getId()) {
                case R.id.front_frame:
                    navigationFronLayout(v, id, word, meaning);
                    break;
                case R.id.back_frame:
                    favoriteViewModel.addFavorite(new FavoritModel(id));
                    snackbar = Snackbar.make(viewForSnackbar, word + itemView.getContext().getString(R.string.added_to_favvorite), Snackbar.LENGTH_LONG).setAction("OK", v1 -> {
                    });
                    snackbar.show();
                    break;

                case R.id.copy_content_main:
                    Spanned stringe = Html.fromHtml(word + "\n\n" + meaning);
                    ClipboardManager clipboard = (ClipboardManager) viewForSnackbar.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", stringe.toString());
                    clipboard.setPrimaryClip(clip);
                    snackbar = Snackbar.make(viewForSnackbar, inHolderDict.getWord() + itemView.getContext().getString(R.string.copied), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                default:
                    break;
            }
        }

        void navigationFronLayout(View v, int id, String word, String meaning) {
            NavController navigation = Navigation.findNavController(v);
            wordViewModel.setSendToDetail(new DictIndonesia(id, word, meaning));
            switch (sender) {
                case DASHBOARD:
                    historyViewModel.addHistory(new HistoryModel(id));
                    navigation.navigate(R.id.action_navigation_home_to_fDetail_ragment2);
                    break;
                case SEARCH:
                    historyViewModel.addHistory(new HistoryModel(id));
                    navigation.navigate(R.id.action_FSearch_layout_to_fDetail_ragment);
                    break;
                default:
                    break;
            }
        }

        public Details getDetails() {
            return details;
        }

    }

}