package com.comikz.di.module;

import com.comikz.di.ActivityScope;
import com.comikz.interactors.GetMangaPage;
import com.comikz.interactors.GetMangaPageInteractor;
import com.comikz.interactors.GetMangas;
import com.comikz.interactors.GetMangaInfo;
import com.comikz.interactors.GetMangaInfoInteractor;
import com.comikz.interactors.GetMangasInteractor;
import com.comikz.interactors.SearchMangas;
import com.comikz.interactors.SearchMangasInteractor;
import com.comikz.repositories.MangaRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    @ActivityScope
    GetMangaPage provideGetMangaPages(MangaRepository mangaRepository) {
        return new GetMangaPageInteractor(mangaRepository);
    }

    @Provides
    @ActivityScope
    SearchMangas provideSearchMangas(MangaRepository mangaRepository) {
        return new SearchMangasInteractor(mangaRepository);
    }

    @Provides
    @ActivityScope
    GetMangas provideGetMangas(MangaRepository mangaRepository) {
        return new GetMangasInteractor(mangaRepository);
    }

    @Provides
    @ActivityScope
    GetMangaInfo provideGetMangaInfo(MangaRepository mangaRepository) {
        return new GetMangaInfoInteractor(mangaRepository);
    }

}
