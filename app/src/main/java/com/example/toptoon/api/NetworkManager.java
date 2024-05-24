package com.example.toptoon.api;

import com.example.toptoon.dto.ApiItems;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkManager {
    private static final TopToonApi service = RetrofitClient.getClient().create(TopToonApi.class);

    public static void fetchTopToonItems(Callback<ApiItems> callback) {
        Call<ApiItems> call = service.getTopToonItems();
        call.enqueue(callback);
    }
}

