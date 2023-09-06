package com.example.rxjava3.rx.jurel.network;

import com.example.rxjava3.rx.jurel.network.model.CommentDTO;
import com.example.rxjava3.rx.jurel.network.model.Post1DTO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiV2 {

    @GET("posts")
    Single<List<Post1DTO>> getPosts();

    @GET("posts/{id}/comments")
    Single<List<CommentDTO>> getComments(@Path("id") int id);
}