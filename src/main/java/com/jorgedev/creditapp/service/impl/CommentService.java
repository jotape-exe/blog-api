package com.jorgedev.creditapp.service.impl;

import com.jorgedev.creditapp.entity.Comment;
import com.jorgedev.creditapp.entity.Post;
import com.jorgedev.creditapp.repository.CommentRepository;
import com.jorgedev.creditapp.repository.PostsRepository;
import com.jorgedev.creditapp.service.ICommentService;
import com.jorgedev.creditapp.service.exceptions.ListNotFoundException;
import com.jorgedev.creditapp.service.exceptions.ObjectNotFoundException;
import com.jorgedev.creditapp.service.exceptions.StringSizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostsService postsService;

    @Override
    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> comments = (ArrayList<Comment>) this.commentRepository.findAll();
        if (comments.isEmpty()) {
            throw new ListNotFoundException("Sem comentários");
        }
        return comments;
    }

    @Override
    public void saveComment(Comment comment) {
        if(comment.getContent().length() > 255){
            throw new StringSizeException("Tamanho do conteudo inválido");
        }

        Post post = this.postsService.getPostById(comment.getPostId());

        if (comment.getId() == null) {
            this.commentRepository.save(comment);
        } else {
            comment.setPostId(post.getId());
            this.commentRepository.save(comment);
        }
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = getCommentById(id);
        this.commentRepository.delete(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        return comment.orElseThrow(() -> new ObjectNotFoundException("Comentário não encontrado. ID:" + id));
    }

    @Override
    public List<Comment> getCommentByPostId(Long postId) {
        ArrayList<Comment> comments = new ArrayList<>();
        comments = (ArrayList<Comment>) this.commentRepository.findByPostId(postId);
        return comments;
    }


}
