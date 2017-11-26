package com.comikz.ui.mangareader;

import android.content.Context;
import android.os.Bundle;

import com.comikz.R;
import com.comikz.interactors.GetMangaPage;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.ui.BasePresenter;
import com.comikz.utils.Constants;

/**
 * Created by Agustin Tomas Larghi on 20/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaReaderPresenterImp extends BasePresenter implements MangaReaderPresenter,
        GetMangaPage.Callback {

    //region Variables declaration
    private final GetMangaPage mGetMangaPage;

    private MangaChapter mChapter;
    private MangaReaderView mView;
    //endregion

    //region GetMangaPage.Callback implementation

    @Override
    public void onPagesFetched(MangaPage mangaPage) {
        if (isViewAttached(mView)) {
            mView.onFirstMangaPageFetched(mChapter, mangaPage);
        }
    }

    @Override
    public void onErrorFetchingPages(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(getString(R.string.default_generic_error));
        }
    }

    //endregion

    public MangaReaderPresenterImp(Context context, GetMangaPage getMangaPage) {
        super(context);
        mGetMangaPage = getMangaPage;
    }


    @Override
    public void fetchFirstMangaPage() {
        mGetMangaPage.executeFromApi(this, mChapter, 1);
    }

    @Override
    public void onViewCreated(MangaReaderView view, Bundle bundle) {
        mView = view;

        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_CHAPTER)) {
            mChapter = bundle.getParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER);
        }
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_CHAPTER)) {
            mChapter = bundle.getParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER);
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }
}
