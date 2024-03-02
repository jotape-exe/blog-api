package com.jorgedev.creditapp.service;

import com.jorgedev.creditapp.entity.Comment;
import com.jorgedev.creditapp.entity.Post;

import java.util.ArrayList;
import java.util.List;

public interface IPostsService {
    ArrayList<Post> getAllPosts();
    void savePost(Post post);
    void deletePost(Long id);

    Post getPostById(Long id);

}
