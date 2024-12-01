package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Transactional
    @Modifying
    @Query("UPDATE User SET " +
            "user_name = CASE WHEN :userName is not null THEN :userName ELSE user_name END," +
            "email = CASE WHEN :email is not null THEN :email ELSE email END," +
            "bio = CASE WHEN :bio is not null THEN :bio END " +
            "WHERE user_id = :id")
    void updateUser(String id, String userName, String email, String bio);
}
