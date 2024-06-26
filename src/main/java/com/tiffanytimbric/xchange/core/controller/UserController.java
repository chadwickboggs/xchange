package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.User;
import com.tiffanytimbric.xchange.core.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(
            @NonNull final UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @GetMapping("/userExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable final long id) {
        return userRepository.existsById(id);
    }

    @GetMapping("/user/{id}")
    @NonNull
    public ResponseEntity<User> readUser(@PathVariable final long id) {
        return ResponseEntity.of(
                userRepository.findById(id)
        );
    }

    @PostMapping("/user")
    @NonNull
    public ResponseEntity<User> createUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                Optional.of(userRepository.save(user))
        );
    }

    @PutMapping("/user")
    @NonNull
    public ResponseEntity<User> updateUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                Optional.of(userRepository.save(user))
        );
    }

    @PatchMapping("/user")
    @NonNull
    public ResponseEntity<User> patchUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchUser\""
        );
    }

    @DeleteMapping("/user/{id}")
    @NonNull
    public ResponseEntity<User> deleteUser(@PathVariable final long id) {
        final Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.ofNullable(null);
        }

        userRepository.deleteById(id);

        return ResponseEntity.of(userOpt);
    }

}
