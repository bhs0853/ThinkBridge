package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
}
