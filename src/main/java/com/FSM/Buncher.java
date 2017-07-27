package com.FSM;

import akka.actor.AbstractFSM;
import akka.japi.pf.UnitMatch;
import java.util.Arrays;
import java.util.LinkedList;

import scala.concurrent.duration.Duration;

import static com.FSM.State.Active;
import static com.FSM.State.Idle;

/**
 * Created by AHernandezS on 29/06/2017.
 */
public class Buncher  extends AbstractFSM<State, Data> {
    {
        startWith(Idle, Uninitialized.Uninitialized); //Uninitialized implements Data (Igual que el Todo.class)

        when(Idle,
                matchEvent(SetTarget.class, Uninitialized.class,
                        (setTarget, uninitialized) ->
                                stay().using(new Todo(setTarget.getRef(), new LinkedList<>()))));

        onTransition(
                matchState(Active, Idle, () -> {
                    // reuse this matcher
                    final UnitMatch<Data> m = UnitMatch.create(
                            matchData(Todo.class,
                                    todo -> todo.getTarget().tell(new Batch(todo.getQueue()), getSelf())));
                    m.match(stateData());
                }).
                        state(Idle, Active, () -> {/* Do something here */}));

        when(Active, Duration.create(1, "second"),
                matchEvent(Arrays.asList(Flush.class, StateTimeout()), Todo.class, //ACA LO FLUSHEO Y EN LA TRASICION ENVIO LA INFO
                        (event, todo) -> goTo(Idle).using(todo.copy(new LinkedList<>()))));

        whenUnhandled(
                matchEvent(Queue.class, Todo.class, // (SI ESTO ES Uninitialized.class se va al default ANYEVENT)
                        (queue, todo) -> goTo(Active).using(todo.addElement(queue.getObj()))) //AGREGO LOS ELEMENTOS

                .anyEvent((event, state) -> {
                    log().warning("received unhandled request {} in state {}/{}",
                            event, stateName(), state);
                    return stay();
                }));

        initialize();
    }

}
