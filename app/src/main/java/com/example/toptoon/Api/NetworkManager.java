package com.example.toptoon.Api;

import com.example.toptoon.DataModel.ApiItems;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkManager {
    private static TopToonApi service = RetrofitClient.getClient().create(TopToonApi.class);

    public static void fetchTopToonItems(Callback<ApiItems> callback) {
        Call<ApiItems> call = service.getTopToonItems();
        call.enqueue(callback);
    }
}

