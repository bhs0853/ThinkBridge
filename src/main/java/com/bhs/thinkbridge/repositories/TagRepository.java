package com.bhs.thinkbridge.repositories;

import com.bhs.thinkbridge.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    boolean existsByNameEqualsIgnoreCase(String tag);

    Tag findByNameEqualsIgnoreCase(String tag);
}
