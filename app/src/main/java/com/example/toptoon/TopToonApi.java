package com.example.toptoon;

import com.example.toptoon.TopToonItems;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopToonApi {
    @GET("toptoon_drawable/drawable.json")
    Call<TopToonItems> getTopToonItems();
}