package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.ItemType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
@PreAuthorize("hasRole('USER')")
public interface ItemTypeRepository extends ListCrudRepository<ItemType, String> {
}
