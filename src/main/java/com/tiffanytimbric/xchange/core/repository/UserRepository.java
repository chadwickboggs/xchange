package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final Map<Long, User> cache = new HashMap<>();
    private static final AtomicLong currentMaxUserId = new AtomicLong();

    public boolean doesExist(final long id) {
        return cache.containsKey(id);
    }

    @NonNull
    public Optional<User> readUser(final long id) {
        return Optional.ofNullable(
                cache.get(id)
        );
    }

    @NonNull
    public Optional<User> createUser(@Nullable User user) {
        if (user == null) {
            return Optional.empty();
        }

        user = new User(
                currentMaxUserId.addAndGet(1),
                user.name(), user.balance()
        );

        cache.put(user.id(), user);

        return Optional.of(user);
    }

    @NonNull
    public Optional<User> deleteUser(final long id) {
        return Optional.ofNullable(
                cache.remove(id)
        );
    }

}
