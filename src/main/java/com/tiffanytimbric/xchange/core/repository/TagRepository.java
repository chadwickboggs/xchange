package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, String> {
}
