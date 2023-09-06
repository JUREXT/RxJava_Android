package com.example.rxjava3.rx.jurel.network;

import com.example.rxjava3.rx.jurel.network.model.PostPaginationDTO;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

// 'https://dummyjson.com'
// https://dummyjson.com/posts?limit=2

public interface ApiV3Pagination {
    @GET("/posts")
    Observable<PostPaginationDTO> getPostsWithPagination(
            @Query("skip") int skip,
            @Query("limit") int limitPerPage
    );
}