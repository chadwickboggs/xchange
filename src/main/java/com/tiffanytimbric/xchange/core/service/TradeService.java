package com.tiffanytimbric.xchange.core.service;

import com.tiffanytimbric.xchange.core.repository.TradeRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PreAuthorize("hasRole('USER')")
public class TradeService {

    private TradeRepository tradeRepository;

    public TradeService() {
    }

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Nullable
    public TradeRepository getTradeRepository() {
        return tradeRepository;
    }

    @NonNull
    public Optional<TradeRepository> tradeRepositoryOpt() {
        return Optional.ofNullable(tradeRepository);
    }

    public void setTradeRepository(
            @Nullable final TradeRepository tradeRepository
    ) {
        this.tradeRepository = tradeRepository;
    }

}
