package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Want;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantRepository extends ListCrudRepository<Want, Long> {
}
