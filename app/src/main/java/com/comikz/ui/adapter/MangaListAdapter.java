package com.comikz.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.comikz.R;
import com.comikz.model.Manga;
import com.comikz.ui.views.OnVoxItemViewClicked;
import com.comikz.ui.views.MangaItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> implements OnVoxItemViewClicked {
    private final LayoutInflater mInflater;
    private final Callback mCallback;
    private List<Manga> mItems;

    public MangaListAdapter(Context context, Callback callback) {
        mInflater = LayoutInflater.from(context);
        mCallback = callback;
        mItems = new ArrayList<>();
    }

    public void addMangas(List<Manga> mangas) {
        int prevSize = mItems.size();
        mItems.addAll(mangas);
        notifyItemRangeInserted(prevSize, mangas.size());
    }

    public void setMangas(List<Manga> mangas) {
        mItems.clear();
        mItems.addAll(mangas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((MangaItemView) mInflater.inflate(R.layout.view_manga_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.loadMangaItem(mItems.get(position), this);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemClicked(Manga manga, MangaItemView mangaItemView) {
        mCallback.onItemClicked(manga, mangaItemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MangaItemView mMangaItemView;

        public ViewHolder(MangaItemView itemView) {
            super(itemView);
            mMangaItemView = itemView;
            ButterKnife.bind(mMangaItemView, mMangaItemView);
        }

        public void loadMangaItem(Manga manga, OnVoxItemViewClicked callback) {
            mMangaItemView.loadMangaItem(manga, callback);
        }
    }

    public interface Callback {
        void onItemClicked(Manga manga, MangaItemView view);
    }
}
