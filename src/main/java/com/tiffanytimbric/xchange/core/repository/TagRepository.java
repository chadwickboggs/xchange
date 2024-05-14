package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Tag;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Repository
public class TagRepository {

    private final Map<String, Tag> cache = new HashMap<>();

    public boolean doesExist(@Nullable final String name) {
        if (isBlank(name)) {
            return false;
        }

        return cache.containsKey(name);
    }

    @NonNull
    public Optional<Tag> readTag(@Nullable final String name) {
        if (isBlank(name)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                cache.get(name)
        );
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

        cache.put(tag.name(), tag);

        return Optional.of(tag);
    }

    @NonNull
public Optional<Tag> deleteTag(@Nullable final String name) {
        if (isBlank(name)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                cache.remove(name)
        );
    }

}
