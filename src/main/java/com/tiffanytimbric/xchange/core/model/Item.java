package com.tiffanytimbric.xchange.core.model;

import java.util.List;

public record Item(
            long id,
            String name,
            String description,
            List<Tag> tags,
            long owner,
            int price
) {}
