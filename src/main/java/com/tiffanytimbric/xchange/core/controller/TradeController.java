package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.xchange.core.model.Trade;
import com.tiffanytimbric.xchange.core.repository.TradeRepository;
import com.tiffanytimbric.xchange.core.service.TradeService;
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
    public ResponseEntity<String> acceptTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"acceptTrade\""
        );
    }

    @GetMapping("/receiveTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<String> receiveTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"receiveTrade\""
        );
    }

    @PostMapping("/abandonTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<String> abandonTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId,
            @RequestBody @Nullable final String reason
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"abandonTrade\""
        );
    }

    @GetMapping("/completeTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<String> completeTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"completeTrade\""
        );
    }

    @PostMapping("/failTrade/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<String> failTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId,
            @RequestBody @Nullable final String reason
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"failTrade\""
        );
    }

    @GetMapping("/tradeDecline/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<String> declineTrade(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        // TODO: Implement.

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"declineTrade\""
        );
    }

    @GetMapping("/tradeFSM")
    @NonNull
    public ResponseEntity<String> readTradeFsm() {
        final FiniteStateMachine fsm = TradeService.newTradeFsm();

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

}
