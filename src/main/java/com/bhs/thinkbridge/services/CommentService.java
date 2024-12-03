package com.bhs.thinkbridge.services;


import com.bhs.thinkbridge.dtos.CommentDTO;
import com.bhs.thinkbridge.dtos.ErrorDTO;
import com.bhs.thinkbridge.models.Answer;
import com.bhs.thinkbridge.models.Comment;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AnswerService answerService;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, AnswerService answerService, UserService userService){
        this.commentRepository = commentRepository;
        this.answerService = answerService;
        this.userService = userService;
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public Optional<List<Comment>> getCommentsByAnswerId(String id){
        Optional<Answer> answer = answerService.getAnswerById(id);
        return Optional.of(commentRepository.findByAnswer(answer));
    }

    public Optional<?> addComment(CommentDTO commentDTO){
        if(commentDTO.getText() == null || commentDTO.getText().length() < 3){
            return Optional.of(new ErrorDTO(HttpStatus.BAD_REQUEST,"The comment should have atleast 3 characters"));
        }
        Optional<Answer> answer = answerService.getAnswerById(commentDTO.getAnswer_id());
        Optional<User> user = userService.getUserById(commentDTO.getUser_id());
        if(user.isEmpty() || answer.isEmpty()){
            return Optional.of(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR));
        }
        Comment newComment = Comment.builder()
                .user(user.get())
                .created_at(new Date())
                .updated_at(new Date())
                .text(commentDTO.getText())
                .answer(answer.get())
                .build();
        return Optional.of(commentRepository.save(newComment));
    }

    public Optional<?> updateCommentById(String id,CommentDTO commentDTO){
        if(commentRepository.existsById(id)){
            if(commentDTO.getText() == null || commentDTO.getText().length() < 3){
                return Optional.of(new ErrorDTO(HttpStatus.BAD_REQUEST,"The comment should have atleast 3 characters"));
            }
            commentRepository.updateTextByCommentId(id,commentDTO.getText(),new Date());
            return commentRepository.findById(id);
        }
        return Optional.of(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public void deleteCommentById(String id){
        commentRepository.deleteById(id);
    }

}
