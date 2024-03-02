package com.jorgedev.creditapp.controller;

import com.jorgedev.creditapp.dto.CommentDTO;
import com.jorgedev.creditapp.dto.PostDTO;
import com.jorgedev.creditapp.dto.response.CommentResponse;
import com.jorgedev.creditapp.dto.response.PostResponse;
import com.jorgedev.creditapp.entity.Comment;
import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.http.response.DefaultResponse;
import com.jorgedev.creditapp.service.impl.CommentService;
import com.jorgedev.creditapp.service.impl.PostsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
@Validated
public class BlogController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private CommentService commentService;

    //POSTS
    @GetMapping("/posts")
    public ResponseEntity<DefaultResponse> getAllPosts() {
        List<PostResponse> postResponses = new ArrayList<>();
        ArrayList<Post> posts = new ArrayList<>();


        posts = postsService.getAllPosts();
        for (Post post : posts) {
            List<Comment> comments = commentService.getCommentByPostId(post.getId());
            List<CommentResponse> commentResponses = new ArrayList<>();

            comments.forEach((item) -> {
                commentResponses.add(new CommentResponse(item.getId(), item.getContent()));
            });

            postResponses.add(new PostResponse(post.getId(), post.getTitle(), post.getContent(), commentResponses));
        }


        return new ResponseEntity<>(new DefaultResponse("Consulta realizada com sucesso", postResponses), HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<DefaultResponse> createPost(@Valid @RequestBody PostDTO request) {
        Post post = new PostDTO(request.getTitle(), request.getContent()).toEntity();
        postsService.savePost(post);
        return new ResponseEntity<>(new DefaultResponse("Post criado com sucesso!", null), HttpStatus.CREATED);
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<DefaultResponse> updatePost(@Valid @RequestBody PostDTO request, @PathVariable("id") Long id) {
        Post post = new PostDTO(request.getTitle(), request.getContent()).toEntity();
        post.setId(id);
        postsService.savePost(post);
        return new ResponseEntity<>(new DefaultResponse("Post atualizado com sucesso!", null), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<DefaultResponse> deletePost(@PathVariable("id") Long id) {
        postsService.deletePost(id);
        return new ResponseEntity<>(new DefaultResponse("Post apagado com sucesso", null), HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<DefaultResponse> getPostById(@PathVariable("id") Long id) {
        Post post = postsService.getPostById(id);

        List<Comment> comments = commentService.getAllComments();

        List<CommentResponse> commentResponses = new ArrayList<>();

        if (comments != null && !comments.isEmpty()) {
            List<Comment> postComments = comments.stream()
                    .filter(comment -> comment.getPostId().equals(post.getId()))
                    .toList();

            commentResponses = postComments.stream()
                    .map(comment -> new CommentResponse(comment.getId(), comment.getContent()))
                    .collect(Collectors.toList());
        }

        return new ResponseEntity<>(new DefaultResponse("Consulta realizada com sucesso", new PostResponse(post.getId(), post.getTitle(), post.getContent(), commentResponses)), HttpStatus.OK);
    }


    //COMMENTS
    @PostMapping("/comentarios")
    public ResponseEntity<DefaultResponse> createComment(@Valid @RequestBody CommentDTO request) {

        Comment comment = new CommentDTO(request.getPostId(), request.getContent()).toEntity();
        commentService.saveComment(comment);
        return new ResponseEntity<>(new DefaultResponse("Comentário salvo com sucesso!", null), HttpStatus.CREATED);
    }

    @PatchMapping("/comentarios/{id}")
    public ResponseEntity<DefaultResponse> updateComment(@Valid @RequestBody CommentDTO request, @PathVariable("id") Long id) {
        Comment comment = new CommentDTO(request.getPostId(), request.getContent()).toEntity();
        comment.setId(id);
        commentService.saveComment(comment);
        return new ResponseEntity<>(new DefaultResponse("Comentário atualizado com sucesso!", null), HttpStatus.OK);
    }

    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<DefaultResponse> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new DefaultResponse("Comentário apagado com sucesso", null), HttpStatus.OK);
    }

    @GetMapping("/comentarios/{postId}")
    public ResponseEntity<DefaultResponse> getCommentById(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(new DefaultResponse("Consulta realizada com sucesso", comments), HttpStatus.OK);
    }
}

