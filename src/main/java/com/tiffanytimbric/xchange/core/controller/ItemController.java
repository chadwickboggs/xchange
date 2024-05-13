package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.repository.ItemRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ItemController {

    private final ItemRepository itemRepository = new ItemRepository();

    @GetMapping("/itemExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable final long id) {
        return itemRepository.doesExist(id);
    }

    @GetMapping("/item/{id}")
    @NonNull
    public Optional<Item> readItem(@PathVariable final long id) {
        return itemRepository.readItem(id);
    }

    @PostMapping("/item")
    @NonNull
    public Optional<Long> createItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        return itemRepository.createItem(item)
                .map(Item::id);
    }

    @PutMapping("/item")
    @NonNull
    public Optional<Long> updateItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/item")
    @NonNull
    public Optional<Long> patchItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/item/{id}")
    @NonNull
    public Optional<Long> deleteItem(@PathVariable final long id) {
        return itemRepository.deleteItem(id)
                .map(Item::id);
    }

}
