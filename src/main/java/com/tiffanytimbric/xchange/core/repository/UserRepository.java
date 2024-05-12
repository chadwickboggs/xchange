package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class UserRepository {

    private final Map<String, User> cache = new HashMap<>();

    public boolean doesExist(@Nullable final String id) {
        if (isBlank(id)) {
            return false;
        }

        return cache.containsKey(id);
    }

    @NonNull
    public Optional<User> readUser(@Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.get(id));
    }

    @NonNull
    public Optional<User> createUser(@Nullable final User user) {
        if (user == null) {
            return Optional.empty();
        }

        final User alreadyExistingUser = cache.get(user.id());
        if (alreadyExistingUser != null) {
            return Optional.of(alreadyExistingUser);
        }

        return Optional.ofNullable(
                cache.put(user.id(), user)
        );
    }

    @NonNull
    public Optional<User> deleteUser(@Nullable final String id) {
        if (isBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.remove(id));
    }

}
