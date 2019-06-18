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
import com.daya.dictio.dictio;
import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.repo.Appreferen;
import com.daya.dictio.view.itemSelection.Details;
import com.daya.dictio.view.layout_thing.OnItemClickListener;
import com.daya.dictio.viewmodel.FavoriteViewModel;
import com.daya.dictio.viewmodel.HistoryViewModel;
import com.daya.dictio.viewmodel.WordViewModel;
import com.github.zagum.switchicon.SwitchIconView;
import com.l4digital.fastscroll.FastScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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
    private Context context;

    public WordIndAdapterPaged() {
        super(diffcallBack);
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


    public class WordHolder extends RecyclerView.ViewHolder{
        final View viewForSnackbar;
        final Details details;
        final Appreferen appreferen;
        OnItemClickListener onItemClickListener;

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
        DictIndonesia inHolderDict;
        HistoryViewModel mHistoryViewModel;
        WordViewModel mWordViewModel;
        FavoriteViewModel mFavoriteViewModel;

        WordHolder(View view) {
            super(view);
            this.viewForSnackbar = view;
            ButterKnife.bind(this, view);
            mHistoryViewModel = ViewModelProviders.of((FragmentActivity) context).get(HistoryViewModel.class);
            mWordViewModel = ViewModelProviders.of((FragmentActivity) context).get(WordViewModel.class);
            details = new Details();
            appreferen = new Appreferen(context);


            frontFrame.setOnClickListener(v -> {
                mHistoryViewModel.addHistory(new HistoryModel(inHolderDict.getIdIndo()));
                mWordViewModel.setSendToDetail(inHolderDict);
                Navigation.findNavController(v).navigate(R.id.action_FSearch_layout_to_fDetail_ragment);
            });
            backFrame.setOnClickListener(v -> {

                mFavoriteViewModel.addFavorite(new FavoritModel(inHolderDict.getIdIndo()));
                dictio.showtoast(v.getContext(),inHolderDict.getWord()+" "+v.getContext().getString(R.string.added_to_favvorite));

            });
            btnCopy.setOnClickListener(v -> {
                Spanned stringe = Html.fromHtml(inHolderDict.getWord() + "\n\n" + inHolderDict.getMeaning());
                ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", stringe.toString());
                clipboard.setPrimaryClip(clip);
                dictio.showtoast(v.getContext(),inHolderDict.getWord()+" "+v.getContext().getString(R.string.copied));
            });
        }

        void bindTo(DictIndonesia dictIndonesia) {
            inHolderDict = dictIndonesia;
            kataFrontFrame.setText(Html.fromHtml(dictIndonesia.getWord()));
            penjelasanFrontFrame.setText(Html.fromHtml(dictIndonesia.getMeaning()));
            Timber.i(dictIndonesia.getWord());
        }

        void clear() {
            kataFrontFrame.invalidate();
            penjelasanFrontFrame.invalidate();
        }

      /*  @Override
        public void onClick(View v) {
            //tombol manapun yang diclik akan isi onadapterclicked dengan view, data,dan posisi
            //onItemClickListener.dashboardClicked(v, inHolderDict, getAdapterPosition());

            int id = inHolderDict.getIdIndo();
            String word = inHolderDict.getWord();
            String meaning = inHolderDict.getMeaning();
            switch (v.getId()) {
                case R.id.front_frame:

                    break;
                case R.id.back_frame: break;

                case R.id.copy_content_main:


                default:
                    break;
            }

        }*/

        public Details getDetails() {
            return details;
        }

    }



}