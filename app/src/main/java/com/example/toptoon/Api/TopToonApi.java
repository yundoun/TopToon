package com.example.toptoon.Api;

import com.example.toptoon.DataModel.ApiItems;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopToonApi {
    @GET("toptoon_drawable/drawable.json")
    Call<ApiItems> getTopToonItems();
}