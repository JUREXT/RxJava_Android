package com.example.rxjava3.rx.jurel.network;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com";

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_JSON_PLACEHOLDER)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final ApiV1 apiV1 = retrofit.create(ApiV1.class);
    private static final ApiV2 apiV2 = retrofit.create(ApiV2.class);

    public static ApiV1 getApiV1() {
        return apiV1;
    }

    public static ApiV2 getApiV2() {
        return apiV2;
    }
}