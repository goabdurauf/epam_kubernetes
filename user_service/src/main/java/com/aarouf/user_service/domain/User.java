package com.aarouf.user_service.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private long amountOfPosts;


    public User() {
    }

    public User(String username) {
        this.username = username;
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
