package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
}
