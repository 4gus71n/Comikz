package com.comikz.interactors;

import com.comikz.model.MangaDetail;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface GetMangaInfo {

    interface Callback {
        void onMangaInfoFetched(MangaDetail vox);
        void onErrorFetchingMangaInfo(String error);
    }

    void executeFromApi(String id, String name, Callback callback);
}
