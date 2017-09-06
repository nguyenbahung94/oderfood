package com.example.nbhung.oderfood.model;

/**
 * Created by nbhung on 9/6/2017.
 */

public class user {
    private String name;
    private String password;

    public user() {
    }

    public user(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
