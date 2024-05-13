package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.User;
import com.tiffanytimbric.xchange.core.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository = new UserRepository();

    @GetMapping("/userExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable final long id) {
        return userRepository.doesExist(id);
    }

    @GetMapping("/user/{id}")
    @NonNull
    public ResponseEntity<User> readUser(@PathVariable final long id) {
        return ResponseEntity.of(
                userRepository.readUser(id)
        );
    }

    @PostMapping("/user")
    @NonNull
    public ResponseEntity<User> createUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                userRepository.createUser(user)
        );
    }

    @PutMapping("/user")
    @NonNull
    public ResponseEntity<User> updateUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/user")
    @NonNull
    public ResponseEntity<User> patchUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/user/{id}")
    @NonNull
    public ResponseEntity<User> deleteUser(@PathVariable final long id) {
        return ResponseEntity.of(
                userRepository.deleteUser(id)
        );
    }

}
