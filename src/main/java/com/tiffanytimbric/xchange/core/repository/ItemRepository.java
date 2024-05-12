package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemRepository {

    private final Map<String, Item> cache = new HashMap<>();

    @NonNull
    public Optional<Item> readItem(@NonNull final String id) {
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.get(id));
    }

    @NonNull
    public Optional<Item> createItem(@Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        final Item alreadyExistingItem = cache.get(item.id());
        if (alreadyExistingItem != null) {
            return Optional.of(alreadyExistingItem);
        }

        return Optional.ofNullable(
                cache.put(item.id(), item)
        );
    }

    @NonNull
    public Optional<Item> deleteItem(@Nullable final String id) {
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.remove(id));
    }

}
