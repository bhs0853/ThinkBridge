package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Answer;
import com.bhs.thinkbridge.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    List<Answer> findAllByQuestionEquals(Question question);

    @Transactional
    @Modifying
    @Query("UPDATE Answer SET text = :text WHERE id = :id")
    void updateAnswerById(String id, String text);
}
