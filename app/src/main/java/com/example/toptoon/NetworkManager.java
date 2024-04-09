package com.example.toptoon;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkManager {
    private static TopToonApi service = RetrofitClient.getClient().create(TopToonApi.class);

    public static void fetchTopToonItems(Callback<TopToonItems> callback) {
        Call<TopToonItems> call = service.getTopToonItems();
        call.enqueue(callback);
    }
}

