package com.ahs.StartStop;

import akka.actor.AbstractActor;

/**
 * Created by AHernandezS on 12/06/2017.
 */
public class StartStopActor2 extends AbstractActor {
    @Override
    public void preStart() throws Exception {
        System.out.println("second started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("second stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
