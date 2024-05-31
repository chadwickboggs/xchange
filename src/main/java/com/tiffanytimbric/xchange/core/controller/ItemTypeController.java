package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.ItemType;
import com.tiffanytimbric.xchange.core.repository.ItemTypeRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class ItemTypeController {

    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeController(
            @NonNull final ItemTypeRepository itemTypeRepository
    ) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @GetMapping("/itemTypeExist/{name}")
    @NonNull
    public boolean doesExist(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return itemTypeRepository.count() > 0;
        }

        return itemTypeRepository.existsById(name);
    }

    @GetMapping("/itemType/{name}")
    @NonNull
    public ResponseEntity<ItemType> readItemType(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                itemTypeRepository.findById(name)
        );
    }

    @PostMapping("/itemType")
    @NonNull
    public ResponseEntity<ItemType> createItemType(
            @RequestBody @Nullable final ItemType itemType
    ) {
        if (itemType == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                Optional.of(itemTypeRepository.save(itemType))
        );
    }

    @PutMapping("/itemType")
    @NonNull
    public ResponseEntity<ItemType> updateItemType(
            @RequestBody @Nullable final ItemType itemType
    ) {
        if (itemType == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                Optional.of(itemTypeRepository.save(itemType))
        );
    }

    @PatchMapping("/itemType")
    @NonNull
    public ResponseEntity<ItemType> patchItemType(
            @RequestBody @Nullable final ItemType itemType
    ) {
        if (itemType == null) {
            return ResponseEntity.of(Optional.empty());
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchTag\""
        );
    }

    @DeleteMapping("/itemType/{name}")
    @NonNull
    public ResponseEntity<ItemType> deleteItemType(
            @PathVariable @Nullable final String name
    ) {
        if (isBlank(name)) {
            return ResponseEntity.of(Optional.empty());
        }

        final Optional<ItemType> itemTypeOpt = itemTypeRepository.findById(name);
        if (itemTypeOpt.isEmpty()) {
            ResponseEntity.of(Optional.empty());
        }

        itemTypeRepository.deleteById(name);

        return ResponseEntity.of(itemTypeOpt);
    }

}
