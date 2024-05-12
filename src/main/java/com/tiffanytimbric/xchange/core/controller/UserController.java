package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/user/{id}")
    public User readUser(@PathVariable final String id) {
        // TODO: Implement.

        return new User(id, "");
    }

    @PostMapping("/user")
    public String createUser(@RequestBody final User user) {
        // TODO: Implement.

        return user.id();
    }

    @PutMapping("/user")
    public String updateUser(@RequestBody final User user) {
        // TODO: Implement.

        return user.id();
    }

    @PatchMapping("/user")
    public String patchUser(@RequestBody final User user) {
        // TODO: Implement.

        return user.id();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable final String id) {
        // TODO: Implement.

        return id;
    }

}
