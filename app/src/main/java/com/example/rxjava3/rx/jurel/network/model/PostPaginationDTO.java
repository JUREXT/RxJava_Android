package com.example.rxjava3.rx.jurel.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPaginationDTO {
    @SerializedName("posts")
    @Expose
    public List<Post2DTO> posts;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("skip")
    @Expose
    public Integer skip;
    @SerializedName("limit")
    @Expose
    public Integer limit;
}