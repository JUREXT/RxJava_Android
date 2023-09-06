package com.example.rxjava3.experimental;

import com.example.rxjava3.model.Post2;
import com.example.rxjava3.model.PostPagination;
import com.example.rxjava3.rx.jurel.mapping.BaseMapper;
import com.example.rxjava3.rx.jurel.network.model.Post2DTO;
import com.example.rxjava3.rx.jurel.network.model.PostPaginationDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class PostPaginationMapper implements BaseMapper<PostPaginationDTO, PostPagination> {
    @Override
    public Observable<PostPagination> map(PostPaginationDTO postPaginationDTO) {
        if (postPaginationDTO == null) {
            return Observable.error(new IllegalArgumentException("Cannot transform a null value"));
        }
        return Observable.just(postPaginationDTO)
                .map(dto -> {
                    List<Post2> posts = new ArrayList<>();
                    for (Post2DTO item : dto.posts) {
                        List<String> tags = new ArrayList<>(item.tags);
                        posts.add(new Post2(item.id, item.title, item.body, item.userId, tags, item.reactions));
                    }
                    return new PostPagination(posts, dto.total, dto.skip, dto.limit);
                });
    }
}