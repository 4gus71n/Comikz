package com.comikz.interactors;

import com.comikz.model.Manga;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 5/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface SearchMangas {

    public interface Callback {
        void onSearchMangasResult(List<Manga> resultMangas);
        void onSearchFailed(String error);
    }

    void executeFromApi(Callback callback, String query);

}
