package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Item;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ItemRepository {

    private final Map<Long, Item> cache = new HashMap<>();
    private static final AtomicLong currentMaxItemId = new AtomicLong();

    public boolean doesExist(final long id) {
        return cache.containsKey(id);
    }

    @NonNull
    public Optional<Item> readItem(final long id) {
        return Optional.ofNullable(
                cache.get(id)
        );
    }

    @NonNull
    public Optional<Item> createItem(@Nullable Item item) {
        if (item == null) {
            return Optional.empty();
        }

        item = new Item(
                currentMaxItemId.addAndGet(1),
                item.name(), item.description(), item.tags(), item.owner(), item.price()
        );

        cache.put(item.id(), item);

        return Optional.of(item);
    }

    @NonNull
    public Optional<Item> deleteItem(final long id) {
        return Optional.ofNullable(
                cache.remove(id)
        );
    }

}
