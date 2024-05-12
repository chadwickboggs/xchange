package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Tag;
import com.tiffanytimbric.xchange.core.repository.TagRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class TagController {

    private final TagRepository tagRepository = new TagRepository();

    @GetMapping("/tag/{name}")
    @NonNull
    public Optional<Tag> readTag(@PathVariable @Nullable final String name) {
        if (isBlank(name)) {
            return Optional.empty();
        }

        return tagRepository.readTag(name);
    }

    @PostMapping("/tag")
    @NonNull
    public Optional<String> createTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return Optional.empty();
        }

        return tagRepository.createTag(tag)
                .map(Tag::name);
    }

    @PutMapping("/tag")
    @NonNull
    public Optional<String> updateTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return Optional.empty();
        }

        // TODO: Implement.

        return Optional.ofNullable(tag.name());
    }

    @PatchMapping("/tag")
    @NonNull
    public Optional<String> patchTag(@RequestBody @Nullable final Tag tag) {
        if (tag == null) {
            return Optional.empty();
        }

        // TODO: Implement.

        return Optional.ofNullable(tag.name());
    }

    @DeleteMapping("/tag/{name}")
    @NonNull
    public Optional<String> deleteTag(@PathVariable @Nullable final String name) {
        if (isBlank(name)) {
            return Optional.empty();
        }

        return tagRepository.deleteTag(name)
                .map(Tag::name);
    }

}
