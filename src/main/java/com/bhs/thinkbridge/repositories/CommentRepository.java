package com.bhs.thinkbridge.repositories;


import com.bhs.thinkbridge.models.Answer;
import com.bhs.thinkbridge.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {

    @Transactional
    @Modifying
    @Query("UPDATE Comment SET text = :text, updated_at = :date WHERE id = :id")
    void updateTextByCommentId(String id, String text, Date date);

    List<Comment> findByAnswer(Optional<Answer> answer);
}
