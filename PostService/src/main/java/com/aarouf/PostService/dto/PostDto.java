package com.aarouf.PostService.dto;

import com.aarouf.PostService.domain.Post;


import java.util.Date;

public class PostDto {
    private long id;
    private long authorId;
    private String text;
    private Date date;


    public PostDto(long id, long authorId, String text, Date date) {
        this.id = id;
        this.authorId = authorId;
        this.text = text;
        this.date = date;
    }

    public PostDto(Post post){
        this(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
