package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.ItemType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends ListCrudRepository<ItemType, String> {
}
