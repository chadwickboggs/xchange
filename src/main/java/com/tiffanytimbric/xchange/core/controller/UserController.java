package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.User;
import com.tiffanytimbric.xchange.core.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public Optional<User> readUser(@PathVariable final long id) {
        return userRepository.readUser(id);
    }

    @PostMapping("/user")
    @NonNull
    public Optional<Long> createUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        return userRepository.createUser(user)
                .map(User::id);
    }

    @PutMapping("/user")
    @NonNull
    public Optional<Long> updateUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/user")
    @NonNull
    public Optional<Long> patchUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/user/{id}")
    @NonNull
    public Optional<Long> deleteUser(@PathVariable final long id) {
        return userRepository.deleteUser(id)
                .map(User::id);
    }

}
