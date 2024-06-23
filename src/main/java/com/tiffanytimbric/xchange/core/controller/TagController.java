package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Tag;
import com.tiffanytimbric.xchange.core.repository.TagRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@PreAuthorize("hasRole('USER')")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(
            @NonNull final TagRepository tagRepository
    ) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/tagExist/{name}")
    @NonNull
    public boolean doesExist(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return tagRepository.count() > 0;
        }

        return tagRepository.existsById(name);
    }

    @GetMapping("/tag/{name}")
    @NonNull
    public ResponseEntity<Tag> readTag(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                tagRepository.findById(name)
        );
    }

    @PostMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> createTag(
            @RequestBody @Nullable final Tag tag
    ) {
        if (tag == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                Optional.of(tagRepository.save(tag))
        );
    }

    @PutMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> updateTag(
            @RequestBody @Nullable final Tag tag
    ) {
        if (tag == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                Optional.of(tagRepository.save(tag))
        );
    }

    @PatchMapping("/tag")
    @NonNull
    public ResponseEntity<Tag> patchTag(
            @RequestBody @Nullable final Tag tag
    ) {
        if (tag == null) {
            return ResponseEntity.of(Optional.empty());
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchTag\""
        );
    }

    @DeleteMapping("/tag/{name}")
    @NonNull
    public ResponseEntity<Tag> deleteTag(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return ResponseEntity.of(Optional.empty());
        }

        final Optional<Tag> tagOpt = tagRepository.findById(name);
        if (tagOpt.isEmpty()) {
            ResponseEntity.of(Optional.empty());
        }

        tagRepository.deleteById(name);

        return ResponseEntity.of(tagOpt);
    }

}
