package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    List<Question> findAllByTextContainingIgnoreCase(String text);

    @Transactional
    @Modifying
    @Query("UPDATE Question SET text = :text,updated_at = :date WHERE id = :id")
    void updateQuestion(String id, String text, Date date);

}
