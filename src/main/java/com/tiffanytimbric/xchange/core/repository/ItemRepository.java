package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Item;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ListCrudRepository<Item, Long> {
}
