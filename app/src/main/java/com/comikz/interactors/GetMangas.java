package com.comikz.interactors;

import com.comikz.model.Manga;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface GetMangas {

    interface Callback {
        void onMangasFetched(List<Manga> mangas);
        void onMangasFetchFailed(String error);
    }

    void executeFromApi(Callback callback, int page);

}
