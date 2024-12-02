package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.dtos.QuestionDTO;
import com.bhs.thinkbridge.models.Question;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;

    public QuestionService(QuestionRepository questionRepository, UserService userService){
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    public Optional<Question> postQuestion(QuestionDTO questionDTO){
        if(questionDTO.getUser_id() == null || questionDTO.getText() == null) {
            return Optional.empty();
        }
        Optional<User> user = userService.getUserById(questionDTO.getUser_id());
        if(user.isEmpty()){
            return Optional.empty();
        }
        Question newQuestion = Question.builder()
                .user(user.get())
                .createdAt(new Date())
                .text(questionDTO.getText())
                .tagsList(questionDTO.getTagsList())
                .build();
        questionRepository.save(newQuestion);
        return Optional.of(newQuestion);
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public List<Question> getQuestions(QuestionDTO questionDTO) {
        return questionRepository.findAllByTextContainingIgnoreCase(questionDTO.getText());
    }

    public void deleteQuestionById(String id) {
        if(questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        }
    }

    public Optional<Question> updateQuestion(String id,QuestionDTO questionDTO) {
        if(questionRepository.existsById(id)){
            questionRepository.updateQuestion(id,questionDTO.getText());
        }
        return questionRepository.findById(id);
    }
}
