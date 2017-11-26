package com.comikz.repositories;

import com.comikz.model.Manga;
import com.comikz.model.MangaDetail;
import com.comikz.model.MangaPage;
import com.comikz.retrofit.mappers.MangaMapper;
import com.comikz.retrofit.services.MangaFoxService;
import com.comikz.retrofit.utils.HtmlToJson;
import com.comikz.utils.rx.DataSource;
import com.comikz.utils.rx.ObservableUtils;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaRepositoryImp implements MangaRepository {

    private final MangaFoxService mService;

    public MangaRepositoryImp(Retrofit retrofit) {
        mService = retrofit.create(MangaFoxService.class);
    }

    @Override
    public Observable<DataSource<List<Manga>>> searchManga(String query) {
        return mService.searchManga(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sMangaFoxCollection)
                .compose(ObservableUtils.mapResponses(MangaMapper.sCollection));
    }

    @Override
    public Observable<DataSource<MangaPage>> getMangaPage(String manga, String volume, String chapter, int page) {
        return mService.getMangaPages(manga, volume, chapter, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sMangaFoxPage)
                .compose(ObservableUtils.mapResponses(MangaMapper.sSinglePage));
    }

    @Override
    public Observable<DataSource<List<Manga>>> getMangas(int page) {
        return mService.getMangas(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sMangaFoxCollection)
                .compose(ObservableUtils.mapResponses(MangaMapper.sCollection));
    }

    @Override
    public Observable<DataSource<MangaDetail>> getMangaInfo(String id) {
        return mService.getMangaDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(HtmlToJson.sMangaFoxSingle)
                .compose(ObservableUtils.mapResponses(MangaMapper.sSingle));
    }
}
