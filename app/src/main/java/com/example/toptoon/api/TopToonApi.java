package com.example.toptoon.api;

import com.example.toptoon.dto.ApiItems;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopToonApi {
    @GET("toptoon_drawable/drawable.json")
    Call<ApiItems> getTopToonItems();
}