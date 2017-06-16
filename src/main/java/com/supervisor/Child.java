package com.supervisor;

import akka.actor.AbstractActor;

/**
 * Created by AHernandezS on 16/06/2017.
 */
public class Child extends AbstractActor {
    int state = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Exception.class, exception -> { throw exception; })
                .match(Integer.class, i -> state = i)
                .matchEquals("get", s -> getSender().tell(state, getSelf()))
                .build();
    }
}
