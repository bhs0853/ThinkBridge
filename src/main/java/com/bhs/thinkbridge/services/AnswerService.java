package com.bhs.thinkbridge.services;


import com.bhs.thinkbridge.dtos.AnswerDTO;
import com.bhs.thinkbridge.dtos.ErrorDTO;
import com.bhs.thinkbridge.models.Answer;
import com.bhs.thinkbridge.models.Question;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.AnswerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    public AnswerService (AnswerRepository answerRepository, QuestionService questionService, UserService userService){
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.userService = userService;
    }

    public List<Answer> getAnswersByQuestionId(String id) {
        Optional<Question> question = questionService.getQuestionById(id);
        return answerRepository.findAllByQuestionEquals(question.get());
    }

    public Optional<?> createAnswer(String id, AnswerDTO answerDTO) {
        if(answerDTO.getText() == null || answerDTO.getUser_id() == null){
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST);
            error.setError("Please enter some text");
            return Optional.of(error);
        }
        Optional<User> user = userService.getUserById(answerDTO.getUser_id());
        Optional<Question> question = questionService.getQuestionById(id);
        Answer newAnswer = Answer.builder()
                .question(question.get())
                .user(user.get())
                .createdAt(new Date())
                .text(answerDTO.getText())
                .build();
        answerRepository.save(newAnswer);
        return Optional.of(newAnswer);
    }

    public Optional<Answer> updateAnswer(String id, AnswerDTO answerDTO) {
        answerRepository.updateAnswerById(id,answerDTO.getText());
        return answerRepository.findById(id);
    }

    public void deleteAnswer(String id){
        answerRepository.deleteById(id);
    }
}
