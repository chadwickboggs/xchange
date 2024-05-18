package com.tiffanytimbric.xchange.core.repository;

import com.tiffanytimbric.xchange.core.model.Trade;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends ListCrudRepository<Trade, Long> {
}
