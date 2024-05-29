package com.tiffanytimbric.xchange.core.controller;

import com.tiffanytimbric.fsm.Event;
import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.fsm.State;
import com.tiffanytimbric.fsm.Transition;
import com.tiffanytimbric.xchange.core.model.Trade;
import com.tiffanytimbric.xchange.core.repository.TradeRepository;
import com.tiffanytimbric.xchange.core.service.TradeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/tradeFsmEvents/{tradeId}")
    @NonNull
    public ResponseEntity<List<Event>> tradeFsmEvents(
            @PathVariable final long tradeId
    ) {
        final Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        final Transition[] transitions = getTradeFsm(
                tradeOpt.get()
        ).getCurrentState().transitions();
        if (ArrayUtils.isEmpty(transitions)) {
            return ResponseEntity.of(Optional.of(
                    List.of()
            ));
        }

        final List<Event> events = Arrays.stream(transitions)
                .map(Transition::event)
                .toList();

        return ResponseEntity.of(Optional.of(
                events
        ));
    }

    @GetMapping("/tradeFsmState/{tradeId}")
    @NonNull
    public ResponseEntity<State> tradeFsmState(
            @PathVariable final long tradeId
    ) {
        final Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
        }

        final State fsmState = getTradeFsm(
                tradeOpt.get()
        ).getCurrentState();

        return ResponseEntity.of(Optional.of(
                fsmState
        ));
    }

    @GetMapping("/tradeAccept/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeAccept(
            @PathVariable final long tradeId,
            @PathVariable final long userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent(
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
        final Optional<Trade> tradeOpt = handleTradeEvent(
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
        final Optional<Trade> tradeOpt = handleTradeEvent(
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
        final Optional<Trade> tradeOpt = handleTradeEvent(
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
        final Optional<Trade> tradeOpt = handleTradeEvent(
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
        final Optional<Trade> tradeOpt = handleTradeEvent(
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

    @NonNull
    private FiniteStateMachine getTradeFsm(
            @Nullable final Trade trade
    ) {
        final FiniteStateMachine fsm = TradeUtil.newTradeFsm();
        if (trade == null) {
            return fsm;
        }

        fsm.findState(trade.getState()).ifPresent(currentState ->
                fsm.setCurrentState((State) currentState)
        );

        return fsm;
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

        final Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return tradeOpt;
        }
        Trade trade = tradeOpt.get();

        final State<String> toState = getTradeFsm(trade).handleEvent(eventName);
        if (trade.getState().equals(toState.name())) {
            trade = addUserIdToDataItem(trade, userId);

            return Optional.of(trade);
        }

        trade.setState(toState.name());
        trade.setDataItem(toState.dataItem());

        trade = addUserIdToDataItem(trade, userId);

        final Trade tradeSaved = tradeRepository.save(trade);

        return Optional.of(tradeSaved);
    }

    @Nullable
    private Trade addUserIdToDataItem(
            @Nullable final Trade trade,
            long userId
    ) {
        if (trade == null) {
            return null;
        }

        final Trade tradeClone = (Trade) trade.clone();

        final Set<String> dataItemSet = tradeClone.dataItemSet();
        dataItemSet.add(String.valueOf(userId));
        tradeClone.dataItemSet(dataItemSet);

        return tradeClone;
    }

}
