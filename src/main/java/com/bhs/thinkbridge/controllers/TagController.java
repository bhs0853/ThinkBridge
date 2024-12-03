package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.TagDTO;
import com.bhs.thinkbridge.models.Tag;
import com.bhs.thinkbridge.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") String id){
        Optional<Tag> tag = tagService.getTagById(id);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO tagDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tagDTO));
    }

}
