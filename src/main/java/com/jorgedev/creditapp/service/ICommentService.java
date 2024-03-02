package com.jorgedev.creditapp.service;

import com.jorgedev.creditapp.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public interface ICommentService {
    ArrayList<Comment> getAllComments();

    void saveComment(Comment comment);

    void deleteComment(Long id);

    Comment getCommentById(Long id);

    List<Comment> getCommentByPostId(Long postId);
}
