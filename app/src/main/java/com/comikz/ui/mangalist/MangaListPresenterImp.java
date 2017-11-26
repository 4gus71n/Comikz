package com.comikz.ui.mangalist;

import android.content.Context;
import android.os.Bundle;

import com.comikz.interactors.GetMangas;
import com.comikz.interactors.SearchMangas;
import com.comikz.model.Manga;
import com.comikz.ui.BasePresenter;
import com.comikz.utils.ArraySetList;
import com.comikz.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaListPresenterImp extends BasePresenter implements MangaListPresenter, GetMangas.Callback,
        SearchMangas.Callback {

    //region Variable declarations
    private final GetMangas mGetMangas;
    private final SearchMangas mSearchMangas;

    private MangaListView mView;
    private ArraySetList<Manga> mMangaList, mSearchMangaList;
    //endregion

    //region SearchMangas.Callback implementation

    @Override
    public void onSearchMangasResult(List<Manga> resultMangas) {
        mSearchMangaList.clear();
        mSearchMangaList.addAll(resultMangas);

        if (isViewAttached(mView)) {
            mView.onSetMangasResults(mSearchMangaList);
        }
    }

    @Override
    public void onSearchFailed(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(error);
        }
    }

    //endregion

    //region GetMangas.Callback implementation
    @Override
    public void onMangasFetched(List<Manga> mangas) {
        mMangaList.addAll(mangas);

        if (isViewAttached(mView)) {
            mView.onAddMangasResults(mMangaList);
        }
    }

    @Override
    public void onMangasFetchFailed(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(error);
        }
    }
    //endregion

    //region Presenter lifecycle methods declaration
    public MangaListPresenterImp(Context context, GetMangas getMangas, SearchMangas searchMangas) {
        super(context);
        mGetMangas = getMangas;
        mSearchMangas = searchMangas;
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        bundle.putParcelableArrayList(Constants.ARG_BUNDLE_MANGA_LIST, mMangaList);
        bundle.putParcelableArrayList(Constants.ARG_BUNDLE_MANGA_SEARCH_LIST, mSearchMangaList);
    }

    @Override
    public void onViewCreated(MangaListView view, Bundle bundle) {
        mView = view;

        mMangaList = new ArraySetList<>();
        mSearchMangaList = new ArraySetList<>();

        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_LIST)) {
            ArrayList<Manga> arrayList = bundle.getParcelableArrayList(Constants.ARG_BUNDLE_MANGA_LIST);
            mMangaList.addAll(arrayList);
        }

        if (bundle.containsKey(Constants.ARG_BUNDLE_MANGA_SEARCH_LIST)) {
            ArrayList<Manga> arrayList = bundle.getParcelableArrayList(Constants.ARG_BUNDLE_MANGA_SEARCH_LIST);
            mSearchMangaList.addAll(arrayList);
        }
    }

    @Override
    public void fetchMangas(int page) {
        mGetMangas.executeFromApi(this, page);
    }

    @Override
    public void searchMangas(String query) {
        mSearchMangas.executeFromApi(this, query);
    }

    @Override
    public void getMangaSearchResults() {
        if (!mSearchMangaList.isEmpty()) {
            if (isViewAttached(mView)) {
                mView.onSetMangasResults(mSearchMangaList);
            }
        }
    }

    @Override
    public void getMangaDirectoryResults() {
        if (!mMangaList.isEmpty()) {
            if (isViewAttached(mView)) {
                mView.onSetMangasResults(mMangaList);
            }
        }
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }
    //endregion
}
