package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Item;
import com.tiffanytimbric.xchange.core.model.KC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @GetMapping("/item/{id}")
    public Item readItem(@PathVariable final String id) {
        // TODO: Implement.

        return new Item(id, "", "", List.of(), new KC(1));
    }

    @PostMapping("/item")
    public String createItem(@RequestBody final Item item) {
        // TODO: Implement.

        return item.id();
    }

    @PutMapping("/item")
    public String updateItem(@RequestBody final Item item) {
        // TODO: Implement.

        return item.id();
    }

    @PatchMapping("/item")
    public String patchItem(@RequestBody final Item item) {
        // TODO: Implement.

        return item.id();
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable final String id) {
        // TODO: Implement.

        return id;
    }

}
