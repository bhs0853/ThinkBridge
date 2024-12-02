package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.AnswerDTO;
import com.bhs.thinkbridge.models.Answer;
import com.bhs.thinkbridge.services.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController (AnswerService answerService){
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Answer>> getAnswersByQuestionId(@PathVariable("id") String id){
        List<Answer> answerList = answerService.getAnswersByQuestionId(id);
        return ResponseEntity.ok(answerList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createAnswer(@PathVariable("id") String id, @RequestBody AnswerDTO answerDTO){
        Optional<?> answer = answerService.createAnswer(id,answerDTO);
        if(answer.get().getClass().equals(Answer.class)){
            return ResponseEntity.ok(answer.get());
        }
        return ResponseEntity.badRequest().body(answer.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable("id") String id, @RequestBody AnswerDTO answerDTO){
        Optional<Answer> answer = answerService.updateAnswer(id,answerDTO);
        return ResponseEntity.ok(answer.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable("id") String id){
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
