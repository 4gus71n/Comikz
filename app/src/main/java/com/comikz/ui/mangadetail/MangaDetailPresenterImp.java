package com.comikz.ui.mangadetail;

import android.content.Context;
import android.os.Bundle;

import com.comikz.R;
import com.comikz.interactors.GetMangaInfo;
import com.comikz.model.Manga;
import com.comikz.model.MangaDetail;
import com.comikz.ui.BasePresenter;
import com.comikz.utils.Constants;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaDetailPresenterImp extends BasePresenter implements MangaDetailPresenter, GetMangaInfo.Callback {

    //region Variables declaration
    private final GetMangaInfo mGetMangaInfo;
    private MangaDetailView mView;

    private MangaDetail mMangaDetail;
    private Manga mManga;
    //endregion

    //region Presenter lifecycle methods declaration
    public MangaDetailPresenterImp(Context context, GetMangaInfo getMangaInfo) {
        super(context);
        mGetMangaInfo = getMangaInfo;
    }

    @Override
    public void onViewCreated(MangaDetailView view, Bundle bundle) {
        mView = view;
        mManga = bundle.getParcelable(Constants.ARG_BUNDLE_MANGA);
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        bundle.putParcelable(Constants.ARG_BUNDLE_MANGA, mManga);
        bundle.putParcelable(Constants.ARG_BUNDLE_MANGA_DETAIL, mMangaDetail);
    }

    @Override
    public void onDestroyView() {
        mView = null;
    }

    @Override
    public void fetchMangaDetails() {
        mGetMangaInfo.executeFromApi(mManga.getId(), mManga.getTitle(), this);
    }

    @Override
    public String getManagaTitle() {
        return mManga.getTitle();
    }

    @Override
    public String getMangaId() {
        return mManga.getId();
    }

    @Override
    public String getMangaCoverImage() {
        return mManga.getImage();
    }
    //endregion

    //region GetMangaInfo.Callback  implementation
    @Override
    public void onMangaInfoFetched(MangaDetail mangaDetail) {
        mMangaDetail = mangaDetail;
        if (isViewAttached(mView)) {
            mView.onMangaDetailFetched(mangaDetail);
        }
    }

    @Override
    public void onErrorFetchingMangaInfo(String error) {
        if (isViewAttached(mView)) {
            mView.onShowError(getString(R.string.default_generic_error));
        }
    }
    //endregion
}
