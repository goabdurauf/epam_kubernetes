package com.aarouf.PostService.dto;

public class UserDto {

    private long id;
    private String username;
    private long amountOfPosts;

    public UserDto(String username, long amountOfPosts) {
        this.username = username;
        this.amountOfPosts = amountOfPosts;
    }

    public UserDto() {
    }

    public UserDto(long id, String username, long amountOfPosts) {
        this.id = id;
        this.username = username;
        this.amountOfPosts = amountOfPosts;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAmountOfPosts() {
        return amountOfPosts;
    }

    public void setAmountOfPosts(long amountOfPosts) {
        this.amountOfPosts = amountOfPosts;
    }
}
