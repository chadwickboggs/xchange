package com.tiffanytimbric.xchange.core.service;

import com.tiffanytimbric.fsm.Event;
import com.tiffanytimbric.fsm.FiniteStateMachine;
import com.tiffanytimbric.fsm.State;
import com.tiffanytimbric.fsm.Transition;
import org.springframework.lang.NonNull;

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
                        completedEndState, null
                ),
                new Transition(
                        new Event("Abandon", null),
                        abandonedEndState, null
                ),
                new Transition(
                        new Event("Fail", null),
                        failedEndState, null
                )
        );

        final State receivedState = new State(
                "Received", null,
                new Transition(
                        new Event("Fail", null),
                        failedEndState, null
                ),
                new Transition(
                        new Event("Complete", null),
                        partiallyCompletedState, null
                )
        );

        final State partiallyReceivedState = new State(
                "Partially Received", null,
                new Transition(
                        new Event("Abandon", null),
                        abandonedEndState, null
                ),
                new Transition(
                        new Event("Recieve", null),
                        receivedState, null
                )
        );

        final State acceptedState = new State(
                "Accepted", null,
                new Transition(
                        new Event("Abandon", null),
                        abandonedEndState, null
                ),
                new Transition(
                        new Event("Recieve", null),
                        partiallyReceivedState, null
                )
        );

        final State partiallyAcceptedState = new State(
                "Partially Accepted", null,
                new Transition(
                        new Event("Decline", null),
                        declinedStateEndState, null
                ),
                new Transition(
                        new Event("Accept", null),
                        acceptedState, null
                )
        );

        final State initialState = new State(
                "Proposed", null,
                new Transition(
                        new Event("Decline", null),
                        declinedStateEndState, null
                ),
                new Transition(
                        new Event("Accept", null),
                        partiallyAcceptedState, null
                )
        );

        return new FiniteStateMachine(
                "Trade FSM", null, initialState
        );
    }

}
