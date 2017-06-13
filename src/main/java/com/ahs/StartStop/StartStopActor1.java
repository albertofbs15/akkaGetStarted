package com.ahs.StartStop;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by AHernandezS on 12/06/2017.
 */
public class StartStopActor1 extends AbstractActor {

    public static ActorSystem actorSystem =  ActorSystem.create();

    public static void main (String[] args) {
        ActorRef topLevel = actorSystem.actorOf(Props.create(StartStopActor1.class), "first");
        topLevel.tell("stop", ActorRef.noSender());
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("first started");
        getContext().actorOf(Props.create(StartStopActor2.class), "second");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("first stop");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().
                matchEquals("stop", p-> {
                    getContext().stop(self());
                })
                .build();
    }
}
