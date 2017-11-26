package com.comikz.retrofit.mappers;

import com.comikz.model.Manga;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaDetail;
import com.comikz.model.MangaPage;
import com.comikz.retrofit.responses.ApiMangaChaptersResponse;
import com.comikz.retrofit.responses.ApiMangaDetailResponse;
import com.comikz.retrofit.responses.ApiMangaPage;
import com.comikz.retrofit.responses.ApiMangaResponse;
import com.comikz.utils.rx.ObservableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaMapper {


    public static ObservableUtils.Transformable<List<ApiMangaResponse>, List<Manga>> sCollection =
            new ObservableUtils.Transformable<List<ApiMangaResponse>, List<Manga>>() {
                @Override
                public List<Manga> transformServerToModel(List<ApiMangaResponse> serverResponseWrapper) {
                    List<Manga> mangaList = new ArrayList<>();

                    for (ApiMangaResponse apiMangaResponse : serverResponseWrapper) {
                        Manga manga = new Manga();
                        manga.setId(apiMangaResponse.id);
                        manga.setCategories(apiMangaResponse.categories);
                        manga.setRanking(apiMangaResponse.votes);
                        manga.setImage(apiMangaResponse.image);
                        manga.setTitle(apiMangaResponse.title);
                        mangaList.add(manga);
                    }

                    return mangaList;
                }
            };

    public static ObservableUtils.Transformable<ApiMangaDetailResponse, MangaDetail> sSingle =
            new ObservableUtils.Transformable<ApiMangaDetailResponse, MangaDetail>() {
                @Override
                public MangaDetail transformServerToModel(ApiMangaDetailResponse serverResponseWrapper) {
                    MangaDetail mangaDetail = new MangaDetail();

                    mangaDetail.setDescription(serverResponseWrapper.description);
                    mangaDetail.setImageUrl(serverResponseWrapper.image);

                    List<MangaChapter> mangaChapters = new ArrayList<>();

                    for (ApiMangaChaptersResponse apiMangaChaptersResponse : serverResponseWrapper.chapters) {
                        MangaChapter mangaChapter = new MangaChapter();
                        mangaChapter.setTitle(apiMangaChaptersResponse.title);

                        // "//mangafox.me/manga/onepunch_man/vTBD/c083/1.html"
                        apiMangaChaptersResponse.link = apiMangaChaptersResponse.link.replace("//mangafox.me/", "");
                        // "manga/onepunch_man/vTBD/c083/1.html"
                        String[] splitLink = apiMangaChaptersResponse.link.split("/");


                        mangaChapter.setMangaPermalink(splitLink[1]);
                        mangaChapter.setVolume(splitLink[2]);
                        mangaChapter.setChapter(splitLink[3]);

                        mangaChapters.add(mangaChapter);
                    }

                    mangaDetail.setChapters(mangaChapters);

                    return mangaDetail;
                }
            };

    public static ObservableUtils.Transformable<ApiMangaPage, MangaPage> sSinglePage =
            new ObservableUtils.Transformable<ApiMangaPage, MangaPage>() {
                @Override
                public MangaPage transformServerToModel(ApiMangaPage serverResponseWrapper) {
                    return new MangaPage(serverResponseWrapper.imageUrl,
                            serverResponseWrapper.currentPage,
                            serverResponseWrapper.totalPages);
                }
            };
}
