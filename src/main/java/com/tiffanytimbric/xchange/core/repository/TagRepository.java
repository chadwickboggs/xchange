package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TagRepository {

    private final Map<String, Tag> cache = new HashMap<>();

    @NonNull
    public Optional<Tag> readTag(@Nullable final String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.get(name));
    }

    @NonNull
    public Optional<Tag> createTag(@Nullable final Tag tag) {
        if (tag == null) {
            return Optional.empty();
        }

        final Tag alreadyExistingTag = cache.get(tag.name());
        if (alreadyExistingTag != null) {
            return Optional.of(alreadyExistingTag);
        }

        return Optional.ofNullable(
                cache.put(tag.name(), tag)
        );
    }

    @NonNull
    public Optional<Tag> deleteTag(@Nullable final String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.remove(name));
    }

}
