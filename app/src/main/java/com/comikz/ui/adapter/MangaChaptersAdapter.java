package com.comikz.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.comikz.R;
import com.comikz.model.MangaChapter;
import com.comikz.ui.views.MangaChapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 4/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaChaptersAdapter extends RecyclerView.Adapter<MangaChaptersAdapter.ViewHolder> implements MangaChapterView.Callback{

    //region Variables declaration
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final Callback mCallback;
    private List<MangaChapter> mMangaChapters = new ArrayList<>();
    //endregion

    //region Adapter lifecycle methods declaration
    public MangaChaptersAdapter(Context context, Callback callback) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCallback = callback;
    }

    public void addMangaChapters(List<MangaChapter> mangaChapters) {
        mMangaChapters = mangaChapters;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((MangaChapterView) mInflater.inflate(R.layout.view_manga_chapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MangaChapter mangaChapter = mMangaChapters.get(position);
        holder.setMangaChapter(mangaChapter, this);
    }

    @Override
    public int getItemCount() {
        return mMangaChapters.size();
    }
    //endregion

    //region MangaChapterView.Callback implementation
    @Override
    public void onMangaChapterClicked(MangaChapter mangaChapter) {
        mCallback.onMangaChapterClicked(mangaChapter);
    }
    //endregion

    //region Callback to fragment interface declaration
    public interface Callback {
        void onMangaChapterClicked(MangaChapter mangaChapter);
    }
    //endregion

    //region ViewHolder declaration
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MangaChapterView mMangaChapterView;

        public ViewHolder(MangaChapterView itemView) {
            super(itemView);
            mMangaChapterView = itemView;
            ButterKnife.bind(mMangaChapterView, mMangaChapterView);
        }

        public void setMangaChapter(MangaChapter mangaChapter, MangaChapterView.Callback callback) {
            mMangaChapterView.updateView(mangaChapter, callback);
        }
    }
    //endregion
}
