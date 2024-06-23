package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Want;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@PreAuthorize("hasRole('USER')")
public interface WantRepository extends ListCrudRepository<Want, UUID> {
}
