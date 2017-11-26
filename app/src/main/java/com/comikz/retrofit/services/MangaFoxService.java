package com.comikz.retrofit.services;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface MangaFoxService {

    @GET("/directory/{page}.htm")
    Observable<Response<ResponseBody>> getMangas(@Path("page") int page);

    @GET("/search.php?name_method=cw&type=&author_method=cw&author=&artist_method=cw&artist=&genres%5BAction%5D=0&released_method=eq&released=&rating_method=eq&rating=&is_completed=&advopts=1")
    Observable<Response<ResponseBody>> searchManga(@Query("name") String query);

    @GET("manga/{manga_permalink}/{volume}/{chapter}/{page}.html")
    Observable<Response<ResponseBody>> getMangaPages(@Path("manga_permalink") String mangaPermalink,
                                                     @Path("volume") String volume,
                                                     @Path("chapter") String chapter,
                                                     @Path("page") int page);

    @GET("/manga/{id}")
    Observable<Response<ResponseBody>> getMangaDetail(@Path("id") String id);

}
