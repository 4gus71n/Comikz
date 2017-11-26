package com.comikz.ui.mangareader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.comikz.model.MangaChapter;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class MangaReaderAdapter extends FragmentStatePagerAdapter {

    private final int mTotalPages;
    private final MangaChapter mMangaChapter;

    public MangaReaderAdapter(FragmentManager fm, MangaChapter mangaChapter, int totalPages) {
        super(fm);
        mTotalPages = totalPages;
        mMangaChapter = mangaChapter;
    }

    @Override
    public Fragment getItem(int position) {
        return MangaReaderPageFragment.newInstance(mMangaChapter, position + 1);
    }

    @Override
    public int getCount() {
        return mTotalPages;
    }
}
