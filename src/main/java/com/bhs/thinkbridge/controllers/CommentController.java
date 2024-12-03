package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.CommentDTO;
import com.bhs.thinkbridge.models.Comment;
import com.bhs.thinkbridge.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentsByAnswerId(@PathVariable("id") String id){
        Optional<List<Comment>> comment = commentService.getCommentsByAnswerId(id);
        if(comment.isPresent()){
            return ResponseEntity.ok(comment.get());
        }
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        Optional<?> newComment = commentService.addComment(commentDTO);
        if(newComment.get().getClass().equals(Comment.class)){
            return ResponseEntity.status(HttpStatus.CREATED).body(newComment.get());
        }
        return ResponseEntity.badRequest().body(newComment.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") String id,@RequestBody CommentDTO commentDTO){
        Optional<?> comment = commentService.updateCommentById(id,commentDTO);
        if(comment.get().getClass().equals(Comment.class)){
            ResponseEntity.ok(comment.get());
        }
        return ResponseEntity.badRequest().body(comment.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") String id){
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }

}
