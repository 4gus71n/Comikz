package com.comikz.ui.mangareader;

import android.os.Bundle;

import com.comikz.ui.Presenter;

/**
 * Created by Agustin Tomas Larghi on 19/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaReaderPresenter extends Presenter<MangaReaderView> {

    void onViewCreated(MangaReaderView view, Bundle args);

    void fetchFirstMangaPage();
}
