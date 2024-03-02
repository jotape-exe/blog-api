package com.jorgedev.creditapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgedev.creditapp.dto.CommentDTO;
import com.jorgedev.creditapp.dto.PostDTO;
import com.jorgedev.creditapp.entity.Comment;
import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.service.impl.CommentService;
import com.jorgedev.creditapp.service.impl.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostsService postsService;

    @MockBean
    private CommentService commentService;

    @Test
    public void testCreatePost() throws Exception {
        PostDTO postDTO = new PostDTO("Test Title", "Test Content");
        Post post = new Post(1L, "Test Title", "Test Content");

        doNothing().when(postsService).savePost(post);

        mockMvc.perform(post("/api/blog/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Post criado com sucesso!"));

        verify(postsService, times(1)).savePost(any());
    }

    @Test
    public void testUpdatePost() throws Exception {
        Long postId = 1L;
        PostDTO postDTO = new PostDTO("Updated Title", "Updated Content");
        Post post = new Post(postId, "Updated Title", "Updated Content");

        doNothing().when(postsService).savePost(post);

        mockMvc.perform(patch("/api/blog/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Post atualizado com sucesso!"));

        verify(postsService, times(1)).savePost(any());
    }

    @Test
    public void testDeletePost() throws Exception {
        Long postId = 1L;

        mockMvc.perform(delete("/api/blog/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Post apagado com sucesso"));

        verify(postsService, times(1)).deletePost(postId);
    }

    @Test
    public void testGetPostById() throws Exception {
        Long postId = 1L;
        Post post = new Post(postId, "Test Title", "Test Content");

        when(postsService.getPostById(postId)).thenReturn(post);

        mockMvc.perform(get("/api/blog/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consulta realizada com sucesso"))
                .andExpect(jsonPath("$.success").value(true));

        verify(postsService, times(1)).getPostById(postId);
    }

    @Test
    public void testCreateComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO(1L, "Test Comment");
        Comment comment = new Comment(1L, 1L, "Test Comment");


        doNothing().when(commentService).saveComment(comment);

        mockMvc.perform(post("/api/blog/comentarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Comentário salvo com sucesso!"))
                .andExpect(jsonPath("$.success").value(true));

        verify(commentService, times(1)).saveComment(any());
    }

    @Test
    public void testUpdateComment() throws Exception {
        Long commentId = 1L;
        CommentDTO commentDTO = new CommentDTO(1L, "Updated Comment");
        Comment comment = new Comment(commentId, 1L, "Updated Comment");

        doNothing().when(commentService).saveComment(comment);

        mockMvc.perform(patch("/api/blog/comentarios/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Comentário atualizado com sucesso!"));

        verify(commentService, times(1)).saveComment(any());
    }

    @Test
    public void testDeleteComment() throws Exception {
        Long commentId = 1L;

        mockMvc.perform(delete("/api/blog/comentarios/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Comentário apagado com sucesso"));

        verify(commentService, times(1)).deleteComment(commentId);
    }

    @Test
    public void testGetCommentById() throws Exception {
        Long postId = 1L;
        Comment comment = new Comment(1L, postId, "Test Comment");

        List<Comment> comments = Arrays.asList(comment);

        when(commentService.getCommentByPostId(postId)).thenReturn(comments);

        mockMvc.perform(get("/api/blog/comentarios/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consulta realizada com sucesso"));

        verify(commentService, times(1)).getCommentByPostId(postId);
    }
}
