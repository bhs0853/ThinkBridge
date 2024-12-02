package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.QuestionDTO;
import com.bhs.thinkbridge.models.Question;
import com.bhs.thinkbridge.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> getQuestionByText(@RequestBody QuestionDTO questionDTO){
        return ResponseEntity.ok(questionService.getQuestions(questionDTO));
    }

    @PostMapping
    public ResponseEntity<Optional<Question>> postQuestion(@RequestBody QuestionDTO questionDTO){
        Optional<Question> newQuestion= questionService.postQuestion(questionDTO);
        if(newQuestion.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Question>> updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable("id") String id){
        Optional<Question> updatedQuestion = questionService.updateQuestion(id,questionDTO);
        if(updatedQuestion.isPresent()){
            return ResponseEntity.ok(updatedQuestion);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable("id") String id){
        questionService.deleteQuestionById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
