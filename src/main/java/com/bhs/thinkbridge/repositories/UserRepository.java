package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Transactional
    @Modifying
    @Query("UPDATE User SET " +
            "user_name = CASE WHEN :userName is not null THEN :userName ELSE user_name END," +
            "email = CASE WHEN :email is not null THEN :email ELSE email END," +
            "bio = CASE WHEN :bio is not null THEN :bio ELSE bio END, " +
            "updated_at = CASE WHEN :date is not null THEN :date END " +
            "WHERE user_id = :id")
    void updateUser(String id, String userName, String email, String bio, Date date);

    @Query("select case when count(u)> 0 then true else false end from User u " +
            "WHERE UPPER(u.email) = UPPER(:email) OR UPPER(u.user_name) = UPPER(:userName)")
    boolean existsByEmailAndUserName(String email, String userName);

}
