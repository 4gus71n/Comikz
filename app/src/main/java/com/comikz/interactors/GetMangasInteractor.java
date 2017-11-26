package com.comikz.interactors;

import com.comikz.model.Manga;
import com.comikz.repositories.MangaRepository;
import com.comikz.utils.rx.SourceSubscriber;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class GetMangasInteractor implements GetMangas {
    private final MangaRepository mMangaRepository;

    public GetMangasInteractor(MangaRepository mangaRepository) {
        mMangaRepository = mangaRepository;
    }

    @Override
    public void executeFromApi(final Callback callback, int page) {
        mMangaRepository.getMangas(page)
            .subscribe(new SourceSubscriber<List<Manga>>() {
                @Override
                protected void onSuccessNext(List<Manga> mangas) {
                    callback.onMangasFetched(mangas);
                }

                @Override
                protected void onResultError(Throwable e) {
                    callback.onMangasFetchFailed(e.getLocalizedMessage());
                }
            });


    }
}
