package com.ahs.Supervising;

import akka.actor.AbstractActor;

import java.util.Optional;

/**
 * Created by AHernandezS on 12/06/2017.
 */
public class SupervisedActor extends AbstractActor {

    @Override
    public void preStart() {
        System.out.println("supervised actor started");
    }

//    @Override
//    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
//        System.out.println("supervised actor preRestart");
//    }
//
//    @Override
//    public void postRestart(Throwable reason) throws Exception {
//        System.out.println("supervised actor postRestart");
//    }

    @Override
    public void postStop() {
        System.out.println("supervised actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("fail", f -> {
                    System.out.println("supervised actor fails now");
                    throw new IllegalArgumentException("I failed!");
                })
                .build();
    }
}
