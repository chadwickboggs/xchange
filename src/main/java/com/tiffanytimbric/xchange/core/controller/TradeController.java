package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.fsm.State;
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

    @GetMapping("/tradeAccept/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeAccept(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Accept", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/tradeReceive/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeReceive(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Receive", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/tradeAbandon/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeAbandon(
            @PathVariable final long tradeId,
            @PathVariable final long userId,
            @RequestBody @Nullable final String reason
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Abandon", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/tradeComplete/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeComplete(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent("" +
                "Complete", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/tradeFail/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeFail(
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
    public ResponseEntity<Trade> tradeDecline(
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

        final Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return tradeOpt;
        }
        final Trade trade = tradeOpt.get();

        final State toState = getTradeFsm(tradeId).handleEvent(eventName);
        trade.setState(
                toState.name()
        );

        final Trade tradeSaved = tradeRepository.save(trade);

        return Optional.of(tradeSaved);
    }

}
