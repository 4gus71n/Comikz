package com.comikz.repositories;

import com.comikz.model.Manga;
import com.comikz.model.MangaDetail;
import com.comikz.model.MangaPage;
import com.comikz.utils.rx.DataSource;

import java.util.List;

import rx.Observable;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaRepository {

    Observable<DataSource<MangaPage>> getMangaPage(String manga, String volume, String chapter, int page);

    Observable<DataSource<List<Manga>>> getMangas(int page);

    Observable<DataSource<MangaDetail>> getMangaInfo(String id);

    Observable<DataSource<List<Manga>>> searchManga(String query);

}
