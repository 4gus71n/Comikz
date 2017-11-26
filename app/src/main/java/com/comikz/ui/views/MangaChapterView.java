package com.comikz.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comikz.R;
import com.comikz.model.MangaChapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Agustin Tomas Larghi on 4/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaChapterView extends LinearLayout {

    @BindView(R.id.view_manga_chapter_title)
    TextView mTitleTextView;

    private MangaChapter mItem;

    private Callback mCallback;

    public MangaChapterView(Context context) {
        super(context);
        onInflateView(context, null);
    }

    public MangaChapterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onInflateView(context, attrs);
    }

    public MangaChapterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInflateView(context, attrs);
    }

    public MangaChapterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onInflateView(context, attrs);
    }

    private void onInflateView(Context context, AttributeSet attrs) {

    }

    public void updateView(MangaChapter mangaChapter, Callback callback) {
        mItem = mangaChapter;
        mCallback = callback;

        mTitleTextView.setText(mItem.getTitle());
    }

    @OnClick(R.id.view_manga_chapter_container)
    void onWholeLayoutClicked() {
        mCallback.onMangaChapterClicked(mItem);
    }

    public interface Callback {
        void onMangaChapterClicked(MangaChapter mangaChapter);
    }
}
