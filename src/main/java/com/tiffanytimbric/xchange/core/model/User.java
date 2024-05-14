package com.tiffanytimbric.xchange.core.model;

import org.springframework.lang.NonNull;

import java.util.Optional;

public record User(
        long id,
        String name,
        int balance
) {

    @NonNull
    public Optional<String> nameOpt() {
        return Optional.ofNullable(name());
    }

}
