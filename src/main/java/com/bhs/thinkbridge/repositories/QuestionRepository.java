package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
}
