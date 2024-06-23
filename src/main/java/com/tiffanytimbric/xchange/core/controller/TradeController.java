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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@PreAuthorize("hasRole('USER')")
public class TradeController {

    private final TradeRepository tradeRepository;

    public TradeController(
            @NonNull final TradeRepository tradeRepository
    ) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/tradeExist/{id}")
    @NonNull
    public boolean doesExist(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return tradeRepository.count() > 0;
        }

        return tradeRepository.existsById(id);
    }

    @GetMapping("/trade/{id}")
    @NonNull
    public ResponseEntity<Trade> readTrade(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                tradeRepository.findById(id)
        );
    }

    @GetMapping("/tradeFsmEvents/{tradeId}")
    @NonNull
    public ResponseEntity<List<Event>> tradeFsmEvents(
            @PathVariable final UUID tradeId
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
            @PathVariable final UUID tradeId
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
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent(
                "Accept", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @GetMapping("/tradeReceive/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeReceive(
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent(
                "Receive", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/tradeAbandon/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeAbandon(
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId,
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
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId
    ) {
        final Optional<Trade> tradeOpt = handleTradeEvent(
                "Complete", tradeId, userId
        );

        return ResponseEntity.of(tradeOpt);
    }

    @PostMapping("/tradeFail/{tradeId}/{userId}")
    @NonNull
    public ResponseEntity<Trade> tradeFail(
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId,
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
            @PathVariable final UUID tradeId,
            @PathVariable final UUID userId
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
    public ResponseEntity<Trade> createTrade(
            @RequestBody @Nullable final Trade trade
    ) {
        if (trade == null) {
            return ResponseEntity.of(Optional.empty());
        }

        if (trade.idOpt().isEmpty()) {
            trade.setId(UUID.randomUUID());
        }
        if (trade.compositeIdOpt().isEmpty()) {
            Trade tradeCloned = (Trade) trade.clone();
            tradeCloned.setId(UUID.randomUUID());
            tradeCloned.setItemTwoId(tradeCloned.getItemOneId());
            tradeCloned = tradeRepository.save(tradeCloned);

            trade.setCompositeId(tradeCloned.getId());
        }

        return ResponseEntity.of(
                Optional.of(tradeRepository.save(trade))
        );
    }

    @PutMapping("/trade")
    @NonNull
    public ResponseEntity<Trade> updateTrade(
            @RequestBody @Nullable final Trade trade
    ) {
        if (trade == null) {
            return ResponseEntity.of(Optional.empty());
        }

        return ResponseEntity.of(
                Optional.of(tradeRepository.save(trade))
        );
    }

    @PatchMapping("/trade")
    @NonNull
    public ResponseEntity<Trade> patchTrade(
            @RequestBody @Nullable final Trade trade
    ) {
        if (trade == null) {
            return ResponseEntity.of(Optional.empty());
        }

        throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Invalid method, method not implemented.  Method Name: \"patchTrade\""
        );
    }

    @DeleteMapping("/trade/{id}")
    @NonNull
    public ResponseEntity<Trade> deleteTrade(
            @PathVariable @Nullable final UUID id
    ) {
        if (id == null) {
            return ResponseEntity.of(Optional.empty());
        }

        final Optional<Trade> tradeOpt = tradeRepository.findById(id);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.of(Optional.empty());
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

        fsm.findState(trade.getState()).ifPresent(fsmState -> {
            fsm.setCurrentState(new State<>(
                    trade.getState(),
                    trade.getDataItem(),
                    ((State) fsmState).transitions()
            ));
        });

        return fsm;
    }

    @NonNull
    private Optional<Trade> handleTradeEvent(
            @Nullable final String eventName,
            @Nullable final UUID tradeId,
            @Nullable final UUID userId
    ) {
        if (isBlank(eventName) || tradeId == null || userId == null) {
            return Optional.empty();
        }

        if (!tradeRepository.existsById(tradeId)) {
            return Optional.empty();
        }

        final Optional<Trade> tradeOpt = tradeRepository.findById(tradeId);
        if (tradeOpt.isEmpty()) {
            return tradeOpt;
        }

        return handleTradeEvent(
                eventName, tradeOpt.get(), userId
        );
    }

    @NonNull
    private Optional<Trade> handleTradeEvent(
            @Nullable final String eventName,
            @Nullable final Trade trade,
            @Nullable final UUID userId
    ) {
        if (isBlank(eventName) || trade == null || userId == null) {
            return Optional.empty();
        }

        if (trade.compositeIdOpt().isEmpty()) {
            return handleParentTradeEvent(eventName, trade, userId);
        }

        final Optional<Trade> tradeUpdatedOpt = handleChildTradeEvent(eventName, trade, userId);

        //
        // Propagate all end-state transitions to parent state.
        //
        tradeUpdatedOpt.ifPresent(tradeUpdated -> {
            final State tradeFsmCurrentState = getTradeFsm(tradeUpdated).getCurrentState();
            if (ArrayUtils.isEmpty(tradeFsmCurrentState.transitions())) {
                if ("Complete".equalsIgnoreCase(tradeFsmCurrentState.name())) {
                    boolean someIncomplete =
                            tradeRepository.findByCompositeId(tradeUpdated.getCompositeId()).stream()
                                    .map(Trade::getState)
                                    .anyMatch(childTrade -> !"Complete".equalsIgnoreCase(childTrade));

                    if (!someIncomplete) {
                        tradeRepository.findById(
                                        tradeUpdated.getCompositeId()
                                )
                                .ifPresent(compositeTrade -> {
                                    compositeTrade.setState(tradeUpdated.getState());
                                    tradeRepository.save(compositeTrade);
                                });
                    }
                }
                else {
                    tradeRepository.findById(
                                    tradeUpdated.getCompositeId()
                            )
                            .ifPresent(compositeTrade -> {
                                compositeTrade.setState(tradeUpdated.getState());
                                tradeRepository.save(compositeTrade);
                            });
                }
            }
        });

        return tradeUpdatedOpt;
    }

    @NonNull
    private Optional<Trade> handleParentTradeEvent(
            @Nullable final String eventName,
            @Nullable final Trade trade,
            @Nullable final UUID userId
    ) {
        if (isBlank(eventName) || trade == null || userId == null) {
            return Optional.empty();
        }

        // TODO: Implement.

        return Optional.empty();
    }

    @NonNull
    private Optional<Trade> handleChildTradeEvent(
            @Nullable final String eventName,
            @Nullable final Trade trade,
            @Nullable final UUID userId
    ) {
        if (isBlank(eventName) || trade == null || userId == null) {
            return Optional.empty();
        }

        Trade tradeUpdated = addUserIdToStateDataItem(trade, userId);
        if (!tradeUpdated.equals(trade)) {
            tradeUpdated = tradeRepository.save(tradeUpdated);
        }

        final FiniteStateMachine tradeFsm = getTradeFsm(trade);

        final State<String> toState = tradeFsm.handleEvent(eventName);

        if (tradeUpdated.getState().equalsIgnoreCase(toState.name())) {
            return Optional.of(tradeUpdated);
        }

        tradeUpdated.setState(toState.name());
        tradeUpdated.dataItemSet(Set.of(String.valueOf(userId)));

        tradeUpdated = tradeRepository.save(tradeUpdated);

        return Optional.of(tradeUpdated);
    }

    @Nullable
    private Trade addUserIdToStateDataItem(
            @Nullable final Trade trade,
            @Nullable final UUID userId
    ) {
        if (trade == null || userId == null) {
            return null;
        }

        final Trade tradeClone = (Trade) trade.clone();

        final Set<String> dataItemSet = tradeClone.dataItemSet();
        dataItemSet.add(String.valueOf(userId));
        tradeClone.dataItemSet(dataItemSet);

        return tradeClone;
    }

}
