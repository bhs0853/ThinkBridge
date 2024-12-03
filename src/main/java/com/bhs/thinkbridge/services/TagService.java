package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.dtos.TagDTO;
import com.bhs.thinkbridge.models.Tag;
import com.bhs.thinkbridge.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(String id){
        return tagRepository.findById(id);
    }

    public Tag createTag(TagDTO tagDTO){
        if(tagRepository.existsByNameEqualsIgnoreCase(tagDTO.getName())){
            return tagRepository.findByNameEqualsIgnoreCase(tagDTO.getName());
        }
        Tag newTag = Tag.builder().name(tagDTO.getName()).build();
        return tagRepository.save(newTag);
    }

}
