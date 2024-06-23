package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.repository.ItemRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@PreAuthorize("hasRole('USER')")
public class ItemController {

    private final ItemRepository itemRepository;
    private final UserController userController;

    public ItemController(
            @NonNull final ItemRepository itemRepository,
            @NonNull final UserController userController
    ) {
        this.itemRepository = itemRepository;
        this.userController = userController;
    }

    @GetMapping("/itemExist/{id}")
    @NonNull
    public boolean doesExist(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return itemRepository.count() > 0;
        }

        return itemRepository.existsById(id);
    }

    @GetMapping("/item/{id}")
    @NonNull
    public ResponseEntity<Item> readItem(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                itemRepository.findById(id)
        );
    }

    @GetMapping("/itemByName/{name}")
    @NonNull
    public ResponseEntity<List<Item>> readItemByName(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.ofNullable(
                itemRepository.findByName(name)
        );
    }

    @PostMapping("/item")
    @NonNull
    public ResponseEntity<Item> createItem(
            @RequestBody @Nullable final Item item
    ) {
        if (item == null) {
            return ResponseEntity.of(Optional.empty());
        }

        // Verify the referenced owner exists.
        if (!userController.doesExist(item.getOwner())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    format(
                            "Invalid owner, owner not found.  Owner: %s", item.getOwner()
                    )
            );
        }

        if (item.idOpt().isEmpty()) {
            item.setId(UUID.randomUUID());
        }

        return ResponseEntity.of(
                Optional.of(itemRepository.save(item))
        );
    }

    @PutMapping("/item")
    @NonNull
    public ResponseEntity<Item> updateItem(
            @RequestBody @Nullable final Item item
    ) {
        if (item == null) {
            return ResponseEntity.of(Optional.empty());
        }

        // Verify the referenced owner exists.
        if (!userController.doesExist(item.getOwner())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    format(
                            "Invalid owner, owner not found.  Owner: %s", item.getOwner()
                    )
            );
        }

        return ResponseEntity.of(
                Optional.of(itemRepository.save(item))
        );
    }

    @PatchMapping("/item")
    @NonNull
    public ResponseEntity<Item> patchItem(
            @RequestBody @Nullable final Item item
    ) {
        if (item == null) {
            return ResponseEntity.of(Optional.empty());
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchItem\""
        );
    }

    @DeleteMapping("/item/{id}")
    @NonNull
    public ResponseEntity<Item> deleteItem(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return ResponseEntity.of(Optional.empty());
        }

        final Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        itemRepository.deleteById(id);

        return ResponseEntity.of(itemOpt);
    }

}
