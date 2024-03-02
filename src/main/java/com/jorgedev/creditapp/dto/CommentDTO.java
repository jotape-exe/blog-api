package com.jorgedev.creditapp.dto;

import com.jorgedev.creditapp.entity.Comment;

public class CommentDTO {
    private Long postId;
    private String content;

    public CommentDTO(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment toEntity(){
        return new Comment(getPostId(), getContent());
    }
}
