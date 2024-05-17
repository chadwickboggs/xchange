package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    @NonNull
    Optional<User> findByName(String name);

}
