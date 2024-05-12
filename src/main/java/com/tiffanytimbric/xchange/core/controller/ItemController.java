package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.repository.ItemRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class ItemController {

    private final ItemRepository itemRepository = new ItemRepository();

    @GetMapping("/item/{id}")
    @NonNull
    public Optional<Item> readItem(@PathVariable @Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return itemRepository.readItem(id);
    }

    @PostMapping("/item")
    @NonNull
    public Optional<String> createItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        return itemRepository.createItem(item)
                .map(Item::id);
    }

    @PutMapping("/item")
    @NonNull
    public Optional<String> updateItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/item")
    @NonNull
    public Optional<String> patchItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/item/{id}")
    @NonNull
    public Optional<String> deleteItem(@PathVariable @Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return itemRepository.deleteItem(id)
                .map(Item::id);
    }

}
