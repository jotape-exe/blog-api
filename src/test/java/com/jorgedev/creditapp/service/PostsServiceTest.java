package com.jorgedev.creditapp.service;

import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.repository.PostsRepository;
import com.jorgedev.creditapp.service.exceptions.ListNotFoundException;
import com.jorgedev.creditapp.service.exceptions.ObjectNotFoundException;
import com.jorgedev.creditapp.service.exceptions.StringSizeException;
import com.jorgedev.creditapp.service.impl.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PostsServiceTest {

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private PostsService postsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Title", "Content"));
        when(postsRepository.findAll()).thenReturn(posts);

        ArrayList<Post> result = postsService.getAllPosts();

        assertEquals(posts, result);
    }

    @Test
    void testGetAllPosts_emptyList() {
        when(postsRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ListNotFoundException.class, () -> postsService.getAllPosts());
    }

    @Test
    void testSavePost_validContent() {
        Post post = new Post(1L, "Title", "Content");

        postsService.savePost(post);

        verify(postsRepository, times(1)).save(post);
    }

    @Test
    void testSavePost_invalidTile() {
        Post post = new Post(1L, "Title Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.", "Content that exceeds 255 ");

        assertThrows(StringSizeException.class, () -> postsService.savePost(post));
    }

    @Test
    void testSavePost_invalidContent() {
        Post post = new Post(1L, "Title ", "Content that exceeds 255 Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

        assertThrows(StringSizeException.class, () -> postsService.savePost(post));
    }

    @Test
    void testDeletePost() {
        Post post = new Post(1L, "Title", "Content");

        when(postsRepository.findById(post.getId())).thenReturn(Optional.of(post));
        postsService.deletePost(post.getId());

        verify(postsRepository, times(1)).deleteById(post.getId());
    }

    @Test
    void testNotDeletePost() {
        Post post = new Post(1L, "Title", "Content");
        final Long fakeID = 54L;

        when(postsRepository.findById(post.getId())).thenReturn(Optional.of(post));

        assertThrows(ObjectNotFoundException.class, () -> postsService.deletePost(fakeID));

    }

    @Test
    void testGetPostById_existingPost() {
        Long postId = 1L;
        Post post = new Post(postId, "Title", "Content");
        when(postsRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postsService.getPostById(postId);

        assertEquals(post, result);
    }

    @Test
    void testGetPostById_nonExistingPost() {

        Long postId = 1L;
        when(postsRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> postsService.getPostById(postId));
    }
}
