package com.tiffanytimbric.xchange.core.model;

import org.springframework.lang.NonNull;

import java.util.Optional;

public record Tag(
        String name
) {

    @NonNull
    public Optional<String> nameOpt() {
        return Optional.ofNullable(name());
    }

}
