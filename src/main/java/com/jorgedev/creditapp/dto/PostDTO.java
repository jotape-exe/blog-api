package com.jorgedev.creditapp.dto;

import com.jorgedev.creditapp.entity.Post;

public class PostDTO {
    private String title;
    private String content;

    public PostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post toEntity(){
        return new Post(getTitle(), getContent());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
