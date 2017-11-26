package com.comikz.interactors;

import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;

/**
 * Created by Agustin Tomas Larghi on 25/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface GetMangaPage {

    interface Callback {
        void onPagesFetched(MangaPage mangaPageList);
        void onErrorFetchingPages(String error);
    }

    void executeFromApi(Callback callback, MangaChapter mangaChapter, int page);

}
