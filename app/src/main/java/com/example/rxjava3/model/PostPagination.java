package com.example.rxjava3.model;

import androidx.annotation.NonNull;

import java.util.List;

public class PostPagination {
    public PostPagination(List<Post2> posts, Integer total, Integer skip, Integer limit) {
        this.posts = posts;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<Post2> posts;
    public Integer total;
    public Integer skip;
    public Integer limit;

    @NonNull
    @Override
    public String toString() {
        return "PostPagination{" +
                "posts count=" + posts.size() +
                ", total=" + total +
                ", skip=" + skip +
                ", limit=" + limit +
                '}';
    }
}