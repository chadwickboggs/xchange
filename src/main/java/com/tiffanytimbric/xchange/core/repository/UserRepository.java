package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {

    private final Map<String, User> cache = new HashMap<>();

    @NonNull
    public Optional<User> readUser(@NonNull final String id) {
        if (StringUtils.isBlank(id)) {
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
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }

        return Optional.ofNullable(cache.remove(id));
    }

}
