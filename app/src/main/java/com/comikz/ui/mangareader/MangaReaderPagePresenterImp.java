package com.comikz.ui.mangareader;

import android.content.Context;
import android.os.Bundle;

import com.comikz.interactors.GetMangaPage;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.ui.BasePresenter;
import com.comikz.utils.Constants;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaReaderPagePresenterImp extends BasePresenter implements MangaReaderPagePresenter,
        GetMangaPage.Callback {

    //region Variables declaration
    private final GetMangaPage mGetMangaPage;
    private MangaReaderPageView mView;

    private MangaChapter mMangaChapter;
    private int mPage;
    //endregion

    //region GetMangaPage.Callback implementation

    @Override
    public void onPagesFetched(MangaPage mangaPage) {
        if (isViewAttached(mView)) {
            mView.onPageFetched(mangaPage);
        }
    }

    @Override
    public void onErrorFetchingPages(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(error);
        }
    }

    //endregion

    //region Presenter lifecycle methods declaration
    public MangaReaderPagePresenterImp(Context context, GetMangaPage getMangaPage) {
        super(context);
        mGetMangaPage = getMangaPage;
    }

    @Override
    public void fetchPage() {
        mGetMangaPage.executeFromApi(this, mMangaChapter, mPage);
    }

    @Override
    public void onViewCreated(MangaReaderPageView view, Bundle bundle) {
        mView = view;

        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_CHAPTER)) {
            mMangaChapter = bundle.getParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER);
        }
        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_CHAPTER_PAGE_NUMBER)) {
            mPage = bundle.getInt(Constants.ARG_BUNDLE_MANGA_CHAPTER_PAGE_NUMBER);
        }
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        if (bundle != null) {
             bundle.putParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER, mMangaChapter);
            bundle.putInt(Constants.ARG_BUNDLE_MANGA_CHAPTER_PAGE_NUMBER, mPage);
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }
    //endregion
}
