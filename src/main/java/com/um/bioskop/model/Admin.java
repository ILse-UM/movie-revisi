package com.um.bioskop.model;

import com.um.movie.abstracts.Account;

public class Admin extends Account {
    private String password;

    public Admin(String username, String password) {
        super(username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
