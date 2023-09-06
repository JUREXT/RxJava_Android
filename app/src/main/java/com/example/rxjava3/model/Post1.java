package com.example.rxjava3.model;

import com.example.rxjava3.rx.jurel.network.model.CommentDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post1 {

    @SerializedName("userId")
    @Expose()
    private int userId;

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("body")
    @Expose()
    private String body;

    private List<CommentDTO> commentDTOS;

    public Post1(int userId, int id, String title, String body, List<CommentDTO> commentDTOS) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.commentDTOS = commentDTOS;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CommentDTO> getComments() {
        return commentDTOS;
    }

    public void setComments(List<CommentDTO> commentDTOS) {
        this.commentDTOS = commentDTOS;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
