package com.comikz.di.module;

import android.content.Context;

import com.comikz.di.ActivityScope;
import com.comikz.interactors.GetMangaInfo;
import com.comikz.interactors.GetMangaPage;
import com.comikz.interactors.GetMangas;
import com.comikz.interactors.SearchMangas;
import com.comikz.ui.mangadetail.MangaDetailPresenter;
import com.comikz.ui.mangadetail.MangaDetailPresenterImp;
import com.comikz.ui.mangalist.MangaListPresenter;
import com.comikz.ui.mangalist.MangaListPresenterImp;
import com.comikz.ui.mangareader.MangaReaderPagePresenter;
import com.comikz.ui.mangareader.MangaReaderPagePresenterImp;
import com.comikz.ui.mangareader.MangaReaderPresenter;
import com.comikz.ui.mangareader.MangaReaderPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {


    @ActivityScope
    @Provides
    MangaReaderPagePresenter provideMangaReaderPagePresenter(Context context, GetMangaPage getMangaPage) {
        return new MangaReaderPagePresenterImp(context, getMangaPage);
    }

    @ActivityScope
    @Provides
    MangaReaderPresenter provideMangaReaderPresenter(Context context, GetMangaPage getMangaPage) {
        return new MangaReaderPresenterImp(context, getMangaPage);
    }

    @ActivityScope
    @Provides
    MangaListPresenter provideMainPresenter(Context context, GetMangas getMangas, SearchMangas searchMangas) {
        return new MangaListPresenterImp(context, getMangas, searchMangas);
    }

    @ActivityScope
    @Provides
    MangaDetailPresenter provideVoxDetailPresenter(Context context, GetMangaInfo getMangaInfo) {
        return new MangaDetailPresenterImp(context, getMangaInfo);
    }


}
