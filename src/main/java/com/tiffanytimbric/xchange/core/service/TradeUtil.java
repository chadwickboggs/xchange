package com.tiffanytimbric.xchange.core.service;

import com.tiffanytimbric.fsm.Event;
import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.fsm.State;
import com.tiffanytimbric.fsm.Transition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Predicate;

public class TradeUtil {

    @NonNull
    public static FiniteStateMachine newTradeFsm() {
        final State declinedStateEndState = new State(
                "Declined", null
        );

        final State abandonedEndState = new State(
                "Abandoned", null
        );

        final State failedEndState = new State(
                "Failed", null
        );

        final State completedEndState = new State(
                "Completed", null
        );

        final State partiallyCompletedState = new State(
                "Partially Completed", null,
                new Transition(
                        new Event("Complete", null),
                        new AllParticipantsPredicate(),
                        completedEndState, null
                ),
                new Transition(
                        new Event("Abandon", null),
                        null, abandonedEndState, null
                ),
                new Transition(
                        new Event("Fail", null),
                        null, failedEndState, null
                )
        );

        final State receivedState = new State(
                "Received", null,
                new Transition(
                        new Event("Abandon", null),
                        null, abandonedEndState, null
                ),
                new Transition(
                        new Event("Fail", null),
                        null, failedEndState, null
                ),
                new Transition(
                        new Event("Complete", null),
                        null, partiallyCompletedState, null
                )
        );

        final State partiallyReceivedState = new State(
                "Partially Received", null,
                new Transition(
                        new Event("Abandon", null),
                        null, abandonedEndState, null
                ),
                new Transition(
                        new Event("Receive", null),
                        new AllParticipantsPredicate(),
                        receivedState, null
                )
        );

        final State acceptedState = new State(
                "Accepted", null,
                new Transition(
                        new Event("Abandon", null),
                        null, abandonedEndState, null
                ),
                new Transition(
                        new Event("Receive", null),
                        null, partiallyReceivedState, null
                )
        );

        final State partiallyAcceptedState = new State(
                "Partially Accepted", null,
                new Transition(
                        new Event("Abandon", null),
                        null, abandonedEndState, null
                ),
                new Transition(
                        new Event("Decline", null),
                        null, declinedStateEndState, null
                ),
                new Transition(
                        new Event("Accept", null),
                        new AllParticipantsPredicate(),
                        acceptedState, null
                )
        );

        final State initialState = new State(
                "Proposed", null,
                new Transition(
                        new Event("Decline", null),
                        null, declinedStateEndState, null
                ),
                new Transition(
                        new Event("Accept", null),
                        null, partiallyAcceptedState, null
                )
        );

        return new FiniteStateMachine(
                "Trade FSM", null, initialState
        );
    }

    private static class AllParticipantsPredicate implements Predicate<String> {
        @Override
        public boolean test(final String dataItem) {
            if (StringUtils.isBlank(dataItem)) {
                return false;
            }

            final List<String> dataItemList = List.of(dataItem.split(","));

            if (dataItemList.size() < 2) {
                return false;
            }

            return true;
        }
    }
}
