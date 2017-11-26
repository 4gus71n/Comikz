package com.comikz.retrofit.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 8/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class ApiMangaResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("rate")
    public String rate;

    @SerializedName("votes")
    public String votes;

    @SerializedName("categories")
    public List<String> categories;

}
