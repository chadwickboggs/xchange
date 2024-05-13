package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Tag;
import com.tiffanytimbric.xchange.core.repository.TagRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class TagController {

    private final TagRepository tagRepository = new TagRepository();

    @GetMapping("/tagExist/{name}")
    @NonNull
    public boolean doesExist(@PathVariable @Nullable final String name) {
        if (isBlank(name)) {
            return false;
        }

        return tagRepository.doesExist(name);
    }

    @GetMapping("/tag/{name}")
    @NonNull
    public ResponseEntity<Tag> readTag(@PathVariable @Nullable final String name) {
        if (isBlank(name)) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                tagRepository.readTag(name)
        );
    }

    @PostMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> createTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                tagRepository.createTag(tag)
        );
    }

    @PutMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> updateTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .build();
    }

    @PatchMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> patchTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity
                .status(HttpStatusCode.valueOf(500))
                .build();
    }

    @DeleteMapping("/tag/{name}")
    @NonNull
    public ResponseEntity<Tag> deleteTag(@PathVariable @Nullable final String name) {
        if (isBlank(name)) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                tagRepository.deleteTag(name)
        );
    }

}
