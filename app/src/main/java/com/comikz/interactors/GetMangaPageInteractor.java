package com.comikz.interactors;

import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.repositories.MangaRepository;
import com.comikz.utils.rx.SourceSubscriber;

/**
 * Created by Agustin Tomas Larghi on 25/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class GetMangaPageInteractor implements GetMangaPage {
    private final MangaRepository mMangaRepository;

    public GetMangaPageInteractor(MangaRepository mangaRepository) {
        mMangaRepository = mangaRepository;
    }

    @Override
    public void executeFromApi(final Callback callback, MangaChapter mangaChapter, int page) {
        mMangaRepository.getMangaPage(mangaChapter.getMangaPermalink(), mangaChapter.getVolume(),
                mangaChapter.getChapter(), page)
                .subscribe(new SourceSubscriber<MangaPage>() {
                    @Override
                    protected void onResultError(Throwable e) {
                        callback.onErrorFetchingPages(e.getLocalizedMessage());
                    }

                    @Override
                    protected void onResultNext(MangaPage mangaPage) {
                        callback.onPagesFetched(mangaPage);
                    }
                });
    }

}
