package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.model.User;
import com.tiffanytimbric.xchange.core.repository.ItemRepository;
import com.tiffanytimbric.xchange.core.repository.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class ItemController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserController userController;
    private final TagController tagController;

    public ItemController(
            @NonNull final ItemRepository itemRepository,
            @NonNull final UserRepository userRepository,
            @NonNull final UserController userController,
            @NonNull final TagController tagController
    ) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.userController = userController;
        this.tagController = tagController;
    }

    @GetMapping("/itemExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable final long id) {
        return itemRepository.existsById(id);
    }

    @GetMapping("/item/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    @NonNull
    public ResponseEntity<Item> readItem(@PathVariable final long id) {
        final Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.of(Optional.empty());
        }

        final String username = String.valueOf(authentication.getPrincipal());
        if (isBlank(username)) {
            return ResponseEntity.of(Optional.empty());
        }

        final Optional<User> userRequestingOpt = userRepository.findByName(username);
        if (userRequestingOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }
        final User userRequesting = userRequestingOpt.get();

        final Item item = itemOpt.get();
        if (item.getOwner() != userRequesting.getId()) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(itemOpt);
    }

    @GetMapping("/itemByName/{name}")
    @NonNull
    public ResponseEntity<List<Item>> readItemByName(@PathVariable final String name) {
        return ResponseEntity.ofNullable(
                itemRepository.findByName(name)
        );
    }

    @PostMapping("/item")
    @NonNull
    public ResponseEntity<Item> createItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
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

    @PutMapping("/item")
    @NonNull
    public ResponseEntity<Item> updateItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
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
    public ResponseEntity<Item> patchItem(@RequestBody @Nullable final Item item) {
        if (item == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchItem\""
        );
    }

    @DeleteMapping("/item/{id}")
    @NonNull
    public ResponseEntity<Item> deleteItem(@PathVariable final long id) {
        final Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.ofNullable(null);
        }

        itemRepository.deleteById(id);

        return ResponseEntity.of(itemOpt);
    }

}
