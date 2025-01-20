package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Transactional
    @Modifying
    @Query("UPDATE User SET " +
            "email = CASE WHEN :email is not null THEN :email ELSE email END," +
            "bio = CASE WHEN :bio is not null THEN :bio ELSE bio END, " +
            "updated_at = CASE WHEN :date is not null THEN :date END " +
            "WHERE user_id = :id")
    void updateUser(String id, String email, String bio, Date date);

    @Transactional
    @Modifying
    @Query("UPDATE User SET password = :password WHERE user_id = :id")
    void updatePassword(String id, String password);

    Optional<User> findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User WHERE email = :email")
    void deleteUserByEmail(String email);

}
