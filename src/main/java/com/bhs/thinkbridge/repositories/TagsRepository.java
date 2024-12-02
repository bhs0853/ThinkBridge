package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, String> {
}
