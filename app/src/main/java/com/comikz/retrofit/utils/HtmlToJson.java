package com.comikz.retrofit.utils;

import android.text.TextUtils;
import android.util.Log;

import com.comikz.retrofit.responses.ApiMangaChaptersResponse;
import com.comikz.retrofit.responses.ApiMangaDetailResponse;
import com.comikz.retrofit.responses.ApiMangaPage;
import com.comikz.retrofit.responses.ApiMangaResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.functions.Func1;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class HtmlToJson {

    public static Func1<? super Response<ResponseBody>, ? extends Response<ApiMangaPage>> sMangaFoxPage =
            new Func1<Response<ResponseBody>, Response<ApiMangaPage>>() {
                @Override
                public Response<ApiMangaPage> call(Response<ResponseBody> responseBody) {
                    ApiMangaPage apiMangaPage = new ApiMangaPage();

                    try {
                        if (responseBody.isSuccessful()) {
                            String htmlString = responseBody.body().string();
                            Document document = Jsoup.parse(htmlString);

                            String imageUrl = document.getElementsByClass("read_img").get(0).getElementsByTag("img")
                                    .attr("src");

                            Log.d("SARASA", imageUrl != null ? imageUrl : "");

                            Element selectChapterNode = document.getElementById("top_bar").getElementsByTag("select").get(1);
                            String currentSelectedPage = selectChapterNode.getElementsByAttributeValueMatching("selected", "selected").text();
                            int totalChapters = selectChapterNode.getElementsByTag("option").size() - 1;

                            apiMangaPage.imageUrl = imageUrl;
                            apiMangaPage.currentPage = Integer.valueOf(currentSelectedPage);
                            apiMangaPage.totalPages = totalChapters;
                        }
                    } catch (Exception e) {
                        return Response.error(responseBody.code(), responseBody.errorBody());
                    } finally {
                        return Response.success(apiMangaPage);
                    }
                }
            };

    public static Func1<? super Response<ResponseBody>, ? extends Response<List<ApiMangaResponse>>> sMangaFoxCollection
            = new Func1<Response<ResponseBody>, Response<List<ApiMangaResponse>>>() {
        @Override
        public Response<List<ApiMangaResponse>> call(Response<ResponseBody> responseBody) {
            List<ApiMangaResponse> responseList = new ArrayList<>();

            try {
                if (responseBody.isSuccessful()) {
                    String htmlString = responseBody.body().string();
                    Document document = Jsoup.parse(htmlString);

                    for (Element mangaNode : document.getElementById("mangalist").getElementsByTag("li")) {
                        String id = mangaNode.getElementsByClass("manga_img").attr("href");
                        if (!TextUtils.isEmpty(id)) {
                            String[] permalinkArray = id.split("/");
                            id = permalinkArray[permalinkArray.length - 1];

                            String title = mangaNode.getElementsByClass("manga_text").get(0).child(0).text();
                            String rate = mangaNode.getElementsByClass("rate").get(0).text();
                            String image = mangaNode.getElementsByTag("img").attr("src");
                            String categories = mangaNode.getElementsByClass("info").get(0).attr("title");
                            String votes = mangaNode.getElementsByClass("info").get(1).getElementsByTag("label")
                                    .text();


                            ApiMangaResponse response = new ApiMangaResponse();

                            response.id = id;
                            response.title = title;
                            response.categories = Arrays.asList(categories.split(","));
                            response.rate = rate;
                            response.votes = votes;
                            response.image = image;

                            responseList.add(response);
                        }
                    }
                }
            } catch (Exception e) {
                return Response.error(responseBody.code(), responseBody.errorBody());
            } finally {
                return Response.success(responseList);
            }
        }
    };

    public static Func1<? super Response<ResponseBody>, ? extends Response<ApiMangaDetailResponse>> sMangaFoxSingle
            = new Func1<Response<ResponseBody>, Response<ApiMangaDetailResponse>>() {
        @Override
        public Response<ApiMangaDetailResponse> call(Response<ResponseBody> responseBody) {
            ApiMangaDetailResponse apiMangaDetailResponse = new ApiMangaDetailResponse();

            try {
                if (responseBody.isSuccessful()) {
                    String htmlString = responseBody.body().string();
                    Document document = Jsoup.parse(htmlString);

                    String description = document.getElementsByClass("summary").text();
                    apiMangaDetailResponse.description = description;

                    apiMangaDetailResponse.image = document.getElementsByClass("cover").get(0).getElementsByTag("img")
                        .attr("src");

                    Element chaptersNode = document.getElementById("chapters");

                    apiMangaDetailResponse.chapters = new ArrayList<>();

                    for (Element ulList : chaptersNode.getElementsByClass("chlist")) {
                        for (Element liList : ulList.getElementsByTag("li")) {
                            ApiMangaChaptersResponse apiMangaChaptersResponse = new ApiMangaChaptersResponse();

                            apiMangaChaptersResponse.title = liList.text();
                            apiMangaChaptersResponse.link = liList.getElementsByTag("a").get(1).attr("href");

                            apiMangaDetailResponse.chapters.add(apiMangaChaptersResponse);
                        }
                    }
                }
            } catch (Exception e) {
                return Response.error(responseBody.code(), responseBody.errorBody());
            } finally {
                return Response.success(apiMangaDetailResponse);
            }
        }
    };

}
