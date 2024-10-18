package com.um.bioskop.abstracts;

public abstract class Account {
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
