package com.jorgedev.creditapp.dto.response;

import java.util.List;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private List<CommentResponse> commentResponses;


    public PostResponse(Long id, String title, String content, List<CommentResponse> commentResponses) {
        this.commentResponses = commentResponses;
        this.id = id;
        this.title = title;
        this.content = content;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<CommentResponse> getCommentResponses() {
        return commentResponses;
    }

    public void setCommentResponses(List<CommentResponse> commentResponses) {
        this.commentResponses = commentResponses;
    }
}
