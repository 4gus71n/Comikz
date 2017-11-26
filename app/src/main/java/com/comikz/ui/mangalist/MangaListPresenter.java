package com.comikz.ui.mangalist;

import com.comikz.ui.Presenter;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaListPresenter extends Presenter<MangaListView> {

    /**
     * Fetch the mangas from the directory page
     * @param page
     */
    void fetchMangas(int page);

    /**
     * Search mangas given a criteria
     * @param query
     */
    void searchMangas(String query);

    /**
     * Returns the searc results stored in the presenter
     */
    void getMangaSearchResults();

    /**
     * Returns the directory results stored in the presenter
     */
    void getMangaDirectoryResults();
}
