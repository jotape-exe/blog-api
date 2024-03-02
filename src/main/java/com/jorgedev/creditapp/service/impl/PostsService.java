package com.jorgedev.creditapp.service.impl;

import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.repository.PostsRepository;
import com.jorgedev.creditapp.service.IPostsService;
import com.jorgedev.creditapp.service.exceptions.ListNotFoundException;
import com.jorgedev.creditapp.service.exceptions.ObjectNotFoundException;
import com.jorgedev.creditapp.service.exceptions.StringSizeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostsService implements IPostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = (ArrayList<Post>) this.postsRepository.findAll();
        if (posts.isEmpty()) {
            throw new ListNotFoundException("Sem posts");
        }
        return posts;
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        if (post.getContent().length() > 255 || post.getTitle().length() > 255) {
            throw new StringSizeException("Tamanho do conteudo inválido");
        }

        this.postsRepository.save(post);

    }

    @Override
    public void deletePost(Long id) {
        getPostById(id);
        this.postsRepository.deleteById(id);
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> post = this.postsRepository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Post não encontrado. ID:" + id));
    }
}

