package com.comikz.ui.mangalist;

import com.comikz.model.Manga;
import com.comikz.ui.View;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
interface MangaListView extends View {

    void onAddMangasResults(List<Manga> mangas);

    void onShowError(String error);

    void onSetMangasResults(List<Manga> mSearchMangaList);
}
