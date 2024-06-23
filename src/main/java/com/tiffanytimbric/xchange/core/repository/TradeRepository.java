package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Trade;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@PreAuthorize("hasRole('USER')")
public interface TradeRepository extends ListCrudRepository<Trade, UUID> {

    List<Trade> findByCompositeId(UUID compositeId);

}
