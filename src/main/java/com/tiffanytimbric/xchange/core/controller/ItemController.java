package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.repository.ItemRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Item> readItem(@PathVariable final long id) {
        return ResponseEntity.of(
                itemRepository.readItem(id)
        );
    }

    @PostMapping("/item")
    @NonNull
    public ResponseEntity<Item> createItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                itemRepository.createItem(item)
        );
    }

    @PutMapping("/item")
    @NonNull
    public ResponseEntity<Item> updateItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/item")
    @NonNull
    public ResponseEntity<Item> patchItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/item/{id}")
    @NonNull
    public ResponseEntity<Item> deleteItem(@PathVariable final long id) {
        return ResponseEntity.of(
                itemRepository.deleteItem(id)
        );
    }

}
