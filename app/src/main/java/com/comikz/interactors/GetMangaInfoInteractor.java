package com.comikz.interactors;

import com.comikz.R;
import com.comikz.MangaApplication;
import com.comikz.model.MangaDetail;
import com.comikz.repositories.MangaRepository;
import com.comikz.utils.rx.SourceSubscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class GetMangaInfoInteractor implements GetMangaInfo {
    private final MangaRepository mRepository;

    public GetMangaInfoInteractor(MangaRepository mangaRepository) {
        mRepository = mangaRepository;
    }

    @Override
    public void executeFromApi(String id, String name, final Callback callback) {
        mRepository.getMangaInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SourceSubscriber<MangaDetail>() {
                @Override
                protected void onResultNext(MangaDetail mangaDetail) {
                    callback.onMangaInfoFetched(mangaDetail);
                }

                @Override
                protected void onResultError(Throwable e) {
                    callback.onErrorFetchingMangaInfo(MangaApplication.getInstance().getString(R.string.default_generic_error));
                }
            });
    }
}
