package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {

    @GetMapping("/tag/{name}")
    public Tag readTag(@PathVariable final String name) {
        // TODO: Implement.

        return new Tag(name);
    }

    @PostMapping("/tag")
    public String createTag(@RequestBody final Tag tag) {
        // TODO: Implement.

        return tag.name();
    }

    @PutMapping("/tag")
    public String updateTag(@RequestBody final Tag tag) {
        // TODO: Implement.

        return tag.name();
    }

    @PatchMapping("/tag")
    public String patchTag(@RequestBody final Tag tag) {
        // TODO: Implement.

        return tag.name();
    }

    @DeleteMapping("/tag/{name}")
    public String deleteTag(@PathVariable final String name) {
        // TODO: Implement.

        return name;
    }

}
