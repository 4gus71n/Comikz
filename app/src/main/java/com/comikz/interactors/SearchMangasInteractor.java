package com.comikz.interactors;

import com.comikz.model.Manga;
import com.comikz.repositories.MangaRepository;
import com.comikz.utils.rx.SourceSubscriber;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 5/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class SearchMangasInteractor implements SearchMangas {

    private final MangaRepository mRepository;

    public SearchMangasInteractor(MangaRepository mangaRepository) {
        mRepository = mangaRepository;
    }

    @Override
    public void executeFromApi(final Callback callback, String query) {
        mRepository.searchManga(query)
            .subscribe(new SourceSubscriber<List<Manga>>() {
            @Override
            protected void onResultError(Throwable e) {
                callback.onSearchFailed(e.getLocalizedMessage());
            }

            @Override
            protected void onResultNext(List<Manga> mangas) {
                callback.onSearchMangasResult(mangas);
            }
        });
    }
}
