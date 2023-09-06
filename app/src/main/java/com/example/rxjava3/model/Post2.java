package com.example.rxjava3.model;

import java.util.List;

public class Post2 {
    public Post2(Integer id, String title, String body, Integer userId, List<String> tags, Integer reactions) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.tags = tags;
        this.reactions = reactions;
    }

    public Integer id;
    public String title;
    public String body;
    public Integer userId;
    public List<String> tags;
    public Integer reactions;
}