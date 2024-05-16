package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Tag;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends ListCrudRepository<Tag, String> {
}
