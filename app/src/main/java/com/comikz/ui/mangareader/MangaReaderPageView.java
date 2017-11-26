package com.comikz.ui.mangareader;

import com.comikz.model.MangaPage;
import com.comikz.ui.View;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaReaderPageView extends View {

    void onPageFetched(MangaPage mangaPage);

    void onShowError(String error);

}
