package com.example.rxjava3.rx.jurel.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientPagination {
    private static final String BASE_URL = "https://dummyjson.com";

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final ApiV3Pagination apiV3Pagination = retrofit.create(ApiV3Pagination.class);

    public static ApiV3Pagination getApiV3Pagination() {
        return apiV3Pagination;
    }
}