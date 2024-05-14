package com.tiffanytimbric.xchange.core.model;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public record Item(
            long id,
            String name,
            String description,
            List<Tag> tags,
            long owner,
            int price
) {

    @NonNull
    public Optional<String> nameOpt() {
        return Optional.ofNullable(name());
    }

    @NonNull
    public Optional<String> descriptionOpt() {
        return Optional.ofNullable(description());
    }

    @NonNull
    public List<Tag> tagsOpt() {
        if (CollectionUtils.isEmpty(tags())) {
            return List.of();
        }

        return tags();
    }

}
