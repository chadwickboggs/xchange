package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.xchange.core.model.Trade;
import com.tiffanytimbric.xchange.core.repository.TradeRepository;
import com.tiffanytimbric.xchange.core.service.TradeUtil;
import org.springframework.http.HttpHeaders;
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
    public boolean doesExist(@PathVariable final long id) {
        return tradeRepository.existsById(id);
    }

    @GetMapping("/trade/{id}")
    @NonNull
    public ResponseEntity<Trade> readTrade(@PathVariable final long id) {
        return ResponseEntity.of(
                tradeRepository.findById(id)
        );
    }

    @GetMapping("/acceptTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> acceptTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Accept", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/receiveTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> receiveTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Receive", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/abandonTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> abandonTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId,
            @RequestBody @Nullable final String reason
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Abandon", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/completeTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> completeTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Complete", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/failTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> failTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId,
            @RequestBody @Nullable final String reason
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Fail", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/tradeDecline/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> declineTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Decline", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/tradeFSM")
    @NonNull
    public ResponseEntity<String> readTradeFsm() {
        final FiniteStateMachine fsm = TradeUtil.newTradeFsm();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8"
                )
                .body(fsm.toJson());
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
    public ResponseEntity<Trade> deleteTrade(@PathVariable final long id) {
        final Optional<Trade> tradeOpt = tradeRepository.findById(id);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.ofNullable(null);
        }

        tradeRepository.deleteById(id);

        return ResponseEntity.of(tradeOpt);
    }

    private FiniteStateMachine getTradeFsm(long tradeId) {
        // TODO: Implement.

        return TradeUtil.newTradeFsm();
    }

    @NonNull
    private Optional<Trade> handleTradeEvent(
            @NonNull final String eventName,
            long tradeId,
            long userId
    ) {
        if (!tradeRepository.existsById(tradeId)) {
            return Optional.empty();
        }

        // TODO: Add `userId` to event data.

        getTradeFsm(tradeId).handleEvent(eventName);

        return tradeRepository.findById(tradeId);
    }

}
