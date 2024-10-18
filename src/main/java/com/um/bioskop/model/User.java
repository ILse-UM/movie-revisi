package com.um.bioskop.model;

import com.um.movie.abstracts.Account;

public class User extends Account {
    private String email;
    private String password;

    public User(String username, String email, String password) {
        super(username);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
