package com.example.kankerkulit.Model;

import java.io.Serializable;

public class ChatList implements Serializable {
    private String id;

    public ChatList(String id) {
        this.id = id;
    }

    public ChatList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
