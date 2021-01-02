package com.example.personal.model;

import java.io.Serializable;

public class UserFA implements Serializable {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "UserFA{" +
                "id=" + id +
                ", content=" + content+ '}';
    }

}
