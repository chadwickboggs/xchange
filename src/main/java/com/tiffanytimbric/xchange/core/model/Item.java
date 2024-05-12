package com.tiffanytimbric.xchange.core.model;

import java.util.List;

public record Item(
        String id,
        String name,
        String description,
        List<Tag> tags,
        int price
) {
}
