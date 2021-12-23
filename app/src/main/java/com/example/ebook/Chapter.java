package com.example.ebook;

import java.io.Serializable;

public class Chapter implements Serializable {

    private String name;
    private String content;

    public Chapter(){}

    public Chapter(String name) {
        this.name = name;
    }

    public Chapter(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
