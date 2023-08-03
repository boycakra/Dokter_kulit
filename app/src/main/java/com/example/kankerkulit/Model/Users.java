package com.example.kankerkulit.Model;

import java.io.Serializable;

public class Users implements Serializable {
    private String id;
    private String username;
    private String imageURL;
    private String profesi;
    private String password;


    public Users(String id, String username, String imageURL, String profesi, String password) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.profesi=profesi;
        this.password=password;
    }
    public Users(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getprofesi() {
        return profesi;
    }

    public void setprofesi(String profesi) {
        this.profesi = profesi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

