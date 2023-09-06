package com.example.rxjava3.rx.jurel.network;

import com.example.rxjava3.rx.jurel.network.model.CommentDTO;
import com.example.rxjava3.rx.jurel.network.model.Post1DTO;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiV1 {

    @GET("posts")
    Observable<List<Post1DTO>> getPosts();

    @GET("posts/{id}/comments")
    Observable<List<CommentDTO>> getComments(@Path("id") int id);
}