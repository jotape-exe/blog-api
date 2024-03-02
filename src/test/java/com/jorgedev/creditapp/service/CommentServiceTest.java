package com.jorgedev.creditapp.service;

import com.jorgedev.creditapp.entity.Comment;
import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.repository.CommentRepository;
import com.jorgedev.creditapp.service.exceptions.ListNotFoundException;
import com.jorgedev.creditapp.service.exceptions.ObjectNotFoundException;
import com.jorgedev.creditapp.service.exceptions.StringSizeException;
import com.jorgedev.creditapp.service.impl.CommentService;
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

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostsService postsService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, 1L, "Content"));
        when(commentRepository.findAll()).thenReturn(comments);

        ArrayList<Comment> result = commentService.getAllComments();

        assertEquals(comments, result);
    }

    @Test
    void testGetAllComments_emptyList() {
        when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ListNotFoundException.class, () -> commentService.getAllComments());
    }

    @Test
    void testSaveComment_validContent() {
        Comment comment = new Comment(1L, 1L, "Content");
        when(postsService.getPostById(comment.getPostId())).thenReturn(new Post());

        commentService.saveComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testSaveComment_invalidContent() {
        Comment comment = new Comment(1L, 1L, "Content that exceeds 255 characters:000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        assertThrows(StringSizeException.class, () -> commentService.saveComment(comment));
    }

    @Test
    void testDeleteComment() {

        Comment comment = new Comment(1L, 1L, "Content");


        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        commentService.deleteComment(comment.getId());


        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void testNotDeleteComment() {
        Comment comment = new Comment(1L, 1L, "Content");
        final Long fakeID = 54L;

        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThrows(ObjectNotFoundException.class, () -> commentService.deleteComment(fakeID));

    }

    @Test
    void testGetCommentById_existingComment() {
        Long commentId = 1L;
        Comment comment = new Comment(commentId, 1L, "Content");
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));


        Comment result = commentService.getCommentById(commentId);

        assertEquals(comment, result);
    }

    @Test
    void testGetCommentById_nonExistingComment() {
        Long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());


        assertThrows(ObjectNotFoundException.class, () -> commentService.getCommentById(commentId));
    }

}
