package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.xchange.core.model.Trade;
import com.tiffanytimbric.xchange.core.repository.TradeRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class TradeController {

    private final TradeRepository tradeRepository;

    public TradeController(
            @NonNull final TradeRepository tradeRepository
    ) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/tradeExist/{id}")
    @NonNull
    public boolean doesExist(@PathVariable @Nullable final long id) {
        return tradeRepository.existsById(id);
    }

    @GetMapping("/trade/{id}")
    @NonNull
    public ResponseEntity<Trade> readTrade(@PathVariable @Nullable final long id) {
        return ResponseEntity.of(
                tradeRepository.findById(id)
        );
    }

    @PostMapping("/trade")
    @NonNull
    public ResponseEntity<Trade> createTrade(@RequestBody @Nullable final Trade trade) {
        if (trade == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                Optional.of(tradeRepository.save(trade))
        );
    }

    @PutMapping("/trade")
    @NonNull
    public ResponseEntity<Trade> updateTrade(@RequestBody @Nullable final Trade trade) {
        if (trade == null) {
            return ResponseEntity.ofNullable(null);
        }

        return ResponseEntity.of(
                Optional.of(tradeRepository.save(trade))
        );
    }

    @PatchMapping("/trade")
    @NonNull
    public ResponseEntity<Trade> patchTrade(@RequestBody @Nullable final Trade trade) {
        if (trade == null) {
            return ResponseEntity.ofNullable(null);
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchTrade\""
        );
    }

    @DeleteMapping("/trade/{id}")
    @NonNull
    public ResponseEntity<Trade> deleteTrade(@PathVariable @Nullable final long name) {
        final Optional<Trade> tradeOpt = tradeRepository.findById(name);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.ofNullable(null);
        }

        tradeRepository.deleteById(name);

        return ResponseEntity.of(tradeOpt);
    }

}
