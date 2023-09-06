package com.example.rxjava3.rx.jurel.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post2DTO {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("userId")
    @Expose
    public Integer userId;
    @SerializedName("tags")
    @Expose
    public List<String> tags;
    @SerializedName("reactions")
    @Expose
    public Integer reactions;
}