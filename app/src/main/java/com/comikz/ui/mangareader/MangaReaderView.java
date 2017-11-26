package com.comikz.ui.mangareader;

import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.ui.View;

/**
 * Created by Agustin Tomas Larghi on 19/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface MangaReaderView extends View {
    void onShowError(String error);
    void onFirstMangaPageFetched(MangaChapter mangaChapter, MangaPage mangaPage);
}
