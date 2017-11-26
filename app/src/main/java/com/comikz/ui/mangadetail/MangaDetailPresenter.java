package com.comikz.ui.mangadetail;

import com.comikz.ui.Presenter;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaDetailPresenter extends Presenter<MangaDetailView>{

    void fetchMangaDetails();

    String getManagaTitle();

    String getMangaId();

    String getMangaCoverImage();
}
