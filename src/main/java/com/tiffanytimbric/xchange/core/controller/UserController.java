package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.User;
import com.tiffanytimbric.xchange.core.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class UserController {

    private final UserRepository userRepository = new UserRepository();

    @GetMapping("/userExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable @Nullable final String id) {
        if (isBlank(id)) {
            return false;
        }

        return userRepository.doesExist(id);
    }

    @GetMapping("/user/{id}")
    @NonNull
    public Optional<User> readUser(@PathVariable @Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return userRepository.readUser(id);
    }

    @PostMapping("/user")
    @NonNull
    public Optional<String> createUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        return userRepository.createUser(user)
                .map(User::id);
    }

    @PutMapping("/user")
    @NonNull
    public Optional<String> updateUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @PatchMapping("/user")
    @NonNull
    public Optional<String> patchUser(@RequestBody @Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        throw new NotImplementedException();
    }

    @DeleteMapping("/user/{id}")
    @NonNull
    public Optional<String> deleteUser(@PathVariable @Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return userRepository.deleteUser(id)
                .map(User::id);
    }

}
