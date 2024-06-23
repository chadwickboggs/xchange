package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Item;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@PreAuthorize("hasRole('USER')")
public interface ItemRepository extends ListCrudRepository<Item, UUID> {

    @Nullable
    List<Item> findByName(String name);

}
