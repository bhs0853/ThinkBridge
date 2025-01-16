package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.dtos.ErrorDTO;
import com.bhs.thinkbridge.dtos.QuestionDTO;
import com.bhs.thinkbridge.models.Question;
import com.bhs.thinkbridge.models.Tag;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.QuestionRepository;
import com.bhs.thinkbridge.repositories.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final TagRepository tagRepository;

    public QuestionService(QuestionRepository questionRepository, UserService userService, TagRepository tagRepository){
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    public Optional<?> postQuestion(QuestionDTO questionDTO){
        if(questionDTO.getUser_id() == null || questionDTO.getText() == null) {
            return Optional.of(new ErrorDTO(HttpStatus.BAD_REQUEST,"question should have more than 5 characters"));
        }
        Optional<User> user = userService.getUser();
        List<Tag> tagList = questionDTO.getTagsList().stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if(user.isEmpty()){
            return Optional.of(new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR));
        }
        Question newQuestion = Question.builder()
                .user(user.get())
                .created_at(new Date())
                .updated_at(new Date())
                .text(questionDTO.getText())
                .tag_list(tagList)
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

    public Optional<Question> getQuestionById(String id){
        return questionRepository.findById(id);
    }

    public void deleteQuestionById(String id) {
        if(questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        }
    }

    public Optional<Question> updateQuestion(String id,QuestionDTO questionDTO) {
        if(questionRepository.existsById(id)){
            questionRepository.updateQuestion(id,questionDTO.getText(),new Date());
        }
        return questionRepository.findById(id);
    }
}
