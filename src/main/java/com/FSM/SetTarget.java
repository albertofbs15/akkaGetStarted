package com.FSM;

import akka.actor.ActorRef;

/**
 * Created by AHernandezS on 29/06/2017.
 */
public class SetTarget {
    private final ActorRef ref;

    public SetTarget(ActorRef ref) {
        this.ref = ref;
    }

    public ActorRef getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "SetTarget{" +
                "ref=" + ref +
                '}';
    }
}
